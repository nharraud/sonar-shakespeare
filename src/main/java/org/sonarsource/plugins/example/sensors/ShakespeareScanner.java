package org.sonarsource.plugins.example.sensors;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstVisitor;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.ast.AstWalker;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.error.NewAnalysisError;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.example.rules.PersonaeCheck;
import org.sonarsource.plugins.example.rules.ShakespeareCheck;
import org.sonarsource.plugins.example.shakespeare.ShakespeareParser;
import org.sonarsource.plugins.example.sensors.ShakespeareRulesDefinition;

/**
 * ShakespeareScanner
 */
public class ShakespeareScanner {

  private static final Logger LOGGER = Loggers.get(ShakespeareScanner.class);

  private List<InputFile> inputFiles;
  private SensorContext context;
  private FileLinesContextFactory fileLinesContextFactory;
  private NoSonarFilter noSonarFilter;

  private final Parser<Grammar> p = ShakespeareParser.create();
  private final Grammar g = p.getGrammar();
  public final static Map<RuleKey, Class<? extends ShakespeareCheck>> visitors = Map
      .ofEntries(new AbstractMap.SimpleEntry<>(PersonaeCheck.KEY, PersonaeCheck.class));

  public ShakespeareScanner(SensorContext context, FileLinesContextFactory fileLinesContextFactory,
      NoSonarFilter noSonarFilter, List<InputFile> inputFiles) {
    this.inputFiles = inputFiles;
    this.context = context;
    this.fileLinesContextFactory = fileLinesContextFactory;
    this.noSonarFilter = noSonarFilter;
  }

  public void scanFiles() {
    Collection<Class<? extends ShakespeareCheck>> activeVisitors = getActiveVisitors();
    for (InputFile shakespeareFile : inputFiles) {
      if (context.isCancelled()) {
        return;
      }
      try {
        AstWalker walker = createWalker(shakespeareFile, activeVisitors);
        scanFile(shakespeareFile, walker);
      } catch (IOException e) {
        NewAnalysisError error = context.newAnalysisError();
        error.message(e.getMessage());
        error.save();
      }
    }
  }

  private Collection<Class<? extends ShakespeareCheck>> getActiveVisitors() {
    Collection<ActiveRule> activeRules = context.activeRules().findByRepository(ShakespeareRulesDefinition.REPO_KEY);
    List<Class<? extends ShakespeareCheck>> activeVisitors = new ArrayList<>();
    for (ActiveRule rule : activeRules) {
      Class<? extends ShakespeareCheck> visitor = visitors.get(rule.ruleKey());
      if (visitor == null) {
        NewAnalysisError error = context.newAnalysisError();
        error.message(String.format("could not find rule %s", rule.toString()));
        error.save();
      } else {
        activeVisitors.add(visitor);
      }
    }
    return activeVisitors;
  }

  private AstWalker createWalker(InputFile file, Collection<Class<? extends ShakespeareCheck>> visitors) {
    AstWalker walker = new AstWalker();
    for (Class<? extends ShakespeareCheck> visitor : visitors) {
      try {
        Constructor<? extends ShakespeareCheck> constructor = visitor.getDeclaredConstructor(SensorContext.class, InputFile.class);
        walker.addVisitor(constructor.newInstance(this.context, file));
      } catch (Exception e) {
        LOGGER.error("Error while creating AstVisitor", e);
        NewAnalysisError error = context.newAnalysisError();
        error.message(e.getMessage());
        error.save();
      }
    }
    return walker;
  }

  private void scanFile(InputFile inputFile, AstWalker walker) throws IOException {
    LOGGER.warn("Scanning file " + inputFile.filename());

    // new PersonaeCheck(context, inputFile));
    // p.setRootRule(g.rule(ShakespeareGrammar.DRAMATIS_PERSONAE));
    AstNode node = p.parse(inputFile.contents());
    walker.walkAndVisit(node);
  }

}
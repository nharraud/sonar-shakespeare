package org.sonarsource.plugins.example.rules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonarsource.plugins.example.sensors.ShakespeareRulesDefinition;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;
import org.sonarsource.plugins.example.shakespeare.ShakespeareVisitor;
import org.sonarsource.plugins.example.utils.AstNodeLocation;

/**
 * CyranoCheck
 */
public class CyranoCheck extends ShakespeareVisitor {
  public final static RuleKey KEY = RuleKey.of(ShakespeareRulesDefinition.REPO_KEY, "CyranoRule");
  private static final String MESSAGE = "Plagiarism Alert: Ah no! young blade! That was a trifle short!";

  public CyranoCheck(SensorContext context, InputFile file) {
    super(context, file);
  }

  @Override
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return Arrays.asList(ShakespeareGrammar.NEGATIVE_NOUN_PHRASE);
  }

  @Override
  public void visitNode(AstNode ast) {
    AstNode noun = ast.getLastChild();
    if ("nose".equals(noun.getToken().getValue())) {
      List<AstNode> bigs = ast.getChildren().stream()
        .filter((node) -> "big".equals(node.getToken().getValue()))
        .collect(Collectors.toList());
      if (!bigs.isEmpty()) {
        NewIssue newIssue = context.newIssue().forRule(KEY);
        NewIssueLocation primaryLocation = newIssue.newLocation().on(file).message(MESSAGE);
        primaryLocation.at(AstNodeLocation.buildLocation(noun, this.file));
        newIssue.at(primaryLocation);

        for (AstNode adjective: bigs) {
          NewIssueLocation secondaryLocation = newIssue.newLocation().on(file);
          secondaryLocation.at(AstNodeLocation.buildLocation(adjective, this.file));
          newIssue.addLocation(secondaryLocation);
        }
        newIssue.save();
      }
    }
  }
}
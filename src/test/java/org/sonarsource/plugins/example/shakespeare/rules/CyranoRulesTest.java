package org.sonarsource.plugins.example.shakespeare.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.ast.AstWalker;

import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.DefaultTextPointer;
import org.sonar.api.batch.fs.internal.DefaultTextRange;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.batch.sensor.issue.IssueLocation;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonarsource.plugins.example.rules.CyranoCheck;
import org.sonarsource.plugins.example.rules.PersonaeCheck;
import org.sonarsource.plugins.example.shakespeare.ShakespeareParser;

/**
 * CyranoRulesTest
 */
public class CyranoRulesTest {
  private final AstWalker walker = new AstWalker();
  protected final Parser<LexerlessGrammar> p = ShakespeareParser.create();
  protected final Grammar g = p.getGrammar();

  @Test
  public void testCheck() throws IOException {
    String program = String.join("\n"
    , "This is a title."
    , "Romeo, a young man with a remarkable patience."
    , "Act I: Hamlet's insults and flattery."
    , "Scene I: The insulting of Romeo."
    , "Romeo:"
    , "You are as disgusting as your big big fat nose."
    );
    TestInputFileBuilder fileBuilder = TestInputFileBuilder.create("myModule", ".");

    DefaultInputFile inputFile = fileBuilder.setContents(program).setLanguage("shakespeare").build();
    
    SensorContextTester context = SensorContextTester.create(new File("."));
    context.fileSystem().add(inputFile);

    walker.addVisitor(new CyranoCheck(context, inputFile));
    AstNode node = p.parse(inputFile.contents());
    walker.walkAndVisit(node);
    Collection<Issue> issues = context.allIssues();
    assertEquals(issues.size(), 1);
    Issue firstIssue = issues.iterator().next();
    assertEquals(firstIssue.ruleKey(), CyranoCheck.KEY);
    IssueLocation location = firstIssue.primaryLocation();
    TextRange range = location.textRange();
    assertEquals(
      new DefaultTextRange(new DefaultTextPointer(6, 42), new DefaultTextPointer(6, 46)),
      range
    );
  }
}
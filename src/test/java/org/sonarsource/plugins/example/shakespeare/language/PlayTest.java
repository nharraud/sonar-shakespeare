package org.sonarsource.plugins.example.shakespeare.language;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.sonar.sslr.tests.Assertions.assertThat;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

public class PlayTest extends RuleTest {
  // private static final String FILE_NAME = "start.spl";

  @Override
  @Before
  public void init() {
    p.setRootRule(g.rule(ShakespeareGrammar.PLAY));
  }

  // public void test(AstNode node) {
  //   AstNodeType type = node.getType();
  // }

  @Test
  public void reallife() throws Exception {
    // ClassLoader classLoader = getClass().getClassLoader();
    // System.out.println(classLoader.getResource(FILE_NAME).getPath());
    String program = String.join("\n"
    , "This is a title."
    , ""
    , "Romeo, a young man with a remarkable patience."
    , "Juliet, a likewise young woman of remarkable grace."
    , "Ophelia, a remarkable woman much in dispute with Hamlet."
    , "Hamlet, the flatterer of Andersen Insulting A/S."
);
assertThat(p)
    .matches(program);
    // Path file_path = Paths.get(PlayTest.class.getResource(FILE_NAME).toURI());
    // String program = new String(Files.readAllBytes(file_path), StandardCharsets.UTF_8);
    // System.out.println(program);
  }

}
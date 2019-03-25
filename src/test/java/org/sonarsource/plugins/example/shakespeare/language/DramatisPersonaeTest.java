package org.sonarsource.plugins.example.shakespeare.language;


import org.junit.Before;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.sonar.sslr.api.Grammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class DramatisPersonaeTest extends RuleTest {

  @Test
  public void reallife() throws Exception {
    String program = String.join("\n"
        , "Romeo, a young man with a remarkable patience."
        , "Juliet, a likewise young woman of remarkable grace."
        , "Ophelia, a remarkable woman much in dispute with Hamlet."
        , "Hamlet, the flatterer of Andersen Insulting A/S."
    );
    // assertThat(p)
    assertThat(g.rule(ShakespeareGrammar.DRAMATIS_PERSONAE))
        .matches(program);
  }

}
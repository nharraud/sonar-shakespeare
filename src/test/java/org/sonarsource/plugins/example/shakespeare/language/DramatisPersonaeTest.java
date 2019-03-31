package org.sonarsource.plugins.example.shakespeare.language;


import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;

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
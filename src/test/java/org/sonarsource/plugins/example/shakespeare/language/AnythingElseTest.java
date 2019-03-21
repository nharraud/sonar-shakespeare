package org.sonarsource.plugins.example.shakespeare.language;


import org.junit.Before;
import org.junit.Test;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class AnythingElseTest extends RuleTest {

  @Override
  @Before
  public void init() {
    p.setRootRule(g.rule(ShakespeareGrammar.TEXT_BEFORE_PUNCTUATION));
  }

  @Test
  public void reallife() throws Exception {
    String program = String.join("\n"
      , "a young man with a remarkable patience"
    );
    assertThat(p)
        .matches(program);
  }

}
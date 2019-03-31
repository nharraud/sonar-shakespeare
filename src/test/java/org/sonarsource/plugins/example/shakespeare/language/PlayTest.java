package org.sonarsource.plugins.example.shakespeare.language;


import static org.sonar.sslr.tests.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;

public class PlayTest extends RuleTest {
  @Test
  public void reallife() throws Exception {
    Path file_path = Paths.get(PlayTest.class.getResource("/hello.spl").toURI());
    String program = new String(Files.readAllBytes(file_path), StandardCharsets.UTF_8);
    assertThat(g.rule(ShakespeareGrammar.PLAY))
      .matches(program);
  }

}
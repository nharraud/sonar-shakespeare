package org.sonarsource.plugins.example.shakespeare;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;
import com.sonar.sslr.impl.Lexer;
import static com.sonar.sslr.test.lexer.LexerMatchers.hasToken;
import static org.junit.Assert.assertThat;
import static org.sonarsource.plugins.example.shakespeare.ShakespeareLexer.Punctuators.*;

import static org.sonarsource.plugins.example.shakespeare.ShakespeareLexer.IGNORED.*;

import org.junit.Test;

/**
 * ShakespeareLexerTest
 */
public class ShakespeareLexerTest {
  Lexer lexer = ShakespeareLexer.create();
  
  @Test
  public void lexIdentifiers() {
    assertThat(lexer.lex("title"), hasToken("title", IDENTIFIER));
  }

  @Test
  public void lexPunctuators() {
    // assertThat(lexer.lex("."), hasToken(".", DOT));
    assertThat(lexer.lex("123"), hasToken("123", ANYTHING_ELSE));
    assertThat(lexer.lex("#$"), hasToken("#$", ANYTHING_ELSE));
  }
}
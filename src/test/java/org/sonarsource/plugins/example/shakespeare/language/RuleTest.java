package org.sonarsource.plugins.example.shakespeare.language;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.Rule;
import com.sonar.sslr.impl.Parser;

import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonarsource.plugins.example.shakespeare.ShakespeareParser;

public abstract class RuleTest {

  protected final Parser<LexerlessGrammar> p = ShakespeareParser.create();
  protected final Grammar g = p.getGrammar();

  public final Rule getTestedRule() {
    return p.getRootRule();
  }
}
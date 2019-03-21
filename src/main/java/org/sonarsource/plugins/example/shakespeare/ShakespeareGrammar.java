package org.sonarsource.plugins.example.shakespeare;

import static org.sonarsource.plugins.example.shakespeare.ShakespeareLexer.Punctuators.*;

import static org.sonarsource.plugins.example.shakespeare.ShakespeareLexer.IGNORED.ANYTHING_ELSE;

import com.sonar.sslr.api.Grammar;
import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

enum ShakespearePunctuator implements GrammarRuleKey {
  DOT("."),
  EXCLAMATION("!");
  private final String value;

  ShakespearePunctuator(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

public enum ShakespeareGrammar implements GrammarRuleKey {
  TEXT_BEFORE_PUNCTUATION,
  TITLE,
  DRAMATIS_PERSONAE,
  PERSONA,
  PLAY;

public static Grammar create() {
    LexerfulGrammarBuilder b = LexerfulGrammarBuilder.create();
    b.rule(TEXT_BEFORE_PUNCTUATION).is(b.oneOrMore(b.firstOf(IDENTIFIER, ANYTHING_ELSE)));
    b.rule(TITLE).is(TEXT_BEFORE_PUNCTUATION, DOT);
    b.rule(PERSONA).is(b.sequence(IDENTIFIER, COMMA, TEXT_BEFORE_PUNCTUATION, DOT));
    b.rule(DRAMATIS_PERSONAE).is(b.oneOrMore(PERSONA));
    b.rule(PLAY).is(TITLE, DRAMATIS_PERSONAE);
    b.setRootRule(PLAY);
    return b.build();

    // LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

    // b.rule(TITLE).is(b.sequence(b.regexp("[^!\\.]*"), b.firstOf(ShakespearePunctuator.DOT, ShakespearePunctuator.EXCLAMATION)));
    // // b.rule(TITLE).is(b.sequence(b.regexp("[^!\\.]*"), b.firstOf(DOT, EXCLAMATION)));
    // b.rule(PLAY).is(TITLE);
    // for (ShakespearePunctuator p : ShakespearePunctuator.values()) {
    //   b.rule(p).is(p.getValue()).skip();
    // }
    // b.setRootRule(PLAY);
    // return b.build();
  }
}
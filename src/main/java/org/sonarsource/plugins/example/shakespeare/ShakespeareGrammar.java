package org.sonarsource.plugins.example.shakespeare;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

public enum ShakespeareGrammar implements GrammarRuleKey {
  DOT, EXCLAMATION,
  TITLE,
  END,
  COLUMN,
  COMMA,
  DRAMATIS_PERSONAE,
  PERSONA_DECLARATION,
  PERSONA_NAME,
  PERSONA_DESCRIPTION,
  WHITESPACE,
  PLAY;

public static LexerlessGrammar create() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

    b.rule(WHITESPACE).is(b.regexp("\\s+"));
    b.rule(DOT).is(".");
    b.rule(EXCLAMATION).is("!");
    b.rule(END).is(b.firstOf(DOT, EXCLAMATION), b.zeroOrMore(WHITESPACE));
    b.rule(COLUMN).is(":");
    b.rule(COMMA).is(",");
    b.rule(TITLE).is(b.regexp("[^!.]*"), END);
    b.rule(PERSONA_NAME).is(b.regexp("[A-Za-z]+(\\s+[A-Za-z]+)*"));
    b.rule(PERSONA_DESCRIPTION).is(b.regexp("[^!.]*"), END);
    b.rule(PERSONA_DECLARATION).is(PERSONA_NAME, b.zeroOrMore(WHITESPACE), COMMA, b.regexp("[^!.]*"), END);
    b.rule(DRAMATIS_PERSONAE).is(b.oneOrMore(PERSONA_DECLARATION));
    b.rule(PLAY).is(TITLE, DRAMATIS_PERSONAE);
    
    b.setRootRule(PLAY);
    return b.build();
  }
}
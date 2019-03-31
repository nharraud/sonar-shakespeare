package org.sonar.sslr.grammar;

import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.Trivia.TriviaKind;

import org.sonar.sslr.grammar.GrammarException;
import org.sonar.sslr.grammar.GrammarRuleBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.internal.grammar.MutableGrammar;
import org.sonar.sslr.internal.grammar.MutableParsingRule;
import org.sonar.sslr.internal.vm.EndOfInputExpression;
import org.sonar.sslr.internal.vm.ParsingExpression;
import org.sonar.sslr.internal.vm.PatternExpression;
import org.sonar.sslr.internal.vm.StringExpression;
import org.sonar.sslr.internal.vm.TokenExpression;
import org.sonar.sslr.internal.vm.TriviaExpression;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Case insensitive LexerlessGrammarBuilder
 * Duplicating the original class because there is no way to subclass it.
 */
public class CaseInsensitiveLexerlessGrammarBuilder extends GrammarBuilder {

  private final Map<GrammarRuleKey, MutableParsingRule> definitions = new HashMap<>();
  private GrammarRuleKey rootRuleKey;

  public static CaseInsensitiveLexerlessGrammarBuilder create() {
    return new CaseInsensitiveLexerlessGrammarBuilder();
  }

  private CaseInsensitiveLexerlessGrammarBuilder() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GrammarRuleBuilder rule(GrammarRuleKey ruleKey) {
    MutableParsingRule rule = definitions.get(ruleKey);
    if (rule == null) {
      rule = new MutableParsingRule(ruleKey);
      definitions.put(ruleKey, rule);
    }
    return new RuleBuilder(this, rule);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRootRule(GrammarRuleKey ruleKey) {
    rule(ruleKey);
    rootRuleKey = ruleKey;
  }

  /**
   * Constructs grammar.
   *
   * @throws GrammarException if some of rules were used, but not defined
   * @return grammar
   */
  public LexerlessGrammar build() {
    for (MutableParsingRule rule : definitions.values()) {
      if (rule.getExpression() == null) {
        throw new GrammarException("The rule '" + rule.getRuleKey() + "' hasn't been defined.");
      }
    }
    return new MutableGrammar(definitions, rootRuleKey);
  }

  /**
   * Creates parsing expression based on regular expression.
   *
   * @param regexp  regular expression
   * @throws java.util.regex.PatternSyntaxException if the expression's syntax is invalid
   */
  public Object regexp(String regexp) {
    return new PatternExpression(regexp);
  }

  /**
   * Creates parsing expression - "end of input".
   * This expression succeeds only if parser reached end of input.
   */
  public Object endOfInput() {
    return EndOfInputExpression.INSTANCE;
  }

  /**
   * Creates parsing expression - "token".
   *
   * @param e  sub-expression
   * @throws IllegalArgumentException if given argument is not a parsing expression
   */
  public Object token(TokenType tokenType, Object e) {
    return new TokenExpression(tokenType, convertToExpression(e));
  }

  /**
   * Creates parsing expression - "comment trivia".
   *
   * @param e  sub-expression
   * @throws IllegalArgumentException if given argument is not a parsing expression
   */
  public Object commentTrivia(Object e) {
    return new TriviaExpression(TriviaKind.COMMENT, convertToExpression(e));
  }

  /**
   * Creates parsing expression - "skipped trivia".
   *
   * @param e  sub-expression
   * @throws IllegalArgumentException if given argument is not a parsing expression
   */
  public Object skippedTrivia(Object e) {
    return new TriviaExpression(TriviaKind.SKIPPED_TEXT, convertToExpression(e));
  }

  @Override
  protected ParsingExpression convertToExpression(Object e) {
    Objects.requireNonNull(e, "Parsing expression can't be null");
    final ParsingExpression result;
    if (e instanceof ParsingExpression) {
      result = (ParsingExpression) e;
    } else if (e instanceof GrammarRuleKey) {
      GrammarRuleKey ruleKey = (GrammarRuleKey) e;
      rule(ruleKey);
      result = definitions.get(ruleKey);
    } else if (e instanceof String) {
      result = new CaseInsensitiveStringExpression((String) e);
    } else if (e instanceof Character) {
      result = new CaseInsensitiveStringExpression(((Character) e).toString());
    } else {
      throw new IllegalArgumentException("Incorrect type of parsing expression: " + e.getClass().toString());
    }
    return result;
  }
}
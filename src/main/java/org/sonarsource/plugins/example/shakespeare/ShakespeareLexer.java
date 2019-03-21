package org.sonarsource.plugins.example.shakespeare;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.IdentifierAndKeywordChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

public final class ShakespeareLexer {
  private ShakespeareLexer() {
  
  }

  // public static enum Title implements TokenType {
  //   ;

  //   @Override
  //   public String getName() {
  //     return null;
  //   }

  //   @Override
  //   public String getValue() {
  //     return null;
  //   }

  //   @Override
  //   public boolean hasToBeSkippedFromAst(AstNode node) {
  //     return false;
	// }
  // }

  public static enum Punctuators implements TokenType {

    DOT("."), EXCLAMATION("!"), COMMA(","), SEMICOLON(";");

    private final String value;

    private Punctuators(String value) {
      this.value = value;
    }

    @Override
    public String getName() {
      return name();
    }

    @Override
    public String getValue() {
      return value;
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
      return false;
    }

  }

  public static enum IGNORED implements TokenType {
    ANYTHING_ELSE;
    // OTHER;

    @Override
    public String getName() {
      return name();
    }

    @Override
    public String getValue() {
      return name();
    }

    @Override
    public boolean hasToBeSkippedFromAst(AstNode node) {
      return false;
    }
  }

  public static Lexer create() {
    return Lexer.builder()
        .withFailIfNoChannelToConsumeOneCharacter(true)
        .withChannel(new BlackHoleChannel("[ \t\r\n]+"))
        .withChannel(new PunctuatorChannel(Punctuators.values()))
        .withChannel(new IdentifierAndKeywordChannel("[a-zA-Z]([a-zA-Z0-9_]*[a-zA-Z0-9])?+", true/*, Keywords.values()*/))
        .withChannel(regexp(IGNORED.ANYTHING_ELSE, "[^!\\.]+"))
        // .withChannel(regexp(Literals.INTEGER, "[0-9]+"))
        // .withChannel(commentRegexp("(?s)/\\*.*?\\*/"))
        .build();
  }
}
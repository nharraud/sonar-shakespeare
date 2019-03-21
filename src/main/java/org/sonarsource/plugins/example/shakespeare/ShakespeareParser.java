package org.sonarsource.plugins.example.shakespeare;

import java.io.File;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.Parser.Builder;

import org.apache.commons.io.FileUtils;

public final class ShakespeareParser {

  private static final Parser<Grammar> P = ShakespeareParser.create();

  private ShakespeareParser() {
  }

  public static Parser<Grammar> create() {
    Grammar tmp = ShakespeareGrammar.create();
    Builder<Grammar> tmp2 = Parser.builder(tmp);
    return tmp2.withLexer(ShakespeareLexer.create()).build();
    // tmp2.build();
    // return null;
  }

  public static AstNode parseFile(String filePath) {
    File file = FileUtils.toFile(ShakespeareParser.class.getResource(filePath));
    if (file == null || !file.exists()) {
      throw new AssertionError("The file \"" + filePath + "\" does not exist.");
    }

    return P.parse(file);
  }

  public static AstNode parseString(String source) {
    return P.parse(source);
  }

}
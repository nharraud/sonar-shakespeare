package org.sonarsource.plugins.example.shakespeare;

import java.io.File;
import java.nio.charset.Charset;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.Parser.Builder;

import org.apache.commons.io.FileUtils;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

public final class ShakespeareParser {

  private static final Parser<LexerlessGrammar> P = ShakespeareParser.create();

  private ShakespeareParser() {
  }

  public static Parser<LexerlessGrammar> create() {
    return new ParserAdapter<>(Charset.forName("UTF8"), ShakespeareGrammar.create());

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
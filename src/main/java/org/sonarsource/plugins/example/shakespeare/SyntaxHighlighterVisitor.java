package org.sonarsource.plugins.example.shakespeare;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.colorizer.CDocTokenizer;
import org.sonar.colorizer.CppDocTokenizer;
import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.Tokenizer;
import org.sonarsource.plugins.example.utils.AstNodeLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

public class SyntaxHighlighterVisitor extends ShakespeareVisitor {

  private final Map<ShakespeareGrammar, TypeOfText> highlights = new HashMap<ShakespeareGrammar, TypeOfText>(){{
      put(ShakespeareGrammar.PERSONA_NAME, TypeOfText.CONSTANT);
      put(ShakespeareGrammar.FIRST_PERSON_VALUE, TypeOfText.CONSTANT);
      put(ShakespeareGrammar.SECOND_PERSON_VALUE, TypeOfText.CONSTANT);
      put(ShakespeareGrammar.OUTPUT, TypeOfText.ANNOTATION);
      put(ShakespeareGrammar.INPUT, TypeOfText.ANNOTATION);
      put(ShakespeareGrammar.ACT_ID, TypeOfText.KEYWORD_LIGHT);
      put(ShakespeareGrammar.SCENE_ID, TypeOfText.KEYWORD_LIGHT);
      put(ShakespeareGrammar.BINARY_OPERATION, TypeOfText.KEYWORD);
      put(ShakespeareGrammar.UNARY_OPERATION, TypeOfText.KEYWORD);
      put(ShakespeareGrammar.AND, TypeOfText.KEYWORD);
      put(ShakespeareGrammar.ANY_TEXT, TypeOfText.COMMENT);
      put(ShakespeareGrammar.TITLE, TypeOfText.COMMENT);
  }};//Map.of(
  //   ShakespeareGrammar.PERSONA_NAME, TypeOfText.CONSTANT,
  //   ShakespeareGrammar.FIRST_PERSON_VALUE, TypeOfText.CONSTANT,
  //   ShakespeareGrammar.SECOND_PERSON_VALUE, TypeOfText.CONSTANT,
  //   ShakespeareGrammar.OUTPUT, TypeOfText.PREPROCESS_DIRECTIVE,
  //   ShakespeareGrammar.INPUT, TypeOfText.PREPROCESS_DIRECTIVE,
  //   // ShakespeareGrammar.ACT_ID, TypeOfText.KEYWORD,
  //   // ShakespeareGrammar.SCENE_ID, TypeOfText.KEYWORD,
  //   ShakespeareGrammar.BINARY_OPERATION, TypeOfText.KEYWORD,
  //   ShakespeareGrammar.UNARY_OPERATION, TypeOfText.KEYWORD,
  //   ShakespeareGrammar.AND, TypeOfText.KEYWORD,
  //   ShakespeareGrammar.ANY_TEXT, TypeOfText.COMMENT,
  //   ShakespeareGrammar.TITLE, TypeOfText.COMMENT
  // );

  // private Set<ShakespeareGrammar> keywords = Stream.of(
  //     ShakespeareGrammar.PERSONA_NAME
  // ).collect(Collectors.toSet());
  // Arrays.asList(ShakespeareGrammar.PERSONA_NAME);

  private NewHighlighting highlighting;

  public SyntaxHighlighterVisitor(SensorContext context, InputFile file, NewHighlighting highlighting) {
    super(context, file);
    this.highlighting = highlighting;
  }

  @Override
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return new ArrayList<>(highlights.keySet());
    // return Stream.of(keywords, Arrays.asList(ShakespeareGrammar.ANY_TEXT))
    //     .flatMap(x -> x.stream()).collect(Collectors.toList());
    // return Arrays.asList(ShakespeareGrammar.PERSONA_NAME);
  }

  @Override
  public void visitNode(AstNode ast) {
    TypeOfText typeOfText = highlights.get(ast.getType());
    this.highlight(ast, typeOfText);
    // if (keywords.contains(ast.getType())) {
    //   this.highlight(ast, TypeOfText.KEYWORD);
    // } else if (ast.getType() == ShakespeareGrammar.ANY_TEXT) {
    //   this.highlight(ast, TypeOfText.KEYWORD);
    // }
  }

  private void highlight(AstNode node, TypeOfText typeOfText) {
    TextRange location = AstNodeLocation.buildLocation(node, this.file);
    highlighting.highlight(location, typeOfText);
  }

}
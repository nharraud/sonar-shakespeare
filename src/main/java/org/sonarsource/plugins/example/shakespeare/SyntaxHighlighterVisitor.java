package org.sonarsource.plugins.example.shakespeare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonarsource.plugins.example.utils.AstNodeLocation;

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
  }};

  private NewHighlighting highlighting;

  public SyntaxHighlighterVisitor(SensorContext context, InputFile file, NewHighlighting highlighting) {
    super(context, file);
    this.highlighting = highlighting;
  }

  @Override
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return new ArrayList<>(highlights.keySet());
  }

  @Override
  public void visitNode(AstNode ast) {
    TypeOfText typeOfText = highlights.get(ast.getType());
    this.highlight(ast, typeOfText);
  }

  private void highlight(AstNode node, TypeOfText typeOfText) {
    TextRange location = AstNodeLocation.buildLocation(node, this.file);
    highlighting.highlight(location, typeOfText);
  }

}
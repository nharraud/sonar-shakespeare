package org.sonarsource.plugins.example.shakespeare;

import java.util.List;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.AstVisitor;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;

public abstract class ShakespeareVisitor implements AstVisitor {

  protected final SensorContext context;
  protected final InputFile file;

  public ShakespeareVisitor(SensorContext context, InputFile file) {
    this.context = context;
    this.file = file;
  }

  @Override
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return null;
  }

  @Override
  public void visitFile(AstNode ast) {

  }

  @Override
  public void leaveFile(AstNode ast) {

  }

  @Override
  public void visitNode(AstNode ast) {

  }

  @Override
  public void leaveNode(AstNode ast) {

  }
}
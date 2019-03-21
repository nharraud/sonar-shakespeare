package org.sonarsource.plugins.example.shakespeare;

public class LexicalConstant {
  public static final String TEXT_BEFORE_PUNCTUATION = "[^!\\.]*";

  public static final String PUNCTUATION = "(?:!|\\.)";
}

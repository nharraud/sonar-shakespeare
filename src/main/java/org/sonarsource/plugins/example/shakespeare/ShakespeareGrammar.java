package org.sonarsource.plugins.example.shakespeare;

import org.sonar.sslr.grammar.CaseInsensitiveLexerlessGrammarBuilder;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

public enum ShakespeareGrammar implements GrammarRuleKey {
  DOT, EXCLAMATION, QUESTION_MARK,
  WHITESPACE,
  OPT_WHITESPACE,
  ROMAN_NUMBER,
  ANY_TEXT,
  TITLE,
  END,
  QEND,
  COLUMN,
  COMMA,

  DRAMATIS_PERSONAE,
  PERSONA_DECLARATION,
  PERSONA_NAME,
  PERSONA_DESCRIPTION,
  
  ACT,
  SCENE,

  PERSONAE_LIST,
  ENTER,
  EXIT,
  EXEUNT,

  LINE,
  SENTENCE,

  EVENT,

  BE,
  ARTICLE,
  FIRST_PERSON,
  FIRST_PERSON_REFLEXIVE,
  FIRST_PERSON_POSSESSIVE,
  SECOND_PERSON,
  SECOND_PERSON_REFLEXIVE,
  SECOND_PERSON_POSSESSIVE,
  THIRD_PERSON_POSSESSIVE,
  NEGATIVE_ADJECTIVE,
  NEUTRAL_ADJECTIVE,
  POSITIVE_ADJECTIVE,
  NEGATIVE_NOUN,
  NEUTRAL_NOUN,
  POSITIVE_NOUN,
  NOTHING,
  POSITIVE_OR_NEUTRAL_ADJECTIVE,
  POSSESSIVE,
  POSITIVE_ADVERB,
  NEGATIVE_ADVERB,
  POSITIVE_COMPARATIVE,
  NEGATIVE_COMPARATIVE,
  POSITIVE_OR_NEUTRAL_NOUN,

  NEUTRAL_COMPARATIVE,
  NEGATIVE_NOUN_PHRASE,
  POSITIVE_NOUN_PHRASE,
  NOUN_PHRASE,
  FIRST_PERSON_VALUE,
  SECOND_PERSON_VALUE,
  VALUE,
  BINARY_OPERATION,
  BINARY_EXPRESSION,
  UNARY_OPERATION,
  UNARY_EXPRESSION,
  EXPRESSION,
  ASSIGNMENT,
  LET_US,
  PROCEED_TO,
  QUESTION,
  GOTO,
  NEGATIVE_IF,
  POSITIVE_IF,
  OUTPUT, INPUT,
  PUSH, POP,
  BREAKPOINT,

  PLAY;

public static LexerlessGrammar create() {
    CaseInsensitiveLexerlessGrammarBuilder b = CaseInsensitiveLexerlessGrammarBuilder.create();

    b.rule(BE).is(b.firstOf("am", "are", "art", "be", "is"), WHITESPACE);
    b.rule(ARTICLE).is(b.firstOf("an", "a", "the"), WHITESPACE);
    b.rule(FIRST_PERSON).is(b.firstOf("I", "me"));
    b.rule(FIRST_PERSON_REFLEXIVE).is("myself");
    b.rule(FIRST_PERSON_POSSESSIVE).is(b.firstOf("mine", "my"), WHITESPACE);
    b.rule(SECOND_PERSON).is(b.firstOf("thee", "thou", "you"));
    b.rule(SECOND_PERSON_REFLEXIVE).is(b.firstOf("thyself", "yourself"));
    b.rule(SECOND_PERSON_POSSESSIVE).is(b.firstOf("thine", "thy", "your"), WHITESPACE);
    b.rule(THIRD_PERSON_POSSESSIVE).is(b.firstOf("his", "her", "its", "their"), WHITESPACE);
    b.rule(NEGATIVE_ADJECTIVE).is(b.firstOf(
        "bad", "cowardly", "cursed", "damned", "dirty", "disgusting",
        "distasteful", "dusty", "evil", "fat-kidneyed", "fatherless", "fat", "foul", "hairy", "half-witted",
        "horrible", "horrid", "infected", "lying", "miserable", "misused", "oozing", "rotten", "smelly", "snotty",
        "sorry", "stinking", "stuffed", "stupid", "vile", "villainous", "worried"
    ), WHITESPACE);
    b.rule(NEUTRAL_ADJECTIVE).is(b.firstOf(
        "big", "black", "bluest", "blue", "bottomless", "furry",
        "green", "hard", "huge", "large", "little", "normal", "old", "purple", "red", "rural", "small",
        "tiny", "white", "yellow"
    ), WHITESPACE);
    b.rule(POSITIVE_ADJECTIVE).is(b.firstOf(
        "amazing", "beautiful", "blossoming", "bold", "brave",
        "charming", "clearest", "cunning", "cute", "delicious", "embroidered", "fair", "fine", "gentle",
        "golden", "good", "handsome", "happy", "healthy", "honest", "lovely", "loving", "mighty", "noble",
        "peaceful", "pretty", "prompt", "proud", "reddest", "rich", "smooth", "sunny", "sweetest", "sweet",
        "trustworthy", "warm"
    ), WHITESPACE);
    b.rule(NEGATIVE_NOUN).is(b.firstOf(
        "Hell", "Microsoft", "bastard", "beggar", "blister", "codpiece",
        "coward", "curse", "death", "devil", "draught", "famine", "flirt-gill", "goat", "hate", "hog", "hound",
        "leech", "lie", "pig", "plague", "starvation", "toad", "war", "wolf"
      )
    );
    b.rule(NEUTRAL_NOUN).is(b.firstOf(
        "animal", "aunt", "brother", "cat", "chihuahua", "cousin", "cow",
        "daughter", "door", "face", "father", "fellow", "granddaughter", "grandfather", "grandmother", "grandson",
        "hair", "hamster", "horse", "lamp", "lantern", "mistletoe", "moon", "morning", "mother", "nephew", "niece",
        "nose", "purse", "road", "roman", "sister", "sky", "son", "squirrel", "stone wall", "thing", "town", "tree",
        "uncle", "wind"
      )
    );
    b.rule(POSITIVE_NOUN).is(b.firstOf(
        "Heaven", "Lord", "angel", "flower", "happiness", "joy", "plum",
        b.sequence("summer's", WHITESPACE, "day"),
        "hero", "rose", "kingdom", "King", "pony"
    )
    );

    b.rule(PERSONA_NAME).is(b.firstOf(
        "Achilles", "Adonis", "Adriana", "Aegeon", "Aemilia", "Agamemnon", "Agrippa", "Ajax", "Alonso", "Andromache",
        "Angelo", "Antiochus", "Antonio", "Arthur", "Autolycus", "Balthazar", "Banquo", "Beatrice", "Benedick",
        "Benvolio", "Bianca", "Brabantio", "Brutus", "Capulet", "Cassandra", "Cassius",
        b.sequence("Christopher", WHITESPACE, "Sly"), "Cicero", "Claudio", "Claudius", "Cleopatra", "Cordelia",
        "Cornelius", "Cressida", "Cymberline", "Demetrius", "Desdemona", "Dionyza",
        b.sequence("Doctor", WHITESPACE, "Caius"), "Dogberry", b.sequence("Don", WHITESPACE, "John"),
        b.sequence("Don", WHITESPACE, "Pedro"), "Donalbain", "Dorcas", "Duncan", "Egeus", "Emilia", "Escalus",
        "Falstaff", "Fenton", "Ferdinand", "Ford", "Fortinbras", "Francisca", b.sequence("Friar", WHITESPACE, "John"),
        b.sequence("Friar", WHITESPACE, "Laurence"), "Gertrude", "Goneril", "Hamlet", "Hecate", "Hector", "Helen",
        "Helena", "Hermia", "Hermonie", "Hippolyta", "Horatio", "Imogen", "Isabella",
        b.sequence("John", WHITESPACE, "of", WHITESPACE, "Gaunt"),
        b.sequence("John", WHITESPACE, "of", WHITESPACE, "Lancaster"), "Julia", "Juliet",
        b.sequence("Julius", WHITESPACE, "Caesar"), b.sequence("King", WHITESPACE, "Henry"),
        b.sequence("King", WHITESPACE, "John"), b.sequence("King", WHITESPACE, "Lear"),
        b.sequence("King", WHITESPACE, "Richard"), b.sequence("Lady", WHITESPACE, "Capulet"),
        b.sequence("Lady", WHITESPACE, "Macbeth"), b.sequence("Lady", WHITESPACE, "Macduff"),
        b.sequence("Lady", WHITESPACE, "Montague"), "Lennox", "Leonato", "Luciana", "Lucio", "Lychorida", "Lysander",
        "Macbeth", "Macduff", "Malcolm", "Mariana", b.sequence("Mark", WHITESPACE, "Antony"), "Mercutio", "Miranda",
        b.sequence("Mistress", WHITESPACE, "Ford"), b.sequence("Mistress", WHITESPACE, "Overdone"),
        b.sequence("Mistress", WHITESPACE, "Page"), "Montague", "Mopsa", "Oberon", "Octavia",
        b.sequence("Octavius", WHITESPACE, "Caesar"), "Olivia", "Ophelia", "Orlando", "Orsino", "Othello", "Page",
        "Pantino", "Paris", "Pericles", "Pinch", "Polonius", "Pompeius", "Portia", "Priam",
        b.sequence("Prince", WHITESPACE, "Henry", "Prospero"), "Proteus", "Publius", "Puck",
        b.sequence("Queen", WHITESPACE, "Elinor"), "Regan", "Robin", "Romeo", "Rosalind", "Sebastian", "Shallow",
        "Shylock", "Slender", "Solinus", "Stephano", "Thaisa",
        b.sequence("The", WHITESPACE, "Abbot", WHITESPACE, "of", WHITESPACE, "Westminster"),
        b.sequence("The", WHITESPACE, "Apothecary"),
        b.sequence("The", WHITESPACE, "Archbishop", WHITESPACE, "of", WHITESPACE, "Canterbury"),
        b.sequence("The", WHITESPACE, "Duke", WHITESPACE, "of", WHITESPACE, "Milan"),
        b.sequence("The", WHITESPACE, "Duke", WHITESPACE, "of", WHITESPACE, "Venice"),
        b.sequence("The", WHITESPACE, "Ghost"), "Theseus", "Thurio", "Timon", "Titania", "Titus", "Troilus", "Tybalt",
        "Ulysses", "Valentine", "Venus", "Vincentio", "Viola"
      )
    );

    // b.rule(PERSONA_NAME).is(b.regexp("((?!Act)[A-Za-z])+(\\s+[A-Za-z]+)*"));

    b.rule(NOTHING).is(b.firstOf("nothing", "zero"));

    b.rule(POSSESSIVE).is(b.firstOf(FIRST_PERSON_POSSESSIVE, SECOND_PERSON_POSSESSIVE, THIRD_PERSON_POSSESSIVE));
    b.rule(POSITIVE_ADVERB).is(b.firstOf("better", "bigger", "fresher", "friendlier", "nicer", "jollier", "more"));
    b.rule(POSITIVE_COMPARATIVE).is(POSITIVE_ADVERB, WHITESPACE, POSITIVE_ADJECTIVE, "than");
    b.rule(NEGATIVE_ADVERB).is(b.firstOf("punier", "smaller", "worse", "more"));
    b.rule(NEGATIVE_COMPARATIVE).is(NEGATIVE_ADVERB, WHITESPACE, NEGATIVE_ADJECTIVE, "than");

    b.rule(POSITIVE_OR_NEUTRAL_ADJECTIVE).is(b.firstOf(POSITIVE_ADJECTIVE, NEUTRAL_ADJECTIVE));
    b.rule(POSITIVE_OR_NEUTRAL_NOUN).is(b.firstOf(POSITIVE_NOUN, NEUTRAL_NOUN));

    b.rule(NEUTRAL_COMPARATIVE).is("as", WHITESPACE, b.firstOf(NEGATIVE_ADJECTIVE, POSITIVE_OR_NEUTRAL_ADJECTIVE), "as");
    
    b.rule(NEGATIVE_NOUN_PHRASE).is(b.optional(b.firstOf(ARTICLE, POSSESSIVE)), b.zeroOrMore(b.firstOf(NEGATIVE_ADJECTIVE, NEUTRAL_ADJECTIVE)), NEGATIVE_NOUN);

    b.rule(POSITIVE_NOUN_PHRASE).is(b.optional(b.firstOf(ARTICLE, POSSESSIVE)), b.zeroOrMore(POSITIVE_OR_NEUTRAL_ADJECTIVE), POSITIVE_OR_NEUTRAL_NOUN);
    b.rule(NOUN_PHRASE).is(b.firstOf(NEGATIVE_NOUN_PHRASE, POSITIVE_NOUN_PHRASE));
    b.rule(FIRST_PERSON_VALUE).is(b.firstOf(FIRST_PERSON_REFLEXIVE, FIRST_PERSON));
    b.rule(SECOND_PERSON_VALUE).is(b.firstOf(SECOND_PERSON_REFLEXIVE, SECOND_PERSON));

    b.rule(VALUE).is(b.firstOf(EXPRESSION, PERSONA_NAME, NOUN_PHRASE, FIRST_PERSON_VALUE, SECOND_PERSON_VALUE, NOTHING));
    // b.rule(VALUE).is(NEGATIVE_NOUN_PHRASE);
    
    b.rule(BINARY_OPERATION).is(b.firstOf(
      b.sequence("the", WHITESPACE, "difference", WHITESPACE, "between"),
      b.sequence("the", WHITESPACE, "product", WHITESPACE, "of"),
      b.sequence("the", WHITESPACE, "quotient", WHITESPACE, "between"),
      b.sequence("the", WHITESPACE, "remainder", WHITESPACE, "of", WHITESPACE, "the", WHITESPACE, "quotient", WHITESPACE, "between"),
      b.sequence("the", WHITESPACE, "sum", WHITESPACE, "of")
    ));
    b.rule(BINARY_EXPRESSION).is(BINARY_OPERATION, WHITESPACE, VALUE, WHITESPACE, "and", WHITESPACE, VALUE);
    b.rule(UNARY_OPERATION).is(b.firstOf(
      b.sequence("the", WHITESPACE, "cube", WHITESPACE, "of"),
      b.sequence("the", WHITESPACE, "factorial", WHITESPACE, "of"),
      b.sequence("the", WHITESPACE, "square", WHITESPACE, "of"),
      b.sequence("the", WHITESPACE, "square", WHITESPACE, "root", WHITESPACE, "of"),
        "twice"
    ));
    b.rule(UNARY_EXPRESSION).is(UNARY_OPERATION, WHITESPACE, VALUE);
    b.rule(EXPRESSION).is(b.firstOf(BINARY_EXPRESSION, UNARY_EXPRESSION));


    b.rule(LET_US).is(
        "Let", WHITESPACE, "us",
        "We", WHITESPACE, "shall",
        "We", WHITESPACE, "must"
    );
    b.rule(PROCEED_TO).is(
      b.sequence("proceed", WHITESPACE, "to"),
      b.sequence("return", WHITESPACE, "to")
    );
    b.rule(NEGATIVE_IF).is(
      b.sequence("If", WHITESPACE, "not,")
    );
    b.rule(POSITIVE_IF).is(
      b.sequence("If", WHITESPACE, "so,")
    );
    b.rule(GOTO).is(b.firstOf(NEGATIVE_IF, POSITIVE_IF), WHITESPACE, LET_US, WHITESPACE, PROCEED_TO, WHITESPACE, "scene", WHITESPACE, ROMAN_NUMBER, END);

    b.rule(OUTPUT).is(b.firstOf(
      b.sequence("Open", WHITESPACE, SECOND_PERSON_POSSESSIVE, "heart"),
      b.sequence("Speak", WHITESPACE, SECOND_PERSON_POSSESSIVE, "mind")
    ), END);
    b.rule(INPUT).is(b.firstOf(
      b.sequence(
        b.sequence("Listen", WHITESPACE, "to"), WHITESPACE, SECOND_PERSON_POSSESSIVE, "heart"),
      b.sequence("Open", WHITESPACE, SECOND_PERSON_POSSESSIVE, "mind")
    ), END);
    b.rule(PUSH).is("Remember", WHITESPACE, VALUE, END);
    b.rule(POP).is("Recall", WHITESPACE, ANY_TEXT, END);


    b.rule(WHITESPACE).is(b.regexp("\\s+"));
    b.rule(OPT_WHITESPACE).is(b.regexp("\\s*"));
    b.rule(DOT).is(".");
    b.rule(EXCLAMATION).is("!");
    b.rule(QUESTION_MARK).is(OPT_WHITESPACE, "?", OPT_WHITESPACE);
    b.rule(END).is(OPT_WHITESPACE, b.firstOf(DOT, EXCLAMATION), OPT_WHITESPACE);
    b.rule(QEND).is(QUESTION_MARK, OPT_WHITESPACE);
    b.rule(COLUMN).is(":");
    b.rule(COMMA).is(",");
    b.rule(ANY_TEXT).is(b.regexp("[^!.]*"));

    b.rule(ROMAN_NUMBER).is(b.regexp("(?=[MDCLXVI])M*D?C{0,4}L?X{0,4}V?I{0,4}"));
    
    b.rule(TITLE).is(b.regexp("[^!.]*"), END);
    
    b.rule(PERSONA_DESCRIPTION).is(ANY_TEXT, END);
    b.rule(PERSONA_DECLARATION).is(PERSONA_NAME, OPT_WHITESPACE, COMMA, ANY_TEXT, END);
    b.rule(DRAMATIS_PERSONAE).is(b.oneOrMore(PERSONA_DECLARATION));

    b.rule(ACT).is("Act", WHITESPACE, ROMAN_NUMBER, OPT_WHITESPACE, COLUMN, ANY_TEXT, END);
    b.rule(SCENE).is("Scene", WHITESPACE, ROMAN_NUMBER, OPT_WHITESPACE, COLUMN, ANY_TEXT, END);

    b.rule(PERSONAE_LIST).is(PERSONA_NAME, b.zeroOrMore(COMMA, OPT_WHITESPACE, PERSONA_NAME), b.optional(WHITESPACE, "and", WHITESPACE, PERSONA_NAME));
    b.rule(ENTER).is("[", OPT_WHITESPACE, "Enter", WHITESPACE, PERSONAE_LIST, OPT_WHITESPACE, "]");
    b.rule(EXIT).is("[", OPT_WHITESPACE, "Exit", WHITESPACE, PERSONAE_LIST, OPT_WHITESPACE, "]");
    b.rule(EXEUNT).is("[", OPT_WHITESPACE, "Exeunt", b.optional(WHITESPACE, PERSONAE_LIST), OPT_WHITESPACE, "]");
    
    b.rule(BREAKPOINT).is("[", OPT_WHITESPACE, "A", WHITESPACE, "pause", OPT_WHITESPACE, "]");

    b.rule(QUESTION).is(BE, VALUE, b.firstOf(POSITIVE_COMPARATIVE, NEUTRAL_COMPARATIVE, NEGATIVE_COMPARATIVE), VALUE, QEND);
    b.rule(ASSIGNMENT).is(
      SECOND_PERSON, WHITESPACE,
      b.optional(BE, "as", WHITESPACE, b.firstOf(POSITIVE_OR_NEUTRAL_ADJECTIVE,  NEGATIVE_ADJECTIVE), "as", WHITESPACE),
      VALUE, END
    );
    // b.rule(ASSIGNMENT).is(
    //   b.firstOf("test-test", "bla-bla"), END
    // );

    b.rule(SENTENCE).is(b.firstOf(QUESTION, ASSIGNMENT, GOTO, OUTPUT, INPUT, PUSH, POP));
    // b.rule(SENTENCE).is(ASSIGNMENT);

    b.rule(LINE).is(PERSONA_NAME, OPT_WHITESPACE, COLUMN, OPT_WHITESPACE, b.oneOrMore(SENTENCE));

    b.rule(EVENT).is(b.firstOf(LINE, BREAKPOINT, ENTER, EXIT, EXEUNT), OPT_WHITESPACE);
    // b.rule(EVENT).is(b.firstOf(LINE, ENTER, EXIT, EXEUNT), OPT_WHITESPACE);

    b.rule(PLAY).is(TITLE, DRAMATIS_PERSONAE, b.oneOrMore(ACT, b.oneOrMore(SCENE, b.oneOrMore(EVENT))));

    b.setRootRule(PLAY);
    return b.build();
  }
}
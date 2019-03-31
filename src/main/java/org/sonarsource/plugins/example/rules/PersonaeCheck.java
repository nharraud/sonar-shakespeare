package org.sonarsource.plugins.example.rules;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonarsource.plugins.example.sensors.ShakespeareRulesDefinition;
import org.sonarsource.plugins.example.shakespeare.ShakespeareGrammar;
import org.sonarsource.plugins.example.shakespeare.ShakespeareVisitor;
import org.sonarsource.plugins.example.utils.AstNodeLocation;

/**
 * PersonaeCheck
 */
public class PersonaeCheck extends ShakespeareVisitor {
  public final static RuleKey KEY = RuleKey.of(ShakespeareRulesDefinition.REPO_KEY, "PersonaeRule");

  public final static String NO_WEAPONS = "Don't give them any weapon";
  public final static String NO_POINSON = "Don't give them any poison";
  
  private final Map<String, Collection<PersonaeWarning>> warnings = Map.of(
    "Romeo", Arrays.asList(
      new PersonaeWarning(NO_POINSON, "Juliet"),
      new PersonaeWarning(NO_WEAPONS, "Tybalt")
    ),
    "Tybalt", Arrays.asList(
      new PersonaeWarning(NO_WEAPONS, "Mercutio")
    )
  );

  class PersonaeWarning {
    private final Set<String> personae;
    private final String message;

    protected PersonaeWarning(String message, String... personae) {
      this.personae = Set.of(personae);
      this.message = message;
    }
  }

  public PersonaeCheck(SensorContext context, InputFile file) {
    super(context, file);
  }

  @Override
  public List<AstNodeType> getAstNodeTypesToVisit() {
    return Arrays.asList(ShakespeareGrammar.DRAMATIS_PERSONAE);
  }

  @Override
  public void visitNode(AstNode ast) {
    Map<String, AstNode> allPersonae = new HashMap<>();
    ast.getChildren().forEach((persona) -> {
      AstNode name = persona.getFirstChild();
      allPersonae.put(name.getTokenValue(), name);
    });

    allPersonae.forEach((persona, node) -> {
      Collection<PersonaeWarning> personaeWarnings =  warnings.get(persona);
      if (personaeWarnings != null) {
        for (PersonaeWarning warning: personaeWarnings) {
          for (String otherPersona: warning.personae) {
            AstNode otherNode = allPersonae.get(otherPersona);
            if (otherNode != null) {
              NewIssue newIssue = context.newIssue().forRule(KEY);
              NewIssueLocation primaryLocation = newIssue.newLocation()
                  .on(file)
                  .message(warning.message);
              primaryLocation.at(AstNodeLocation.buildLocation(node, this.file));

              NewIssueLocation secondaryLocation = newIssue.newLocation()
              .on(file);
              secondaryLocation.at(AstNodeLocation.buildLocation(otherNode, this.file));
              newIssue.at(primaryLocation);
              newIssue.addLocation(secondaryLocation);
  
              newIssue.save();
            }
          }
        }
      }
    });
  }
}
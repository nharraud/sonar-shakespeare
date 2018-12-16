package org.sonarsource.plugins.example.sensors;

import java.util.List;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

/**
 * ShakespeareScanner
 */
public class ShakespeareScanner {

  private static final Logger LOGGER = Loggers.get(ShakespeareScanner.class);

  private List<InputFile> inputFiles;
  private SensorContext context;
  private FileLinesContextFactory fileLinesContextFactory;
  private NoSonarFilter noSonarFilter;

  public ShakespeareScanner(SensorContext context, FileLinesContextFactory fileLinesContextFactory,
      NoSonarFilter noSonarFilter, List<InputFile> inputFiles) {
    this.inputFiles = inputFiles;
    this.context = context;
    this.fileLinesContextFactory = fileLinesContextFactory;
    this.noSonarFilter = noSonarFilter;
  }

  public void scanFiles() {
    for (InputFile pythonFile : inputFiles) {
      if (context.isCancelled()) {
        return;
      }
      scanFile(pythonFile);
    }
  }

  private void scanFile(InputFile inputFile) {
    LOGGER.warn("Scanning file " + inputFile.filename());
    // RuleKey ruleKey = RuleKey.of(ShakespeareRulesDefinition.REPO_NAME, "ShakespeareRule1");
    RuleKey ruleKey = RuleKey.of(inputFile.language() + '-' + ShakespeareRulesDefinition.KEY, "ShakespeareRule1");
    LOGGER.warn("RULE KEY :" + ruleKey.toString());
    NewIssue newIssue = context.newIssue().forRule(ruleKey);

    NewIssueLocation primaryLocation = newIssue.newLocation()
    .on(inputFile)
    .message("This is a message");
    int line = 1;
    if (line > 0) {
      primaryLocation.at(inputFile.selectLine(line));
    }
    newIssue.at(primaryLocation);

    newIssue.save();
  }

}
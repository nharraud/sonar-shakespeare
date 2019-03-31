/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.example.sensors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Configuration;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.example.languages.ShakespeareLanguage;

/**
 * The goal of this Sensor is to load the results of an analysis performed by a
 * fictive external tool named: FooLint Results are provided as an xml file and
 * are corresponding to the rules defined in 'rules.xml'. To be very abstract,
 * these rules are applied on source files made with the fictive language Foo.
 */
public class ShakespeareSensor implements Sensor {

  private static final Logger LOGGER = Loggers.get(ShakespeareSensor.class);

  protected final Configuration config;
  protected final FileSystem fileSystem;
  protected SensorContext context;

  private final FileLinesContextFactory fileLinesContextFactory;
  private final NoSonarFilter noSonarFilter;

  /**
   * Use of IoC to get Settings, FileSystem, RuleFinder and ResourcePerspectives
   */
  public ShakespeareSensor(final Configuration config, final FileSystem fileSystem,
      FileLinesContextFactory fileLinesContextFactory, CheckFactory checkFactory, NoSonarFilter noSonarFilter) {
    this.config = config;
    this.fileSystem = fileSystem;

    this.fileLinesContextFactory = fileLinesContextFactory;
    this.noSonarFilter = noSonarFilter;
  }

  @Override
  public void describe(final SensorDescriptor descriptor) {
    descriptor.name("Shakespeare Sensor");
    descriptor.onlyOnLanguage(ShakespeareLanguage.KEY);
  }

  @Override
  public void execute(final SensorContext context) {
    LOGGER.warn("Executing Sensor " + ShakespeareSensor.class.getName());
    FilePredicates p = context.fileSystem().predicates();
    Iterable<InputFile> it = context.fileSystem()
        .inputFiles(p.and(p.hasType(InputFile.Type.MAIN), p.hasLanguage(ShakespeareLanguage.KEY)));
    List<InputFile> list = new ArrayList<>();
    it.forEach(list::add);
    List<InputFile> inputFiles = Collections.unmodifiableList(list);

    ShakespeareScanner scanner = new ShakespeareScanner(context
        , fileLinesContextFactory, noSonarFilter, inputFiles);
    scanner.scanFiles();
  }
}

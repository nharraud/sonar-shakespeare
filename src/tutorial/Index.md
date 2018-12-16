# SonarQube analyzer plugin Tutorial

## Introduction

This document explains step by step how to create an analyzer.

We will follow the recommended way which is to clone the GitHub repository: https://github.com/SonarSource/sonar-custom-plugin-example

This provides an example for every feature a plugin can provide.

It is also possible to [start  a plugin from scratch](start_from_scratch.md)

## Start your first analysis

Make sure that SonarQube is running. Go to the `tests` directory inside the `sonar-custom-plugin-example` project and run the  [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner).

**TODO: add the tests directory**

You should now have a new project in SonarQube with [the file `hello.foo` analyzed](http://localhost:9000/code?id=my%3Aproject&selected=my%3Aproject%3Atest.foo).
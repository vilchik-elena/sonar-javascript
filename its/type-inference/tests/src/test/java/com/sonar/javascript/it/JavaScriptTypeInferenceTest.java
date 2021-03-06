/*
 * SonarSource :: JavaScript :: ITs :: TypeInference :: Tests
 * Copyright (C) 2015 ${owner}
 * sonarqube@googlegroups.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.sonar.javascript.it;

import com.google.common.io.Files;
import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarRunner;
import com.sonar.orchestrator.locator.FileLocation;
import com.sonar.orchestrator.locator.MavenLocation;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.fest.assertions.Assertions.assertThat;

public class JavaScriptTypeInferenceTest {

  private static final String PLUGIN_KEY = "javascript";
  @ClassRule
  public static Orchestrator orchestrator = Orchestrator.builderEnv()
    .addPlugin(PLUGIN_KEY)
    .setMainPluginKey(PLUGIN_KEY)
    .addPlugin(MavenLocation.create("org.sonarsource.sonar-lits-plugin", "sonar-lits-plugin", "0.5-SNAPSHOT"))
    .addPlugin(FileLocation.of(TestUtils.pluginJar("javascript-type-inference-plugin")))
    .restoreProfileAtStartup(FileLocation.ofClasspath("/profile.xml"))
    .build();

  @Test
  public void test() throws Exception {
    File litsDifferencesFile = FileLocation.of("target/differences").getFile();

    SonarRunner build = SonarRunner.create(TestUtils.projectDir(""))
      .setProjectKey("project")
      .setProjectName("project")
      .setProjectVersion("1")
      .setSourceDirs(".")
      .setSourceEncoding("utf-8")
      .setProperty("sonar.analysis.mode", "preview")
      .setProperty("sonar.issuesReport.html.enable", "true")
      .setProperty("dump.old", TestUtils.file("tests/src/expected").getAbsolutePath())
      .setProperty("dump.new", TestUtils.file("tests/target/actual").getAbsolutePath())
      .setProperty("lits.differences", litsDifferencesFile.getAbsolutePath())
      .setProperty("sonar.cpd.skip", "true")
      .setProfile("javascript-type-inference")
      .setEnvironmentVariable("SONAR_RUNNER_OPTS", "-Xmx1024m");
    orchestrator.executeBuild(build);

    assertThat(Files.toString(litsDifferencesFile, StandardCharsets.UTF_8)).isEmpty();
  }
}

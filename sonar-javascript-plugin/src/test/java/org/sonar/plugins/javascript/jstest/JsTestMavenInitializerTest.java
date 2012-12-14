/*
 * Sonar JavaScript Plugin
 * Copyright (C) 2011 Eriks Nukis and SonarSource
 * dev@sonar.codehaus.org
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
package org.sonar.plugins.javascript.jstest;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;
import org.sonar.plugins.javascript.JavaScriptPlugin;
import org.sonar.plugins.javascript.core.JavaScript;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class JsTestMavenInitializerTest {

  private JavaScript language;
  private Settings settings;
  private JsTestMavenPluginHandler mavenPluginHandler;
  private JsTestMavenInitializer mavenInitializer;

  @Before
  public void setUp() {
    settings = new Settings();
    language = new JavaScript(settings);
    mavenPluginHandler = mock(JsTestMavenPluginHandler.class);
    mavenInitializer = new JsTestMavenInitializer(mavenPluginHandler, language);
  }

  @Test
  public void test_shouldExecuteOnProject() {
    Project project = mockProject();

    project.setAnalysisType(Project.AnalysisType.STATIC);
    assertThat(mavenInitializer.shouldExecuteOnProject(project)).isFalse();

    project.setAnalysisType(Project.AnalysisType.DYNAMIC);
    assertThat(mavenInitializer.shouldExecuteOnProject(project)).isFalse();

    project.setLanguage(language);
    project.setAnalysisType(Project.AnalysisType.STATIC);
    assertThat(mavenInitializer.shouldExecuteOnProject(project)).isFalse();

    project.setAnalysisType(Project.AnalysisType.DYNAMIC);
    assertThat(mavenInitializer.shouldExecuteOnProject(project)).isFalse();

    settings.setProperty(JavaScriptPlugin.TEST_FRAMEWORK_KEY, "jstest");
    assertThat(mavenInitializer.shouldExecuteOnProject(project)).isTrue();
  }

  @Test
  public void test_getMavenPluginHandler() {
    Project project = mockProject();
    assertThat(mavenInitializer.getMavenPluginHandler(project)).isSameAs(mavenPluginHandler);

    project.setPackaging("pom");
    assertThat(mavenInitializer.getMavenPluginHandler(project)).isNull();
  }

  private Project mockProject() {
    return new Project("mock");
  }

}

/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
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
package org.sonar.javascript.checks;

import com.google.common.collect.Iterables;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.statement.SwitchClauseTree;
import org.sonar.plugins.javascript.api.tree.statement.SwitchStatementTree;
import org.sonar.plugins.javascript.api.visitors.BaseTreeVisitor;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "SwitchWithoutDefault",
  name = "\"switch\" statements should end with a \"default\" clause",
  priority = Priority.MAJOR,
  tags = {Tags.CERT, Tags.CWE, Tags.MISRA})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("5min")
public class SwitchWithoutDefaultCheck extends BaseTreeVisitor {

  @Override
  public void visitSwitchStatement(SwitchStatementTree tree) {
    if (!hasDefaultCase(tree)) {
      getContext().addIssue(this, tree, "Avoid switch statement without a \"default\" clause.");

    } else if (!Iterables.getLast(tree.cases()).is(Kind.DEFAULT_CLAUSE)) {
      getContext().addIssue(this, tree, "\"default\" clause should be the last one.");
    }
    super.visitSwitchStatement(tree);
  }

  private static boolean hasDefaultCase(SwitchStatementTree switchStmt) {
    for (SwitchClauseTree clause : switchStmt.cases()) {

      if (clause.is(Kind.DEFAULT_CLAUSE)) {
        return true;
      }
    }
    return false;
  }

}

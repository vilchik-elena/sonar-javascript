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

import com.google.common.collect.ImmutableList;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.javascript.checks.utils.SubscriptionBaseVisitor;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.expression.LiteralTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Rule(
  key = "S2611",
  name = "Untrusted content should not be included",
  priority =  Priority.CRITICAL,
  tags = {Tags.CWE, Tags.SECURITY, Tags.SANS_TOP25_RISKY}
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SECURITY_FEATURES)
@SqaleConstantRemediation("15min")
public class UntrustedContentCheck extends SubscriptionBaseVisitor {

  private static final String MESSAGE = "Remove this content from an untrusted source.";

  @RuleProperty (
          key = "domainsToIgnore",
          description = "Comma-delimited list of domains to ignore. Regexes may be used, e.g. (.*\\.)?example.com,foo.org"
  )
  public String domainsToIgnore = "";
  private List<Pattern> patterns = null;

  public String getDomainsToIgnore() {
    return domainsToIgnore;
  }


  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.STRING_LITERAL);
  }

  @Override
  public void visitFile(Tree tree) {

    patterns = new ArrayList<>();
    String[] pieces = domainsToIgnore.split(",");
    for (String piece : pieces) {
      patterns.add(Pattern.compile(piece));
    }
  }

  @Override
  public void visitNode(Tree tree) {

    LiteralTree literal = (LiteralTree) tree;

    String value = literal.value();
    value = value.substring(1, value.length() - 1);
    if (value.matches("^http.*")) {
      try {
        URI uri = new URI(value);
        if ( isBad(uri) ) {
          getContext().addIssue(this, tree, MESSAGE);
        }
      } catch (URISyntaxException e) {
        // we don't consider uri, which could not be parsed
      }
    }
  }

  private boolean isBad(URI uri) {

    for (Pattern pattern : patterns) {
      String host = uri.getHost();
      if (host == null || pattern.matcher(host).matches()) {
        return false;
      }
    }
    return true;
  }
}

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
package org.sonar.plugins.javascript.api.tree.declaration;

import com.google.common.annotations.Beta;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;

import javax.annotation.Nullable;

/**
 * Interface for types <a href="https://people.mozilla.org/~jorendorff/es6-draft.html#sec-exports">Named and Default Imports</a>
 * (<a href="http://wiki.ecmascript.org/doku.php?id=harmony:specification_drafts">ES6</a>).
 * <pre>
 *    import {@link #importClause()} {@link #fromClause()} ;
 * </pre>
 */
@Beta
public interface ImportDeclarationTree extends DeclarationTree {

  SyntaxToken importToken();

  DeclarationTree importClause();

  FromClauseTree fromClause();

  @Nullable
  SyntaxToken semicolonToken();

}

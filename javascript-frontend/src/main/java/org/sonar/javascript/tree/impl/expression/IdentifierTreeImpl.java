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
package org.sonar.javascript.tree.impl.expression;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import org.sonar.plugins.javascript.api.symbols.TypeSet;
import org.sonar.javascript.tree.impl.JavaScriptTree;
import org.sonar.javascript.tree.impl.lexical.InternalSyntaxToken;
import org.sonar.plugins.javascript.api.symbols.Symbol;
import org.sonar.plugins.javascript.api.symbols.Type;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.javascript.api.visitors.TreeVisitor;

import java.util.Iterator;

public class IdentifierTreeImpl extends JavaScriptTree implements IdentifierTree {

  private final InternalSyntaxToken nameToken;
  private final Kind kind;
  private Symbol symbol = null;
  private TypeSet types = TypeSet.emptyTypeSet();

  public IdentifierTreeImpl(Kind kind, InternalSyntaxToken nameToken) {
    this.kind = kind;
    this.nameToken = Preconditions.checkNotNull(nameToken);
  }

  @Override
  public Kind getKind() {
    return kind;
  }

  @Override
  public SyntaxToken identifierToken() {
    return nameToken;
  }

  @Override
  public String name() {
    return identifierToken().text();
  }

  @Override
  public String toString() {
    return name();
  }

  @Override
  public Symbol symbol() {
    return symbol;
  }

  public void setSymbol(Symbol symbol) {
    this.symbol = symbol;
  }

  @Override
  public TypeSet types() {
    if (symbol == null){
      return types.immutableCopy();
    } else {
      return symbol.types();
    }
  }

  public void addType(Type type){
    if (symbol == null){
      types.add(type);
    } else {
      symbol.addType(type);
    }
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.<Tree>singletonIterator(nameToken);
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitIdentifier(this);
  }
}

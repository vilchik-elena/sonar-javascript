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
package org.sonar.javascript.tree.symbols.type;

import org.sonar.plugins.javascript.api.tree.declaration.FunctionTree;

public class FunctionType extends ObjectType {

  private FunctionTree functionTree;

  @Override
  public Kind kind() {
    return Kind.FUNCTION;
  }

  @Override
  public Callability callability() {
    return Callability.CALLABLE;
  }

  public static FunctionType create(FunctionTree functionTree){
    FunctionType type = new FunctionType();
    type.functionTree = functionTree;
    return type;
  }

  public FunctionTree functionTree(){
    return functionTree;
  }
}

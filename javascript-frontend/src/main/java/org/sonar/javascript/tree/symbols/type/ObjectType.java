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

import org.sonar.plugins.javascript.api.symbols.Type;

public class ObjectType implements Type {


  private Callability callability;

  @Override
  public Kind kind() {
    return Kind.OBJECT;
  }

  @Override
  public Callability callability() {
    return callability;
  }

  protected ObjectType(){
    this.callability = Callability.UNKNOWN;
  }

  public static ObjectType create(){
    return new ObjectType();
  }
  public static ObjectType create(Callability callability){
    ObjectType objectType = new ObjectType();
    objectType.callability = callability;
    return objectType;
  }

  @Override
  public String toString() {
    return this.kind().name();
  }

  public enum FrameworkType implements Type {
    JQUERY_SELECTOR_OBJECT {
      @Override
      public Kind kind() {
        return Kind.JQUERY_SELECTOR_OBJECT;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
    JQUERY_OBJECT {
      @Override
      public Kind kind() {
        return Kind.JQUERY_OBJECT;
      }

      @Override
      public Callability callability() {
        return Callability.CALLABLE;
      }
    },
    BACKBONE_MODEL {
      @Override
      public Kind kind() {
        return Kind.BACKBONE_MODEL;
      }

      @Override
      public Callability callability() {
        return Callability.CALLABLE;
      }
    },
    BACKBONE_MODEL_OBJECT {
      @Override
      public Kind kind() {
        return Kind.BACKBONE_MODEL_OBJECT;
      }

      @Override
      public Callability callability() {
        return Callability.UNKNOWN;
      }
    },
  }

  public enum BuiltInObjectType implements Type {
    NUMBER {
      @Override
      public Kind kind() {
        return Kind.NUMBER_OBJECT;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
    STRING {
      @Override
      public Kind kind() {
        return Kind.STRING_OBJECT;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
    BOOLEAN {
      @Override
      public Kind kind() {
        return Kind.BOOLEAN_OBJECT;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
    DATE {
      @Override
      public Kind kind() {
        return Kind.DATE;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
  }

  public enum WebApiType implements Type {
    WINDOW {
      @Override
      public Kind kind() {
        return Kind.WINDOW;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
    DOCUMENT {
      @Override
      public Kind kind() {
        return Kind.DOCUMENT;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },
    DOM_ELEMENT {
      @Override
      public Kind kind() {
        return Kind.DOM_ELEMENT;
      }

      @Override
      public Callability callability() {
        return Callability.NON_CALLABLE;
      }
    },

  }
}

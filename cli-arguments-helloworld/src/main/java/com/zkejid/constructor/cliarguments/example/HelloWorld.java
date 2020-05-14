package com.zkejid.constructor.cliarguments.example;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsFactory;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.ParseResult;
import com.zkejid.constructor.core.api.v1.ConstructionException;
import com.zkejid.constructor.core.api.v1.ConstructorPart;
import com.zkejid.constructor.core.api.v1.EntryPoint;
import com.zkejid.constructor.javasdk.system.api.v1.System;
import com.zkejid.constructor.stringvalue.api.v1.InputValueType;
import com.zkejid.constructor.stringvalue.api.v1.StringValue;
import java.util.Set;

public class HelloWorld implements EntryPoint, ConstructorPart {

  private ArgumentsFactory argumentsFactory;
  private System system;

  @Override
  public Set<Class<?>> getInterfacesNecessary() {
    return Set.of(ArgumentsFactory.class, System.class);
  }

  @Override
  public Set<Class<?>> getInterfacesProvided() {
    return Set.of(EntryPoint.class);
  }

  @Override
  public Object getImplementation(Class<?> aClass) throws ConstructionException {
    if (EntryPoint.class.equals(aClass)) {
      return this;
    } else {
      throw new ConstructionException("Module does not provide " + aClass);
    }
  }

  @Override
  public void putImplementation(Class<?> aClass, Object... objects) {
    if (ArgumentsFactory.class.equals(aClass)) {
      if (objects.length != 1) {
        throw new ConstructionException("Expects exactly 1 implementation. Got " + objects.length);
      }
      argumentsFactory = (ArgumentsFactory) objects[0];
    } else if (System.class.equals(aClass)) {
      if (objects.length != 1) {
        throw new ConstructionException("Expects exactly 1 implementation. Got " + objects.length);
      }
      system = (System) objects[0];
    }
  }

  @Override
  public void verifyNecessaryInterfaces() throws ConstructionException {
    if (argumentsFactory == null) {
      throw new ConstructionException("Expects ArgumentsFactory provided");
    }
    if (system == null) {
      throw new ConstructionException("Expects System provided");
    }
  }

  @Override
  public void main(String[] strings) {
    final ArgumentsParser parser = argumentsFactory.createParser();
    final Argument hasShrike = parser.addFlag("s", "has-shrike");
    final Argument name = parser.addProperty("n", "name");
    final ParseResult parseResult = parser.parse(strings);

    StringBuilder sb = new StringBuilder();
    sb.append("Hello, ");
    final StringValue nameValue = parseResult.getArgumentsParsed().get(name);
    if (nameValue.getInputValueType() == InputValueType.SPECIFIED) {
      sb.append(nameValue.getValue());
    } else {
      sb.append("world");
    }
    if (parseResult.getArgumentsParsed().get(hasShrike).getValue().equals("true")) {
      sb.append("!");
    }

    system.out().println(sb.toString());
  }
}

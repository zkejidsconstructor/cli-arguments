package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.ArgumentsFactory;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.core.api.v1.ConstructionException;
import com.zkejid.constructor.core.api.v1.ConstructorPart;
import com.zkejid.constructor.stringvalue.api.v1.StringValueFactory;
import java.util.Collections;
import java.util.Set;

public class ArgumentsFactoryImpl implements ArgumentsFactory, ConstructorPart {

  private StringValueFactory stringValueFactory;

  @Override
  public ArgumentsParser createParser() {
    return new CommonsCliParser(stringValueFactory);
  }

  @Override
  public Set<Class<?>> getInterfacesNecessary() {
    return Collections.singleton(StringValueFactory.class);
  }

  @Override
  public Set<Class<?>> getInterfacesProvided() {
    return Collections.singleton(ArgumentsFactory.class);
  }

  @Override
  public Object getImplementation(Class<?> aClass) throws ConstructionException {
    if (ArgumentsFactory.class.equals(aClass)) {
      return this;
    } else {
      throw new ConstructionException("Module doesn't provide implementation of " + aClass);
    }
  }

  @Override
  public void putImplementation(Class<?> aClass, Object... objects) {
    if (StringValueFactory.class.isAssignableFrom(aClass)) {
      if (objects.length != 1) {
        throw new ConstructionException(
            "Expect exactly one " + StringValueFactory.class + ", got " + objects.length
        );
      }
      stringValueFactory = (StringValueFactory) objects[0];
    } else {
      throw new ConstructionException("Module doesn't have dependencies");
    }
  }

  @Override
  public void verifyNecessaryInterfaces() throws ConstructionException {
    if (stringValueFactory == null) {
      throw new ConstructionException("Expect " + StringValueFactory.class);
    }
  }
}

package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.ArgumentsFactory;
import com.zkejid.constructor.cliarguments.api.v1.test.ArgumentsFactoryCheckList;
import com.zkejid.constructor.core.api.v1.ConstructionException;
import com.zkejid.constructor.stringvalue.api.v1.StringValueFactory;
import com.zkejid.constructor.stringvalue.impl.StringValueFactoryImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArgumentsFactoryImplTest extends ArgumentsFactoryCheckList {

  @Override
  public ArgumentsFactory getFactoryImplementation() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();
    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());
    return factory;
  }

  @DisplayName("Constructor part requires only one interface StringValueFactory")
  @Test
  void getInterfacesNecessary_call_getTheClass() {
    final Set<Class<?>> expected = Collections.singleton(StringValueFactory.class);
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    final Set<Class<?>> interfacesNecessary = factory.getInterfacesNecessary();

    Assertions.assertEquals(expected, interfacesNecessary);
  }

  @DisplayName("Constructor part provides only one interface ArgumentsParserFactory")
  @Test
  void getInterfacesProvided_call_getTheClass() {
    final Set<Class<?>> expected = Collections.singleton(ArgumentsFactory.class);
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    final Set<Class<?>> interfacesProvided = factory.getInterfacesProvided();

    Assertions.assertEquals(expected, interfacesProvided);
  }

  @DisplayName("Constructor part returns an implementation of ArgumentsParserFactory")
  @Test
  void getImplementation_ofArgumentsParserFactory_getTheClass() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    final Object implementation = factory.getImplementation(ArgumentsFactory.class);

    Assertions.assertTrue(implementation instanceof ArgumentsFactory);
  }

  @DisplayName("Constructor part returns only an implementation of ArgumentsParserFactory")
  @Test
  void getImplementation_ofMap_throwsException() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        () -> factory.getImplementation(Map.class),
        "Expect exception to be thrown on the call with wrong argument"
    );
  }

  @DisplayName("Constructor part accepts implementation of StringValueFactory")
  @Test
  void putImplementation_stringValueFactory_success() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());
  }

  @DisplayName("Constructor part does not accept several implementations of StringValueFactory")
  @Test
  void putImplementation_severalStringValueFactories_exception() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        () -> factory.putImplementation(
            StringValueFactory.class,
            new StringValueFactoryImpl(),
            new StringValueFactoryImpl()
        ),
        "Method should not accept several implementations"
    );
  }

  @DisplayName("Constructor part accepts only implementation of StringValueFactory")
  @Test
  void putImplementation_mapImplementation_exception() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        () -> factory.putImplementation(Map.class, new HashMap<>()),
        "Method should accept only StringValueFactory implementation"
    );
  }

  @DisplayName("Verification of constructor part should pass if implementation set")
  @Test
  void verifyNecessaryInterfaces_implementationSet_success() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();
    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());

    factory.verifyNecessaryInterfaces();

    Assertions.assertNotNull(factory.createParser());
  }

  @DisplayName("Verification of constructor part should fail if no implementation set")
  @Test
  void verifyNecessaryInterfaces_noimplementationSet_exception() {
    final ArgumentsFactoryImpl factory = new ArgumentsFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        factory::verifyNecessaryInterfaces,
        "Verification should fail on improper initialization"
    );
  }
}
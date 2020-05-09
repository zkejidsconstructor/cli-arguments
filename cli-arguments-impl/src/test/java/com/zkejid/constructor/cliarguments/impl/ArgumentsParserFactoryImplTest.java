package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParserFactory;
import com.zkejid.constructor.cliarguments.api.v1.test.ArgumentsParserFactoryCheckList;
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

class ArgumentsParserFactoryImplTest extends ArgumentsParserFactoryCheckList {

  @Override
  public ArgumentsParserFactory getFactoryImplementation() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();
    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());
    return factory;
  }

  @DisplayName("Constructor part requires only one interface StringValueFactory")
  @Test
  void getInterfacesNecessary_call_getTheClass() {
    final Set<Class<?>> expected = Collections.singleton(StringValueFactory.class);
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    final Set<Class<?>> interfacesNecessary = factory.getInterfacesNecessary();

    Assertions.assertEquals(expected, interfacesNecessary);
  }

  @DisplayName("Constructor part provides only one interface ArgumentsParserFactory")
  @Test
  void getInterfacesProvided_call_getTheClass() {
    final Set<Class<?>> expected = Collections.singleton(ArgumentsParserFactory.class);
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    final Set<Class<?>> interfacesProvided = factory.getInterfacesProvided();

    Assertions.assertEquals(expected, interfacesProvided);
  }

  @DisplayName("Constructor part returns an implementation of ArgumentsParserFactory")
  @Test
  void getImplementation_ofArgumentsParserFactory_getTheClass() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    final Object implementation = factory.getImplementation(ArgumentsParserFactory.class);

    Assertions.assertTrue(implementation instanceof ArgumentsParserFactory);
  }

  @DisplayName("Constructor part returns only an implementation of ArgumentsParserFactory")
  @Test
  void getImplementation_ofMap_throwsException() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        () -> factory.getImplementation(Map.class),
        "Expect exception to be thrown on the call with wrong argument"
    );
  }

  @DisplayName("Constructor part accepts implementation of StringValueFactory")
  @Test
  void putImplementation_stringValueFactory_success() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());
  }

  @DisplayName("Constructor part does not accept several implementations of StringValueFactory")
  @Test
  void putImplementation_severalStringValueFactories_exception() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

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
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        () -> factory.putImplementation(Map.class, new HashMap<>()),
        "Method should accept only StringValueFactory implementation"
    );
  }

  @DisplayName("Verification of constructor part should pass if implementation set")
  @Test
  void verifyNecessaryInterfaces_implementationSet_success() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();
    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());

    factory.verifyNecessaryInterfaces();

    Assertions.assertNotNull(factory.createParser());
  }

  @DisplayName("Verification of constructor part should fail if no implementation set")
  @Test
  void verifyNecessaryInterfaces_noimplementationSet_exception() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();

    Assertions.assertThrows(
        ConstructionException.class,
        factory::verifyNecessaryInterfaces,
        "Verification should fail on improper initialization"
    );
  }
}
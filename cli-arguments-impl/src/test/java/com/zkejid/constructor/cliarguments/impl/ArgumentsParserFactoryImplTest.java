package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParserFactory;
import com.zkejid.constructor.cliarguments.api.v1.test.ArgumentsParserFactoryCheckList;
import com.zkejid.constructor.stringvalue.api.v1.StringValueFactory;
import com.zkejid.constructor.stringvalue.impl.StringValueFactoryImpl;

class ArgumentsParserFactoryImplTest extends ArgumentsParserFactoryCheckList {

  @Override
  public ArgumentsParserFactory getFactoryImplementation() {
    final ArgumentsParserFactoryImpl factory = new ArgumentsParserFactoryImpl();
    factory.putImplementation(StringValueFactory.class, new StringValueFactoryImpl());
    return factory;
  }
}
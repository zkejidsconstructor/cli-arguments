package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.test.ArgumentsParserCheckList;
import com.zkejid.constructor.stringvalue.impl.StringValueFactoryImpl;

public class CommonsCliParserTest extends ArgumentsParserCheckList {

  @Override
  public ArgumentsParser getArgumentsParser() {
    return new CommonsCliParser(new StringValueFactoryImpl());
  }
}

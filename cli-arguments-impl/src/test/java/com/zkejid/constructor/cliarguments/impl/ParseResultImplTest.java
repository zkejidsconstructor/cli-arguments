package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.test.ParseResultCheckList;
import com.zkejid.constructor.stringvalue.impl.StringValueFactoryImpl;

public class ParseResultImplTest extends ParseResultCheckList {

  @Override
  public ArgumentsParser getArgumentsParser() {
    return new CommonsCliParser(new StringValueFactoryImpl());
  }
}

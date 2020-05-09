package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import com.zkejid.constructor.cliarguments.api.v1.ParseResult;
import com.zkejid.constructor.stringvalue.api.v1.StringValue;
import java.util.List;
import java.util.Map;

class ParseResultImpl implements ParseResult {

  private final List<String> plainArguments;
  private final Map<Argument, StringValue> argumentsParsed;

  public ParseResultImpl(List<String> plainArguments, Map<Argument, StringValue> argumentsParsed) {
    this.argumentsParsed = argumentsParsed;
    this.plainArguments = plainArguments;
  }

  @Override
  public Map<Argument, StringValue> getArgumentsParsed() {
    return argumentsParsed;
  }

  @Override
  public List<String> getPlainArguments() {
    return plainArguments;
  }
}

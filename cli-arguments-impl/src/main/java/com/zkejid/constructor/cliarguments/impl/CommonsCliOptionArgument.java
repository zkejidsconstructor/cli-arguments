package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import org.apache.commons.cli.Option;

class CommonsCliOptionArgument implements Argument {

  private final Option option;

  public CommonsCliOptionArgument(Option option) {
    this.option = option;
  }

  @Override
  public String getShortName() {
    return option.getOpt();
  }

  @Override
  public String getLongName() {
    return option.getLongOpt();
  }

  @Override
  public String getDescription() {
    return option.getDescription();
  }

  Option getOption() {
    return option;
  }
}

package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import org.apache.commons.cli.Option;

class CommonsCliOptionArgument implements Argument {

  private final Option option;
  private final ArgumentType type;

  public CommonsCliOptionArgument(Option option, ArgumentType type) {
    this.option = option;
    this.type = type;
  }

  @Override
  public String getShortName() {
    return option.getOpt();
  }

  @Override
  public String getLongName() {
    return option.getLongOpt();
  }

  Option getOption() {
    return option;
  }

  /**
   * Returns name of argument. Prefer long name. Return short name if long name is not specified.
   */
  String getName() {
    final String id = option.getLongOpt() == null ? option.getOpt() : option.getLongOpt();
    assert id != null : "not possible to get meaningful name of argument";
    return id;
  }

  /**
   * Get type of the option. It allows processing different logic for flags and properties
   * at parse time.
   */
  ArgumentType getType() {
    return type;
  }
}

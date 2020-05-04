package com.zkejid.constructor.cliarguments.api.v1;

/**
 * The descriptor of registered argument.
 */
public interface Argument {

  /**
   * Get short name of the argument. Name is typically a one-letter string.
   *
   * @return name of the argument or empty string if no short name specified.
   */
  String getShortName();

  /**
   * Get long name of the argument. Name is typically a alphanumeric string in kebab-case.
   *
   * @return name of the argument or empty string if no long name specified.
   */
  String getLongName();

  /**
   * Returns textual description of argument. Returns empty string if no description specified.
   */
  String getDescription();
}

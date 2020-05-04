package com.zkejid.constructor.cliarguments.api.v1;

/**
 * Parser for handling POSIX and GNU style arguments like {@code ls -al logs} * or {@code ls
 * --block-size=M current_folder} as stated
 * <a href="https://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html">here</a>.
 * <p>
 * Object holds the state of arguments configuration and provides methods for argument registration:
 * {@link #addFlag(String, String)},
 * {@link #addProperty(String, String)}.
 */
public interface ArgumentsParser {

  /**
   * Add boolean flag to parser state. Either short name or long name should be specified.
   *
   * @param shortName short one-letter name of the flag. Can be null.
   * @param longName long kebab-case name of the flag. Can be null.
   * @return descriptor of the registered flag.
   * @throws CliArgumentsException if short or long name clashes with current state of the parser
   *                               or both arguments are null
   */
  Argument addFlag(String shortName, String longName);

  /**
   * Add name-value property to parser state. Either short name or long name should be specified.
   *
   * @param shortName short one-letter name of the property. Can be null.
   * @param longName long kebab-case name of the property. Can be null.
   * @return descriptor of the registered property.
   * @throws CliArgumentsException if short or long name clashes with current state of the parser
   *                               or both arguments are null.
   */
  Argument addProperty(String shortName, String longName);

  /**
   * Parse given arguments list with current state of the parser.
   *
   * @param args arguments list. Arguments should be in POSIX or GNU-like format.
   * @return result of the method.
   */
  ParseResult parse(String[] args);

  /**
   * Parse given arguments string with current state of the parser.
   *
   * @param args string of space-separated arguments in POSIX or GNU-like format.
   * @return result of the method.
   */
  ParseResult parse(String args);
}

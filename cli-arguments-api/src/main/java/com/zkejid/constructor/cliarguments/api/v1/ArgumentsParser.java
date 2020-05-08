package com.zkejid.constructor.cliarguments.api.v1;

/**
 * Parser for handling POSIX and GNU style arguments like {@code ls -al logs} * or {@code ls
 * --block-size=M current_folder} as stated
 * <a href="https://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html">here</a>.
 * <p>
 * Object holds the state of arguments configuration and provides methods for argument registration:
 * {@link #addFlag(String, String)}, {@link #addProperty(String, String)}.
 * <p>
 * Arguments fall into three categories:
 * <ul>
 *   <li>flags,
 *   <li>properties,
 *   <li>plain arguments.
 * </ul>
 * <strong>Flags</strong> are boolean arguments with {@code true} value mapped to flag existence and
 * {@code false} value mapped to flag nonexistence. <strong>Properties</strong> are string arguments
 * with value directly specified in the argument. The rest of arguments fall into
 * <strong>plain group</strong>. Parser is not able to recognize it and returns this group as a
 * list of string values.
 */
public interface ArgumentsParser {

  /**
   * Add boolean flag to parser state. Either short name or long name should be specified.
   *
   * @param shortName short one-letter name of the flag. Can be null.
   * @param longName long kebab-case name of the flag. Can be null.
   * @return descriptor of the registered flag.
   * @throws CliArgumentsException if short or long name clashes with current state of the parser,
   *                               if both arguments are null,
   *                               if any of arguments is empty string.
   */
  Argument addFlag(String shortName, String longName);

  /**
   * Add name-value property to parser state. Either short name or long name should be specified.
   *
   * @param shortName short one-letter name of the property. Can be null.
   * @param longName long kebab-case name of the property. Can be null.
   * @return descriptor of the registered property.
   * @throws CliArgumentsException if short or long name clashes with current state of the parser,
   *                               if both arguments are null,
   *                               if any of arguments is empty string.
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

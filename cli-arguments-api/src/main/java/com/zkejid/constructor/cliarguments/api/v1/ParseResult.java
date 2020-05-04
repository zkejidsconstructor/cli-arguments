package com.zkejid.constructor.cliarguments.api.v1;

import java.util.List;
import java.util.Map;

/**
 * The result of parsing.
 */
public interface ParseResult {

  /**
   * Get all arguments as key-value map. Keys in the map are arguments from
   * the state of {@link ArgumentsParser} at the moment of parsing. Each flag maps to the string
   * value {@code "true"} if flag exists or {@code "false"} if flag does not exist. Each property
   * maps to its value.
   */
  Map<Argument, Value> getArgumentsParsed();

  /**
   * Return plain arguments which are neither flags nor properties. Method keeps the order of
   * arguments as it is in original arguments list.
   */
  List<String> getPlainArguments();

  /**
   * Types of values differentiated by how does argument specified. Type makes a great influence on
   * actual value, which is returned by {@link Value#getValue()}.
   */
  enum ArgumentType {

    /**
     * Property or flag specified clearly in the arguments list. String value of flag would be
     * {@code "true"}. String value of property would be as specified in the arguments list.
     */
    SPECIFIED,

    /**
     * Property specified but has empty value. Flag couldn't belong to this type. String value
     * of property would be an empty string.
     */
    EMPTY_VALUE,

    /**
     * Property or flag omitted in the arguments list. String value of flag would be
     * {@code "false"}. String value of property would be an empty string.
     */
    OMITTED
  }

  /**
   * Representation of argument value as it is specified or not specified in the arguments list.
   */
  final class Value {
    private final ArgumentType argumentType;
    private final String value;

    public Value(ArgumentType argumentType, String value) {
      this.argumentType = argumentType;
      this.value = value;
    }

    public ArgumentType getArgumentType() {
      return argumentType;
    }

    public String getValue() {
      return value;
    }
  }
}

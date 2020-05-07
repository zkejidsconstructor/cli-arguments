package com.zkejid.constructor.cliarguments.api.v1;

import com.zkejid.constructor.stringvalue.api.v1.StringValue;
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
   * <p>
   * {@link StringValue#getInputValueType()} means actual existence of the key in the arguments
   * list. {@link com.zkejid.constructor.stringvalue.api.v1.InputValueType#EMPTY_VALUE} belongs
   * to an existing flag or a property without value.
   * {@link com.zkejid.constructor.stringvalue.api.v1.InputValueType#OMITTED} belongs to an omitted
   * flag or a nonexisting property.
   * {@link com.zkejid.constructor.stringvalue.api.v1.InputValueType#SPECIFIED} belongs to an
   * existing property and doesn't belong to a flag.
   */
  Map<Argument, StringValue> getArgumentsParsed();

  /**
   * Return plain arguments which are neither flags nor properties. Method keeps the order of
   * arguments as it is in original arguments list.
   */
  List<String> getPlainArguments();

}

package com.zkejid.constructor.cliarguments.api.v1.test;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.ParseResult;
import com.zkejid.constructor.stringvalue.api.v1.InputValueType;
import com.zkejid.constructor.stringvalue.api.v1.StringValue;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The list of checks. Each implementation of {@link ParseResult} should pass
 * given checks to match requirements of the API.
 */
public abstract class ParseResultCheckList {

  public abstract ArgumentsParser getArgumentsParser();

  // getArgumentsParsed tests

  @DisplayName("Parse result contains all registered arguments as keys of map")
  @Test
  void getArgumentsParsed_call_allArgumentsReturned() {
    final ArgumentsParser argumentsParser = getArgumentsParser();
    final Argument unspecifiedFlag = argumentsParser.addFlag("a", "aa");
    final Argument specifiedFlag = argumentsParser.addFlag("b", "bb");
    final Argument unspecifiedProperty = argumentsParser.addFlag("c", "cc");
    final Argument specifiedProperty = argumentsParser.addFlag("d", "dd");

    final ParseResult result = argumentsParser.parse(new String[] {"-b", "--dd"});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(4, argumentsParsed.size(), "Expect 4 arguments registered");
    Assertions.assertTrue(argumentsParsed.containsKey(unspecifiedFlag));
    Assertions.assertTrue(argumentsParsed.containsKey(specifiedFlag));
    Assertions.assertTrue(argumentsParsed.containsKey(unspecifiedProperty));
    Assertions.assertTrue(argumentsParsed.containsKey(specifiedProperty));
  }

  @DisplayName("Omitted flag results in false value and omitted type")
  @Test
  void getArgumentsParsed_omittedFlag_falseValueOmittedType() {
    final ArgumentsParser argumentsParser = getArgumentsParser();
    final Argument flag = argumentsParser.addFlag("a", "aa");

    final ParseResult result = argumentsParser.parse(new String[] {});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument registered");
    final StringValue stringValue = argumentsParsed.get(flag);
    Assertions.assertEquals("false", stringValue.getValue());
    Assertions.assertEquals(InputValueType.OMITTED, stringValue.getInputValueType());
  }

  @DisplayName("Specified flag results in true value and specified type")
  @Test
  void getArgumentsParsed_specifiedFlag_falseValueOmittedType() {
    final ArgumentsParser argumentsParser = getArgumentsParser();
    final Argument flag = argumentsParser.addFlag("a", "aa");

    final ParseResult result = argumentsParser.parse(new String[] {});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument registered");
    final StringValue stringValue = argumentsParsed.get(flag);
    Assertions.assertEquals("false", stringValue.getValue());
    Assertions.assertEquals(InputValueType.OMITTED, stringValue.getInputValueType());
  }

  @DisplayName("Property with specified value results in given value and specified type")
  @Test
  void getArgumentsParsed_specifiedProperty_givenValueSpecifiedType() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addProperty("a", "aa");

    final ParseResult result = parser.parse(new String[]{"--aa=hello"});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument registered");
    final StringValue stringValue = argumentsParsed.get(argument);
    Assertions.assertEquals("hello", stringValue.getValue());
    Assertions.assertEquals(InputValueType.SPECIFIED, stringValue.getInputValueType());
  }

  @DisplayName("Property with omitted value results in empty value and empty type")
  @Test
  void getArgumentsParsed_emptyProperty_emptyValueEmptyType() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addProperty("a", "aa");

    final ParseResult result = parser.parse(new String[]{"--aa"});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument registered");
    final StringValue stringValue = argumentsParsed.get(argument);
    Assertions.assertEquals("", stringValue.getValue());
    Assertions.assertEquals(InputValueType.EMPTY_VALUE, stringValue.getInputValueType());
  }

  @DisplayName("Omitted property results in empty value and omitted type")
  @Test
  void getArgumentsParsed_omittedProperty_emptyValueEmptyType() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addProperty("a", "aa");

    final ParseResult result = parser.parse(new String[]{});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument registered");
    final StringValue stringValue = argumentsParsed.get(argument);
    Assertions.assertEquals("", stringValue.getValue());
    Assertions.assertEquals(InputValueType.OMITTED, stringValue.getInputValueType());
  }

  @DisplayName("If token is a merge of flags, then all flags can be parsed")
  @Test
  void getArgumentsParsed_twoFlagsMergedIntoToken_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument flagA = parser.addFlag("a", "aa");
    final Argument flagB = parser.addFlag("b", "bb");

    final ParseResult result = parser.parse(new String[]{"-ab"});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(2, argumentsParsed.size(), "Expect 2 arguments");
    final StringValue stringValueA = argumentsParsed.get(flagA);
    Assertions.assertEquals("true", stringValueA.getValue());
    Assertions.assertEquals(InputValueType.SPECIFIED, stringValueA.getInputValueType());
    final StringValue stringValueB = argumentsParsed.get(flagB);
    Assertions.assertEquals("true", stringValueB.getValue());
    Assertions.assertEquals(InputValueType.SPECIFIED, stringValueB.getInputValueType());
  }

  @DisplayName("Can parse flag if marked with double hyphen")
  @Test
  void getArgumentsParsed_flagWithDoubleHyphen_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument flagA = parser.addFlag("a", "aa");

    final ParseResult result = parser.parse(new String[]{"--a"});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument");
    final StringValue stringValue = argumentsParsed.get(flagA);
    Assertions.assertEquals("true", stringValue.getValue());
    Assertions.assertEquals(InputValueType.SPECIFIED, stringValue.getInputValueType());
  }

  @DisplayName("Can parse property if marked with single hyphen")
  @Test
  void getArgumentsParsed_propertyWithSingleHyphen_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument flagA = parser.addProperty("a", "aa");

    final ParseResult result = parser.parse(new String[]{"-aa=hello"});
    final Map<Argument, StringValue> argumentsParsed = result.getArgumentsParsed();

    Assertions.assertEquals(1, argumentsParsed.size(), "Expect 1 argument");
    final StringValue stringValue = argumentsParsed.get(flagA);
    Assertions.assertEquals("hello", stringValue.getValue());
    Assertions.assertEquals(InputValueType.SPECIFIED, stringValue.getInputValueType());
  }

  // getPlainArguments tests

  @DisplayName("Unregistered short name results in plain argument")
  @Test
  void getPlainArguments_unregisteredShortName_isPlainArgument() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult result = parser.parse(new String[]{"-a"});
    final List<String> plainArguments = result.getPlainArguments();

    Assertions.assertEquals(1, plainArguments.size(), "Expect 1 plain argument");
    Assertions.assertEquals("-a", plainArguments.get(0));
  }

  @DisplayName("Unregistered long name results in plain argument")
  @Test
  void getPlainArguments_unregisteredLongName_isPlainArgument() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult result = parser.parse(new String[]{"--aa"});
    final List<String> plainArguments = result.getPlainArguments();

    Assertions.assertEquals(1, plainArguments.size(), "Expect 1 plain argument");
    Assertions.assertEquals("--aa", plainArguments.get(0));
  }

  @DisplayName("Unregistered plain alphabetic string results in plain argument")
  @Test
  void getPlainArguments_alphabeticString_isPlainArgument() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult result = parser.parse(new String[]{"abcd"});
    final List<String> plainArguments = result.getPlainArguments();

    Assertions.assertEquals(1, plainArguments.size(), "Expect 1 plain argument");
    Assertions.assertEquals("abcd", plainArguments.get(0));
  }

  @DisplayName("Unregistered java type property results in plain argument")
  @Test
  void getPlainArguments_javaTypeProperty_isPlainArgument() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult result = parser.parse(new String[]{"-Djava.type.property=hello"});
    final List<String> plainArguments = result.getPlainArguments();

    Assertions.assertEquals(1, plainArguments.size(), "Expect 1 plain argument");
    Assertions.assertEquals("-Djava.type.property=hello", plainArguments.get(0));
  }

  /**
   * Merged flags should be processed by a single arguments list. If token contains both
   * registered and unregistered flags, then it could be the option handled by other
   * argument parser.
   */
  @DisplayName(
      "If token looks like merge flags token but has unregistered part, "
          + "then treat it as plain argument"
  )
  @Test
  void getPlainArguments_twoRegisteredAndOneUnregisteredFlagMergedIntoToken_success() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "aa");
    parser.addFlag("b", "bb");

    final ParseResult result = parser.parse(new String[]{"-abc"});
    final List<String> plainArguments = result.getPlainArguments();

    Assertions.assertEquals(1, plainArguments.size());
    Assertions.assertEquals("-abc", plainArguments.get(0));
  }

  @DisplayName("Argument -- stops parsing and rest of arguments counts as plain")
  @Test
  void getPlainArguments_doubleHiphen_separatesPlainArguments() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument flagA = parser.addFlag("a", "aa");
    final Argument flagB = parser.addFlag("b", "bb");

    final ParseResult result = parser.parse(new String[]{"--aa", "--", "--bb", "--cc", "dddd"});
    final List<String> plainArguments = result.getPlainArguments();

    Assertions.assertEquals(3, plainArguments.size(), "Expect 3 plain arguments");
    Assertions.assertEquals("--bb", plainArguments.get(0));
    Assertions.assertEquals("--cc", plainArguments.get(1));
    Assertions.assertEquals("dddd", plainArguments.get(2));
  }
}

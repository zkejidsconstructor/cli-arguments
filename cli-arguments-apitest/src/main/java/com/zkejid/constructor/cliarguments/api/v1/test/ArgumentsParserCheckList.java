package com.zkejid.constructor.cliarguments.api.v1.test;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.CliArgumentsException;
import com.zkejid.constructor.cliarguments.api.v1.ParseResult;
import com.zkejid.constructor.stringvalue.api.v1.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The list of checks. Each implementation of {@link ArgumentsParser} should pass
 * given checks to match requirements of the API.
 */
public abstract class ArgumentsParserCheckList {

  public abstract ArgumentsParser getArgumentsParser();

  // addFlag tests

  @DisplayName("Can register flag with short oneletter name and long kebab-case name")
  @Test
  void addFlag_oneFlag_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final String expectedShort = "a";
    final String expectedLong = "aaa-aaa";

    final Argument argument = parser.addFlag(expectedShort, expectedLong);

    Assertions.assertNotNull(argument);
    Assertions.assertEquals(expectedLong, argument.getLongName());
    Assertions.assertEquals(expectedShort, argument.getShortName());
  }

  @DisplayName("Can register flag only with short oneletter name")
  @Test
  void addFlag_onlyShortName_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final String expectedShort = "a";

    final Argument argument = parser.addFlag(expectedShort, null);

    Assertions.assertNotNull(argument);
    Assertions.assertEquals(expectedShort, argument.getShortName());
  }

  @DisplayName("Can register flag with long kebab-case name")
  @Test
  void addFlag_onlyLongName_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final String expectedLong = "aaa-aaa";

    final Argument argument = parser.addFlag(null, expectedLong);

    Assertions.assertNotNull(argument);
    Assertions.assertEquals(expectedLong, argument.getLongName());
  }

  @DisplayName("Could not register flag without short name or long name")
  @Test
  void addFlag_noShortOrLongName_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag(null, null)
    );
  }

  @DisplayName("Could not register flag with short name longer than 1 letter")
  @Test
  void addFlag_shortNameTwoLettersLong_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("aa", null)
    );
  }

  @DisplayName("Could not register flag with long name shorter than 2 letters")
  @Test
  void addFlag_longNameOneLetterLong_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("a", "b")
    );
  }

  @DisplayName("Could not register flag with empty string as short name")
  @Test
  void addFlag_shortNameEmptyString_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("", "valid-long-name")
    );
  }

  @DisplayName("Could not register flag with empty string as long name")
  @Test
  void addFlag_longNameEmptyString_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("a", "")
    );
  }

  @DisplayName("Could not register same flag twice")
  @Test
  void addFlag_sameFlagRegisteredTwice_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("a", "valid-long-name")
    );
  }

  @DisplayName("Could not register flag with same names as already registered property")
  @Test
  void addFlag_sameFlagAsProperty_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addProperty("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("a", "valid-long-name")
    );
  }

  @DisplayName("Could not register two flags with same short name")
  @Test
  void addFlag_sameShortNameRegisteredTwice_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "valid-long-name1");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("a", "valid-long-name2")
    );
  }

  @DisplayName("Could not register flag and property with same short name. Property first")
  @Test
  void addFlag_sameShortNameFlagAndProperty_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addProperty("a", "valid-long-name1");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("a", "valid-long-name2")
    );
  }

  @DisplayName("Could not register flag with same long name twice")
  @Test
  void addFlag_sameLongNameRegisteredTwice_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("b", "valid-long-name")
    );
  }

  @DisplayName("Could not register flag and property with same long name. Property first")
  @Test
  void addFlag_sameLongNameFlagAndProperty_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addProperty("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addFlag("b", "valid-long-name")
    );
  }

  // addProperty tests

  @DisplayName("Can register property with short oneletter name and long kebab-case name")
  @Test
  void addProperty_oneFlag_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final String expectedShort = "a";
    final String expectedLong = "aaa-aaa";

    final Argument argument = parser.addProperty(expectedShort, expectedLong);

    Assertions.assertNotNull(argument);
    Assertions.assertEquals(expectedLong, argument.getLongName());
    Assertions.assertEquals(expectedShort, argument.getShortName());
  }

  @DisplayName("Can register property only with short oneletter name")
  @Test
  void addProperty_onlyShortName_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final String expectedShort = "a";

    final Argument argument = parser.addProperty(expectedShort, null);

    Assertions.assertNotNull(argument);
    Assertions.assertEquals(expectedShort, argument.getShortName());
  }

  @DisplayName("Can register property with long kebab-case name")
  @Test
  void addProperty_onlyLongName_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final String expectedLong = "aaa-aaa";

    final Argument argument = parser.addProperty(null, expectedLong);

    Assertions.assertNotNull(argument);
    Assertions.assertEquals(expectedLong, argument.getLongName());
  }

  @DisplayName("Could not register property without short name or long name")
  @Test
  void addProperty_noShortOrLongName_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty(null, null)
    );
  }

  @DisplayName("Could not register property with short name longer than 1 letter")
  @Test
  void addProperty_shortNameTwoLettersLong_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("aa", null)
    );
  }

  @DisplayName("Could not register property with long name shorter than 2 letters")
  @Test
  void addProperty_longNameOneLetterLong_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("a", "b")
    );
  }

  @DisplayName("Could not register property with empty string as short name")
  @Test
  void addProperty_shortNameEmptyString_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("", "valid-long-name")
    );
  }

  @DisplayName("Could not register property with empty string as long name")
  @Test
  void addProperty_longNameEmptyString_exception() {
    final ArgumentsParser parser = getArgumentsParser();

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("a", "")
    );
  }

  @DisplayName("Could not register same property twice")
  @Test
  void addProperty_samePropertyRegisteredTwice_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addProperty("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("a", "valid-long-name")
    );
  }

  @DisplayName("Could not register property with same names as already registered flag")
  @Test
  void addProperty_samePropertyAsFlag_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("a", "valid-long-name")
    );
  }

  @DisplayName("Could not register property with same short name twice")
  @Test
  void addProperty_sameShortNameRegisteredTwice_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addProperty("a", "valid-long-name1");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("a", "valid-long-name2")
    );
  }

  @DisplayName("Could not register property and flag with same short name. Flag first")
  @Test
  void addProperty_sameShortNamePropertyAndFlag_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "valid-long-name1");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("a", "valid-long-name2")
    );
  }

  @DisplayName("Could not register property with same long name twice")
  @Test
  void addProperty_sameLongNameRegisteredTwice_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addProperty("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("b", "valid-long-name")
    );
  }

  @DisplayName("Could not register property and flag with same long name. Flag first")
  @Test
  void addProperty_sameLongNamePropertyAndFlag_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    parser.addFlag("a", "valid-long-name");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.addProperty("b", "valid-long-name")
    );
  }

  // parse tests

  @DisplayName("Can parse empty arguments list with empty state")
  @Test
  void parse_emptyStateEmptyArguments_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[0]);

    Assertions.assertNotNull(parseResult);
  }

  @DisplayName("Can parse short flag argument")
  @Test
  void parse_shortFlagArgument_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addFlag("f", "flag");

    final ParseResult parseResult = parser.parse(new String[]{"-f"});

    final StringValue value = parseResult.getArgumentsParsed().get(argument);
    Assertions.assertEquals("true", value.getValue());
  }

  @DisplayName("Can parse long flag argument")
  @Test
  void parse_longFlagArgument_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addFlag("f", "flag");

    final ParseResult parseResult = parser.parse(new String[]{"--flag"});

    final StringValue value = parseResult.getArgumentsParsed().get(argument);
    Assertions.assertEquals("true", value.getValue());
  }

  /**
   * Such an arguments list could confuse user if one will decide to remove the flag, but actually
   * remove only one of two names for given the flag.
   */
  @DisplayName("Fail if both short and long names of flag argument are present")
  @Test
  void parse_shortAndLongFlagArgument_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addFlag("f", "flag");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.parse(new String[]{"-f", "--flag"})
    );
  }

  @DisplayName("Can parse short property argument")
  @Test
  void parse_shortPropertyArgument_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addProperty("p", "property");

    final ParseResult parseResult = parser.parse(new String[]{"-p=hello"});

    final StringValue value = parseResult.getArgumentsParsed().get(argument);
    Assertions.assertEquals("hello", value.getValue());
  }

  @DisplayName("Can parse long property argument")
  @Test
  void parse_longPropertyArgument_success() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addProperty("p", "property");

    final ParseResult parseResult = parser.parse(new String[]{"--property=hello"});

    final StringValue value = parseResult.getArgumentsParsed().get(argument);
    Assertions.assertEquals("hello", value.getValue());
  }

  @DisplayName("Fail if short and long property arguments are present in same argument list")
  @Test
  void parse_shortAndLongPropertyArguments_exception() {
    final ArgumentsParser parser = getArgumentsParser();
    final Argument argument = parser.addProperty("p", "property");

    Assertions.assertThrows(
        CliArgumentsException.class,
        () -> parser.parse(new String[]{"-p=hello", "--property=hello"})
    );
  }

  @DisplayName("Can parse argument list with plain argument")
  @Test
  void parse_plainArgument_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[]{"hello"});

    Assertions.assertNotNull(parseResult);
  }

  @DisplayName("Can parse argument list with unregistered short flag")
  @Test
  void parse_unregisteredShortFlag_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[]{"-f"});

    Assertions.assertNotNull(parseResult);
  }

  @DisplayName("Can parse argument list with unregistered long flag")
  @Test
  void parse_unregisteredLongFlag_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[]{"--flag"});

    Assertions.assertNotNull(parseResult);
  }

  @DisplayName("Can parse argument list with unregistered short property")
  @Test
  void parse_unregisteredShortProperty_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[]{"-p=hello"});

    Assertions.assertNotNull(parseResult);
  }

  @DisplayName("Can parse argument list with unregistered long property")
  @Test
  void parse_unregisteredLongProperty_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[]{"--property=hello"});

    Assertions.assertNotNull(parseResult);
  }

  @DisplayName("Can parse argument list with unregistered java-type property")
  @Test
  void parse_javaTypeProperty_success() {
    final ArgumentsParser parser = getArgumentsParser();

    final ParseResult parseResult = parser.parse(new String[]{"-Djava.type.property=value"});

    Assertions.assertNotNull(parseResult);
  }
}

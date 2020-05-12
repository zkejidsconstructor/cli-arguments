package com.zkejid.constructor.cliarguments.api.v1.test;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsFactory;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.ParseResult;
import com.zkejid.constructor.stringvalue.api.v1.StringValue;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The list of checks. Each implementation of {@link ArgumentsFactory} should pass
 * given checks to match requirements of the API.
 */
public abstract class ArgumentsFactoryCheckList {

  public abstract ArgumentsFactory getFactoryImplementation();

  @DisplayName("Factory can create one parser")
  @Test
  void createParser_oneCall_success() {
    final ArgumentsFactory factory = getFactoryImplementation();

    final ArgumentsParser parser = factory.createParser();

    Assertions.assertNotNull(parser, "Expect parser");
  }

  @DisplayName("Two parsers have independent states")
  @Test
  void createParser_twoCalls_bothParsersHaveIndependentState() {
    final ArgumentsFactory factory = getFactoryImplementation();

    final ArgumentsParser parser1 = factory.createParser();
    final Argument f1 = parser1.addFlag("a", "a-flag-1");
    final Argument p1 = parser1.addProperty("b", "b-property-1");
    final ArgumentsParser parser2 = factory.createParser();
    final Argument f2 = parser2.addFlag("c", "c-flag-2");
    final Argument p2 = parser2.addProperty("d", "d-property-2");

    final ParseResult parseResult1 = parser1.parse(new String[]{"-a", "--b-property-1=hello"});
    Assertions.assertTrue(parseResult1.getPlainArguments().isEmpty());
    final Map<Argument, StringValue> argumentsParsed1 = parseResult1.getArgumentsParsed();
    Assertions.assertTrue(argumentsParsed1.containsKey(f1));
    Assertions.assertTrue(argumentsParsed1.containsKey(p1));

    final ParseResult parseResult2 = parser2.parse(new String[]{"-d=hello", "--c-flag-2"});
    Assertions.assertTrue(parseResult2.getPlainArguments().isEmpty());
    final Map<Argument, StringValue> argumentsParsed2 = parseResult2.getArgumentsParsed();
    Assertions.assertTrue(argumentsParsed2.containsKey(f2));
    Assertions.assertTrue(argumentsParsed2.containsKey(p2));
  }

  @DisplayName("Factory calls return different instances of parser")
  @Test
  void createParser_twoCalls_differentInstances() {
    final ArgumentsFactory factory = getFactoryImplementation();

    final ArgumentsParser parser1 = factory.createParser();
    final ArgumentsParser parser2 = factory.createParser();

    Assertions.assertNotSame(parser1, parser2, "Should be different instances");
  }
}

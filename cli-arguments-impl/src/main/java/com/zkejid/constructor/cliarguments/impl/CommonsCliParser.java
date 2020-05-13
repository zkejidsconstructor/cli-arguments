package com.zkejid.constructor.cliarguments.impl;

import com.zkejid.constructor.cliarguments.api.v1.Argument;
import com.zkejid.constructor.cliarguments.api.v1.ArgumentsParser;
import com.zkejid.constructor.cliarguments.api.v1.CliArgumentsException;
import com.zkejid.constructor.cliarguments.api.v1.ParseResult;
import com.zkejid.constructor.stringvalue.api.v1.InputValueType;
import com.zkejid.constructor.stringvalue.api.v1.StringValue;
import com.zkejid.constructor.stringvalue.api.v1.StringValueFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommonsHelper;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

public class CommonsCliParser implements ArgumentsParser {

  private final List<CommonsCliOptionArgument> arguments = new ArrayList<>();
  private final List<String> excludedTokens = new ArrayList<>();
  private final StringValueFactory stringValueFactory;
  private final Set<String> names = new HashSet<>();

  public CommonsCliParser(StringValueFactory stringValueFactory) {
    Objects.requireNonNull(stringValueFactory, "require stringValueFactory");
    this.stringValueFactory = stringValueFactory;
  }

  @Override
  public Argument addFlag(String shortName, String longName) {
    validateName(shortName, longName);
    final Option opt = new Option(shortName, longName, false, "");
    final CommonsCliOptionArgument argument = new CommonsCliOptionArgument(opt, ArgumentType.FLAG);
    arguments.add(argument);
    return argument;
  }

  @Override
  public Argument addProperty(String shortName, String longName) {
    validateName(shortName, longName);
    final Option opt = new Option(shortName, longName, true, "");
    final CommonsCliOptionArgument argument =
        new CommonsCliOptionArgument(opt, ArgumentType.PROPERTY);
    arguments.add(argument);
    return argument;
  }

  @Override
  public ParseResult parse(String[] args) {
    CommandLine cmd = safeParse(args);
    final List<String> plainArguments = getPlainArguments(cmd);
    final Map<Argument, StringValue> argumentsParsed = getArgumentsParsed(cmd);

    return new ParseResultImpl(plainArguments, argumentsParsed);
  }

  private void validateName(String shortName, String longName) {
    if (shortName == null && longName == null) {
      throw new CliArgumentsException("Either short name or long name should be specified");
    }

    if (shortName != null) {
      if (shortName.isEmpty()) {
        throw new CliArgumentsException("Short name could not be empty string");
      }
      if (shortName.length() != 1) {
        throw new CliArgumentsException("Short name should be 1 symbol long");
      }
      if (names.contains(shortName)) {
        throw new CliArgumentsException("Could not use name twice: " + shortName);
      } else {
        names.add(shortName);
      }
    }

    if (longName != null) {
      if (longName.isEmpty()) {
        throw new CliArgumentsException("Long name could not be empty string");
      }
      if (longName.length() < 2) {
        throw new CliArgumentsException("Long name should be at least 2 symbols long");
      }
      if (names.contains(longName)) {
        throw new CliArgumentsException("Could not use name twice: " + longName);
      } else {
        names.add(longName);
      }
    }
  }

  private CommandLine safeParse(String[] args) {
    CommandLine cmd = null;
    String[] currentArgs = args;
    final DefaultParser parser = new DefaultParser();
    List<Option> emptyValueProperties = new ArrayList<>();
    while (cmd == null) {
      try {
        cmd = parser.parse(getOptions(), currentArgs);
      } catch (UnrecognizedOptionException e) {
        int oldValue = currentArgs.length;
        final String tokenToRemove = e.getOption();
        currentArgs = removeUnrecognizedToken(currentArgs, tokenToRemove);
        if (currentArgs.length != oldValue - 1) {
          throw new CliArgumentsException("Could not remove unrecognized token");
        }
        excludedTokens.add(tokenToRemove);
      } catch (MissingArgumentException e) {
        // library does not process empty values properly
        final Option option = (Option) e.getOption().clone();
        CommonsHelper.addValueToOption(option, "");
        int oldValue = currentArgs.length;
        // it is property token, cause only properties have argument
        final String tokenToRemove = getPropertyTokenFromOption(currentArgs, option);
        currentArgs = removeUnrecognizedToken(currentArgs, tokenToRemove);
        if (currentArgs.length != oldValue - 1) {
          throw new CliArgumentsException("Could not remove token to handle empty argument");
        }
        emptyValueProperties.add(option);
      } catch (ParseException e) {
        throw new CliArgumentsException("Not able to parse ", e);
      }
    }
    for (Option option : emptyValueProperties) {
      CommonsHelper.addOptionToCmd(cmd, option);
    }
    return cmd;
  }

  private String getPropertyTokenFromOption(String[] currentArgs, Option option) {
    for (final String token : currentArgs) {
      String currentToken = token;
      while (currentToken.startsWith("-")) {
        currentToken = currentToken.substring(1);
      }
      if (currentToken.equals(option.getOpt()) || currentToken.equals(option.getLongOpt())) {
        return token;
      }
    }
    throw new CliArgumentsException("Could not find property with empty value: " + option);
  }

  private Options getOptions() {
    final Options options = new Options();
    for (CommonsCliOptionArgument argument : arguments) {
      options.addOption(argument.getOption());
    }
    return options;
  }

  private Map<Argument, StringValue> getArgumentsParsed(CommandLine cmd) {
    final HashMap<Argument, StringValue> result = new HashMap<>();
    for (CommonsCliOptionArgument argument : arguments) {
      final StringValue stringValue = processArgument(cmd, argument);
      result.put(argument, stringValue);
    }
    return result;
  }

  private StringValue processArgument(CommandLine cmd, CommonsCliOptionArgument argument) {
    final ArgumentType type = argument.getType();
    final String name = argument.getName();
    if (ArgumentType.FLAG.equals(type)) {
      // infer value and value type from existence
      final boolean hasOption = cmd.hasOption(name);
      if (hasOption) {
        return stringValueFactory.make("true", InputValueType.SPECIFIED);
      } else {
        return stringValueFactory.make("false", InputValueType.OMITTED);
      }
    } else if (ArgumentType.PROPERTY.equals(type)) {
      // get value and infer input value type from it
      final String value = cmd.getOptionValue(name);
      if (value == null) {
        return stringValueFactory.make("", InputValueType.OMITTED);
      } else if (value.isEmpty()) {
        return stringValueFactory.make(value, InputValueType.EMPTY_VALUE);
      } else {
        return stringValueFactory.make(value, InputValueType.SPECIFIED);
      }
    } else {
      throw new CliArgumentsException("Could not get type of " + name);
    }
  }

  private List<String> getPlainArguments(CommandLine cmd) {
    final List<String> plainArguments = new ArrayList<>();
    plainArguments.addAll(excludedTokens);
    plainArguments.addAll(cmd.getArgList());
    return plainArguments;
  }

  private String[] removeUnrecognizedToken(String[] args, String token) {
    final ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
    if (!argsList.remove(token)) {
      throw new CliArgumentsException("Not able to remove unexpected token: " + token);
    }
    return argsList.toArray(new String[0]);
  }
}

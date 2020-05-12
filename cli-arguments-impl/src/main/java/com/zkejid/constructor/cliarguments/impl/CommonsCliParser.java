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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

public class CommonsCliParser implements ArgumentsParser {

  private final List<CommonsCliOptionArgument> arguments = new ArrayList<>();

  private final List<String> excludedTokens = new ArrayList<>();
  private final StringValueFactory stringValueFactory;

  public CommonsCliParser(StringValueFactory stringValueFactory) {
    Objects.requireNonNull(stringValueFactory, "require stringValueFactory");
    this.stringValueFactory = stringValueFactory;
  }

  @Override
  public Argument addFlag(String shortName, String longName) {
    final Option opt = new Option(shortName, longName, false, "");
    final CommonsCliOptionArgument argument = new CommonsCliOptionArgument(opt);
    arguments.add(argument);
    return argument;
  }

  @Override
  public Argument addProperty(String shortName, String longName) {
    final Option opt = new Option(shortName, longName, true, "");
    final CommonsCliOptionArgument argument = new CommonsCliOptionArgument(opt);
    arguments.add(argument);
    return argument;
  }

  @Override
  public ParseResult parse(String[] args) {
    CommandLine cmd = safeParse(args);
    final List<String> plainArguments = getPlainArguments(cmd);
    final Map<Argument, StringValue> argumentsParsed = getArgumentsParsed();

    return new ParseResultImpl(plainArguments, argumentsParsed);
  }

  private CommandLine safeParse(String[] args) {
    CommandLine cmd = null;
    String[] currentArgs = args;
    final DefaultParser parser = new DefaultParser();
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
      } catch (ParseException e) {
        throw new CliArgumentsException("Not able to parse ", e);
      }
    }
    return cmd;
  }

  private Options getOptions() {
    final Options options = new Options();
    for (CommonsCliOptionArgument argument : arguments) {
      options.addOption(argument.getOption());
    }
    return options;
  }

  private Map<Argument, StringValue> getArgumentsParsed() {
    final HashMap<Argument, StringValue> result = new HashMap<>();
    for (CommonsCliOptionArgument argument : arguments) {
      final String value = argument.getOption().getValue();
      InputValueType inputValueType;
      if (value == null) {
        inputValueType = InputValueType.OMITTED;
      } else if (value.isEmpty()) {
        inputValueType = InputValueType.EMPTY_VALUE;
      } else {
        inputValueType = InputValueType.SPECIFIED;
      }
      final StringValue stringValue = stringValueFactory.make(value, inputValueType);
      result.put(argument, stringValue);
    }
    return result;
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

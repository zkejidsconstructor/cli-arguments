package org.apache.commons.cli;

public class CommonsHelper {

  public static void addOptionToCmd(CommandLine cmd, Option option) {
    cmd.addOption(option);
  }

  public static void addValueToOption(Option option, String value) {
    option.addValueForProcessing(value);
  }
}

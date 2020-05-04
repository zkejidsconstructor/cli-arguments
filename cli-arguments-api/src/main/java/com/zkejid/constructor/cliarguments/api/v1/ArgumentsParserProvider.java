package com.zkejid.constructor.cliarguments.api.v1;

/**
 * Provider for argument parsers.
 */
public interface ArgumentsParserProvider {

  /**
   * Parser for handling POSIX and GNU style arguments like {@code ls -al logs}
   * or {@code ls --block-size=M current_folder}
   */
  ArgumentsParser getParser();
}

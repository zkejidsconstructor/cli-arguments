package com.zkejid.constructor.cliarguments.api.v1;

/**
 * Factory for argument parsers.
 * <p>
 * Factory does not hold the state.
 */
public interface ArgumentsParserFactory {

  /**
   * Create parser for handling POSIX and GNU style arguments like {@code ls -al logs}
   * or {@code ls --block-size=M current_folder}.
   * <p>
   * Each parser holds its own state and does not interacts with state of other parsers.
   */
  ArgumentsParser createParser();
}

package org.iridescence.primrose.utils;


import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * A custom formatter for the logger used throughout this engine. Omits date information for a
 * cleaner style of data format.
 */
public class LoggerFormatter extends Formatter {

  public String format(LogRecord record) {
    return "[" + record.getLevel() + "]: " +
        formatMessage(record) +
        "\n";
  }

  public String getHead(Handler h) {
    return super.getHead(h);
  }

  public String getTail(Handler h) {
    return super.getTail(h);
  }
}

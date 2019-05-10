package org.iridescence.primrose.utils;

/** A simple timer class */
public class Timer {
  private long prevTime;

  /** Create a Timer */
  public Timer() {
    prevTime = System.nanoTime();
  }

  /**
   * Get the time since Timer creation or last reset
   *
   * @return - Returns the delta time as a double
   */
  public double deltaTime() {
    long newTime = System.nanoTime();

    double delta_time = (((double) newTime - (double) prevTime) / 1e9);
    prevTime = newTime;

    return delta_time;
  }

  /** Resets the Timer start. */
  public void reset() {
    prevTime = System.nanoTime();
  }
}

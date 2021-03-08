package com.github.elementbound.jamtracer.core;

/**
 * Extra utilities for maths.
 */
public class MathUtils {
  private static double DEFAULT_EPSILON = 1e-6;

  /**
   * Clamp a value to [0,1] range.
   *
   * @param value value
   *
   * @return clamped value
   */
  public static double saturate(double value) {
    return Math.min(1.0, Math.max(value, 0.0));
  }

  /**
   * Fuzzy compare two values.
   *
   * @param a left value
   * @param b right value
   *
   * @return true if fuzzy equal
   */
  public static boolean compare(double a, double b) {
    return compare(a, b, DEFAULT_EPSILON);
  }

  /**
   * Fuzzy compare two values.
   *
   * @param a       left value
   * @param b       right value
   * @param epsilon threshold
   *
   * @return true if difference smaller than epsilon
   */
  public static boolean compare(double a, double b, double epsilon) {
    return Math.abs(a - b) <= epsilon;
  }
}

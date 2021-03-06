package com.github.elementbound.jamtracer.core;

import java.util.Arrays;

public class Color {
  private static int COMPONENT_COUNT = 4;

  public static Color WHITE = new Color(1.0, 1.0, 1.0);
  public static Color RED = new Color(1.0, 0.0, 0.0);
  public static Color GREEN = new Color(0.0, 1.0, 0.0);
  public static Color BLUE = new Color(0.0, 0.0, 1.0);
  public static Color BLACK = new Color(0.0, 0.0, 0.0);

  private final double[] data;

  public Color(double[] data) {
    if (data.length != COMPONENT_COUNT)
      throw new IllegalArgumentException("Colors must have " + COMPONENT_COUNT + " components");

    this.data = Arrays.copyOf(data, data.length);
  }

  public Color(double r, double g, double b) {
    data = new double[]{r, g, b, 1.0};
  }

  public Color(double r, double g, double b, double a) {
    data = new double[]{r, g, b, a};
  }

  public double getRed() {
    return data[0];
  }

  public double getGreen() {
    return data[1];
  }

  public double getBlue() {
    return data[2];
  }

  public double getAlpha() {
    return data[3];
  }
}

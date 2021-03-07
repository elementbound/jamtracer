package com.github.elementbound.jamtracer.core;

/**
 * Extra utilities for managing colors.
 */
public class ColorUtils {
  /**
   * Convert {@link Color} to an RGB integer.
   *
   * @param color color
   * @return color as RGB integer
   */
  public static int asRGB(Color color) {
    return new java.awt.Color((int) (color.getRed() * 255.0),
            (int) (color.getGreen() * 255.0),
            (int) (color.getBlue() * 255.0))
            .getRGB();
  }

  /**
   * Convert an RGB integer to a {@link Color} instance.
   *
   * @param rgb RGB integer
   * @return color
   */
  public static Color fromRGB(int rgb) {
    java.awt.Color awtColor = new java.awt.Color(rgb);

    return new Color((double) awtColor.getRed() / 255.0,
            (double) awtColor.getGreen() / 255.0,
            (double) awtColor.getBlue() / 255.0);
  }
}

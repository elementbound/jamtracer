package com.github.elementbound.jamtracer.core;

/**
 * Extra utilities for managing colors.
 */
public class ColorUtils {
  /**
   * Convert {@link Color} to an RGB integer, clamping HDR values to LDR.
   *
   * @param color color
   *
   * @return color as RGB integer
   */
  public static int asRGB(Color color) {
    Color saturated = saturate(color);

    return new java.awt.Color(
            (int) (saturated.getRed() * 255.0),
            (int) (saturated.getGreen() * 255.0),
            (int) (saturated.getBlue() * 255.0),
            (int) (saturated.getAlpha() * 255.0))
            .getRGB();
  }

  /**
   * Convert an RGB integer to a {@link Color} instance.
   *
   * @param rgb RGB integer
   *
   * @return color
   */
  public static Color fromRGB(int rgb) {
    java.awt.Color awtColor = new java.awt.Color(rgb);

    return new Color(
            (double) awtColor.getRed() / 255.0,
            (double) awtColor.getGreen() / 255.0,
            (double) awtColor.getBlue() / 255.0);
  }

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
   * Clamp a color from HDR to LDR, limiting all components to [0,1] range.
   *
   * @param color color
   *
   * @return clamped color
   */
  public static Color saturate(Color color) {
    return new Color(
            saturate(color.getRed()),
            saturate(color.getGreen()),
            saturate(color.getBlue()),
            saturate(color.getAlpha())
    );
  }
}

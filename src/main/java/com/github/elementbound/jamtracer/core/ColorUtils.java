package com.github.elementbound.jamtracer.core;

public class ColorUtils {
  public static int asRGB(Color color) {
    return new java.awt.Color((int) (color.getRed() * 255.0),
            (int) (color.getGreen() * 255.0),
            (int) (color.getBlue() * 255.0))
            .getRGB();
  }

  public static Color fromRGB(int rgb) {
    java.awt.Color awtColor = new java.awt.Color(rgb);

    return new Color((double) awtColor.getRed() / 255.0,
            (double) awtColor.getGreen() / 255.0,
            (double) awtColor.getBlue() / 255.0);
  }
}

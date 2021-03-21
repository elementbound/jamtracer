package com.github.elementbound.jamtracer.raytracing.pigment;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;

/**
 * Pigment with a fixed color.
 */
public class ColorPigment implements Pigment {
  private Color color;

  /**
   * Construct pigment with the default color of white.
   */
  public ColorPigment() {
    color = Color.WHITE;
  }

  /**
   * Construct pigment with specific color.
   *
   * @param color color
   */
  public ColorPigment(Color color) {
    this.color = color;
  }

  /**
   * Get pigment color.
   *
   * @return pigment color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Set pigment color.
   *
   * @param color pigment color
   */
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public Color evaluate(Vector texcoords) {
    return color;
  }
}

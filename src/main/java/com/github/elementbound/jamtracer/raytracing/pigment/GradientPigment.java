package com.github.elementbound.jamtracer.raytracing.pigment;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;

/**
 * Pigment with a color gradient along a given direction.
 * <p>The pigment can only be evaluated with texcoords of the same dimensionality as its
 * direction.</p>
 */
public class GradientPigment implements Pigment {
  private Color startColor;
  private Color endColor;
  private Vector direction;

  /**
   * Construct a default vertical gradient from black to white.
   */
  public GradientPigment() {
    startColor = Color.BLACK;
    endColor = Color.WHITE;
    direction = new Vector(0.0, 1.0);
  }

  /**
   * Construct a gradient with properties.
   *
   * @param startColor start color
   * @param endColor   end color
   * @param direction  gradient direction
   */
  public GradientPigment(Color startColor, Color endColor, Vector direction) {
    this.startColor = startColor;
    this.endColor = endColor;
    this.direction = direction.normalized();
  }

  public Color getStartColor() {
    return startColor;
  }

  public void setStartColor(Color startColor) {
    this.startColor = startColor;
  }

  public Color getEndColor() {
    return endColor;
  }

  public void setEndColor(Color endColor) {
    this.endColor = endColor;
  }

  public Vector getDirection() {
    return direction;
  }

  public void setDirection(Vector direction) {
    this.direction = direction.normalized();
  }

  @Override
  public Color evaluate(Vector texcoords) {
    var f = texcoords.length() * direction.dot(texcoords.normalized());
    return startColor.mix(endColor, f);
  }
}

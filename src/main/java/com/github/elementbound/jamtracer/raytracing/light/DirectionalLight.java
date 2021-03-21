package com.github.elementbound.jamtracer.raytracing.light;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;

/**
 * Class representing an omnipresent light source, radiating light in a given direction.
 */
public class DirectionalLight implements Light {
  private Color color;
  private double intensity;
  private Vector direction;

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public double getIntensity() {
    return intensity;
  }

  /**
   * Get light direction.
   *
   * @return direction
   */
  public Vector getDirection() {
    return direction;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public void setIntensity(double intensity) {
    this.intensity = intensity;
  }

  /**
   * Set light direction.
   *
   * @param direction direction
   */
  public void setDirection(Vector direction) {
    this.direction = direction.normalized();
  }

  @Override
  public Ray getRayTowardsSource(Vector point) {
    return new Ray(point, direction.scale(-1.0));
  }

  @Override
  public boolean isInShadow(Vector point, RaycastResult raycastResult) {
    return raycastResult.isHit();
  }
}

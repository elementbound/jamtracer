package com.github.elementbound.jamtracer.raytracing.light;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;

/**
 * Class representing a light source emanating from a single point in space.
 */
public class PointLight implements Light {
  private Color color;
  private double intensity;
  private Vector position;

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public double getIntensity() {
    return intensity;
  }

  /**
   * Get light position.
   *
   * @return light position
   */
  public Vector getPosition() {
    return position;
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
   * Set light position.
   *
   * @param position light position
   */
  public void setPosition(Vector position) {
    this.position = position;
  }

  @Override
  public Ray getRayTowardsSource(Vector point) {
    return Ray.lookat(point, position);
  }

  @Override
  public boolean isInShadow(Vector point, RaycastResult raycastResult) {
    if (!raycastResult.isHit()) {
      return false;
    } else {
      return raycastResult.distance() < Vector.distance(point, position);
    }
  }
}

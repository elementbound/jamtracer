package com.github.elementbound.jamtracer.raytracing.light;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;

/**
 * Interface to represent lights.
 */
public interface Light {
  /**
   * Get light color.
   *
   * @return color
   */
  Color getColor();

  /**
   * Get light intensity.
   *
   * @return intensity
   */
  double getIntensity();

  /**
   * Set light color.
   *
   * @param color color
   */
  void setColor(Color color);

  /**
   * Set light intensity.
   *
   * @param intensity intensity
   */
  void setIntensity(double intensity);

  /**
   * Get a ray from surface points towards the light source.
   * <p>This ray can be used to determine whether the surface is in shadow or not using
   * {@link Light#isInShadow(Vector, RaycastResult)}</p>
   *
   * @param point surface point
   *
   * @return ray towards light
   */
  Ray getRayTowardsSource(Vector point);

  /**
   * Determine whether a surface point is in shadow based on raycast result.
   * <p>The ray can be obtained using {@link Light#getRayTowardsSource(Vector)}</p>
   *
   * @param point         surface point
   * @param raycastResult raycast result
   *
   * @return true if in shadow, false otherwise
   */
  boolean isInShadow(Vector point, RaycastResult raycastResult);

  /**
   * Get light strength at given surface point.
   *
   * @param point surface point
   *
   * @return light strength
   */
  double getContributionStrength(Vector point);
}

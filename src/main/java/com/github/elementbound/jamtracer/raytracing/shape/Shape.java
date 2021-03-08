package com.github.elementbound.jamtracer.raytracing.shape;

import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;

/**
 * Interface to represent any shape that can be raytraced.
 */
public interface Shape {
  /**
   * Cast ray against the shape and return its result.
   *
   * <p>Implementations <em>should</em> return {@link RaycastResult#NO_HIT} when the ray misses the
   * shape.</p>
   *
   * @param ray ray
   *
   * @return raycast result
   */
  RaycastResult raycast(Ray ray);
}

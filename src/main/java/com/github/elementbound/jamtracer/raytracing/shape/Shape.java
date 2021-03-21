package com.github.elementbound.jamtracer.raytracing.shape;

import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.Transformable;
import com.github.elementbound.jamtracer.raytracing.material.Material;

/**
 * Interface to represent any shape that can be raytraced.
 */
public interface Shape extends Transformable {
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

  Material getMaterial();

  void setMaterial(Material material);
}

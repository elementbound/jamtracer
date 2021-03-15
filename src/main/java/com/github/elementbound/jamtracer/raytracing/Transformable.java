package com.github.elementbound.jamtracer.raytracing;

/**
 * Interface to represent entities with transforms.
 */
public interface Transformable {
  /**
   * Get transform.
   *
   * @return transform
   */
  Transform getTransform();

  /**
   * Set transform.
   *
   * @param transform transform
   */
  void setTransform(Transform transform);
}

package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.shape.scene.Scene;

/**
 * Interface for representing materials.
 * <p>Materials define how a given surface reflects light under different circumstances.</p>
 */
public interface Material {
  Material DEFAULT_MATERIAL = new DiffuseMaterial(Color.RED);

  /**
   * Evaluate material for given point on surface.
   *
   * @param scene scene
   *
   * @return material color
   */
  Color evaluate(Scene scene, RaycastResult sceneHit);
}

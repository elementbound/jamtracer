package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.raytracing.RayContext;
import com.github.elementbound.jamtracer.raytracing.pigment.ColorPigment;

/**
 * Interface for representing materials.
 * <p>Materials define how a given surface reflects light under different circumstances.</p>
 */
public interface Material {
  Material DEFAULT_MATERIAL = new DiffuseMaterial(new ColorPigment(Color.RED));
  Material DEFAULT_SCENE_MATERIAL = new SkyMaterial(new ColorPigment(Color.RED));

  /**
   * Evaluate material for given point on surface.
   *
   * @param rayContext ray context
   *
   * @return material color
   */
  Color evaluate(RayContext rayContext);
}

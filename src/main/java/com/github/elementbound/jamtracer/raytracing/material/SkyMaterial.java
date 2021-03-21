package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.raytracing.RayContext;
import com.github.elementbound.jamtracer.raytracing.pigment.Pigment;

/**
 * Material implementation suitable for scene backgrounds.
 * <p>The material will directly evaluate its pigment with the ray direction as texture
 * coordinates.</p>
 */
public class SkyMaterial implements Material {
  private Pigment pigment;

  /**
   * Construct a sky material with pigment.
   *
   * @param pigment pigment
   */
  public SkyMaterial(Pigment pigment) {
    this.pigment = pigment;
  }

  public Pigment getPigment() {
    return pigment;
  }

  public void setPigment(Pigment pigment) {
    this.pigment = pigment;
  }

  @Override
  public Color evaluate(RayContext rayContext) {
    return pigment.evaluate(rayContext.ray().getDirection());
  }
}

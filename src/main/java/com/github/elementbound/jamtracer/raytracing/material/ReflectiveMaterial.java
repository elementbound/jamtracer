package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.raytracing.RayContext;

/**
 * Material implementation for purely reflective materials.
 */
public class ReflectiveMaterial implements Material {
  @Override
  public Color evaluate(RayContext rayContext) {
    return Color.BLACK;
  }
}

package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RayContext;

/**
 * Material implementation for purely reflective materials.
 */
public class ReflectiveMaterial implements Material {
  @Override
  public Color evaluate(RayContext rayContext) {
    var point = rayContext.raycastResult().point();
    var normal = rayContext.raycastResult().normal();
    var incoming = rayContext.ray().getDirection();
    var biasedOrigin = point.add(normal.scale(0.005));

    var reflectTo = normal.add(incoming).add(normal);

    return rayContext.raytracer().evaluateRay(new Ray(biasedOrigin, reflectTo), rayContext);
  }
}

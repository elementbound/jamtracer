package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.MathUtils;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RayContext;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.pigment.Pigment;

/**
 * Diffuse material implementation based on Lambert's cosine law.
 */
public class DiffuseMaterial implements Material {
  private Pigment pigment;

  public DiffuseMaterial(Pigment pigment) {
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
    Color contributions = Color.BLACK;

    var scene = rayContext.scene();
    var point = rayContext.raycastResult().point();
    var normal = rayContext.raycastResult().normal();

    for (var light : scene.getLights()) {
      Ray rayTowardsLight = light.getRayTowardsSource(point);
      RaycastResult shadowResult = scene.raycastWithBias(rayTowardsLight, normal.scale(0.005));
      boolean isInShadow = light.isInShadow(point, shadowResult);

      if (isInShadow) {
        // No contribution from light
        continue;
      }

      var f = MathUtils.saturate(normal.dot(rayTowardsLight.getDirection()));
      contributions = contributions.add(light.getColor().multiply(light.getIntensity() * f));
    }

    return pigment.evaluate(rayContext.raycastResult().texcoords()).multiply(contributions);
  }
}

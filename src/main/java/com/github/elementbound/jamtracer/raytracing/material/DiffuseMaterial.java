package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.MathUtils;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RayContext;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;

/**
 * Diffuse material implementation based on Lambert's cosine law.
 */
public class DiffuseMaterial implements Material {
  private Color color;

  public DiffuseMaterial(Color color) {
    this.color = color;
  }

  /**
   * Get material color.
   *
   * @return color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Set material color.
   *
   * @param color color
   */
  public void setColor(Color color) {
    this.color = color;
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

    return color.multiply(contributions);
  }
}

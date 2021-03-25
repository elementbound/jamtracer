package com.github.elementbound.jamtracer.raytracing.material;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.raytracing.RayContext;
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
    return Color.BLACK;
  }
}

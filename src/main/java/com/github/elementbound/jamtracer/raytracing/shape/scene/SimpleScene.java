package com.github.elementbound.jamtracer.raytracing.shape.scene;

import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.Transform;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple, unoptimized implementation of {@link Scene}.
 */
public class SimpleScene implements Scene {
  private Transform transform;
  private final Set<Shape> shapes;

  public SimpleScene() {
    transform = new Transform();
    shapes = new HashSet<>();
  }

  @Override
  public Transform getTransform() {
    return transform;
  }

  @Override
  public void setTransform(Transform transform) {
    this.transform = transform;
  }

  @Override
  public RaycastResult raycast(Ray ray) {
    var localRay = transform.inverseTransformRay(ray);

    // Raycast all shapes and return closest hit
    return shapes.stream()
            .map(shape -> shape.raycast(localRay))
            .filter(RaycastResult::isHit)
            .min(Comparator.comparingDouble(RaycastResult::distance))
            .orElse(RaycastResult.NO_HIT);
  }

  @Override
  public void prepare() {
    // Do nothing.
  }

  @Override
  public Scene addShape(Shape shape) {
    shapes.add(shape);
    return this;
  }

  @Override
  public Scene removeShape(Shape shape) {
    shapes.remove(shape);
    return this;
  }
}

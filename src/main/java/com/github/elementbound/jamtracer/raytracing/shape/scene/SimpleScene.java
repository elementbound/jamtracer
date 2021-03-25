package com.github.elementbound.jamtracer.raytracing.shape.scene;

import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.Transform;
import com.github.elementbound.jamtracer.raytracing.light.Light;
import com.github.elementbound.jamtracer.raytracing.material.Material;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple, unoptimized implementation of {@link Scene}.
 */
public class SimpleScene implements Scene {
  private Transform transform;
  private final Set<Shape> shapes;
  private final Set<Light> lights;
  private Material material;

  /**
   * Construct an empty scene.
   */
  public SimpleScene() {
    transform = new Transform();
    shapes = new HashSet<>();
    lights = new HashSet<>();
    material = Material.DEFAULT_SCENE_MATERIAL;
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
  public Material getMaterial() {
    return material;
  }

  @Override
  public void setMaterial(Material material) {
    this.material = material;
  }

  @Override
  public RaycastResult raycast(Ray ray) {
    var localRay = transform.inverseTransformRay(ray);

    return RaycastResult.NO_HIT;
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

  @Override
  public Scene addLight(Light light) {
    lights.add(light);
    return this;
  }

  @Override
  public Scene removeLight(Light light) {
    lights.remove(light);
    return this;
  }

  @Override
  public Set<Light> getLights() {
    return Collections.unmodifiableSet(lights);
  }
}

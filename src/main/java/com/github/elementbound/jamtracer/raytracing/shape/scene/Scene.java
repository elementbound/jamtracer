package com.github.elementbound.jamtracer.raytracing.shape.scene;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.light.Light;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;
import java.util.Set;

/**
 * Interface representing scenes.
 * <p>Scenes are collections of shapes and they themselves act as shapes.</p>
 * <p>Since scenes are collections, implementations are may introduce different optimisation
 * techniques to reduce the cost of raycasts.</p>
 */
public interface Scene extends Shape {
  /**
   * Prepare scene for raytracing.
   */
  void prepare();

  /**
   * Raycast scene by applying a bias to the ray origin.
   *
   * @param ray  ray
   * @param bias bias
   *
   * @return raycast result
   */
  default RaycastResult raycastWithBias(Ray ray, Vector bias) {
    var biasedOrigin = ray.getFrom().add(bias);
    var biasedRay = new Ray(biasedOrigin, ray.getDirection());

    return raycast(biasedRay);
  }

  /**
   * Add shape to scene.
   *
   * @param shape shape
   *
   * @return scene
   */
  Scene addShape(Shape shape);

  /**
   * Remove shape from scene.
   *
   * @param shape shape
   *
   * @return scene
   */
  Scene removeShape(Shape shape);

  /**
   * Add light source to the scene.
   *
   * @param light light source
   *
   * @return scene
   */
  Scene addLight(Light light);

  /**
   * Remove light source from the scene.
   *
   * @param light light source
   *
   * @return scene
   */
  Scene removeLight(Light light);

  /**
   * Get light sources in scene.
   * <p>The resulting set is immutable!</p>
   *
   * @return light sources
   */
  Set<Light> getLights();
}

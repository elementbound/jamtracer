package com.github.elementbound.jamtracer.raytracing.shape.scene;

import com.github.elementbound.jamtracer.raytracing.shape.Shape;

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
}

package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.display.Display;
import com.github.elementbound.jamtracer.raytracing.camera.Camera;
import com.github.elementbound.jamtracer.raytracing.shape.scene.Scene;
import java.awt.Point;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Raytracer implementation.
 */
public class Raytracer {
  private Scene scene;
  private Camera camera;
  private Display display;

  private int rayDepthLimit;

  /**
   * Get scene.
   *
   * @return scene
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Set scene.
   *
   * @param scene scene
   */
  public void setScene(Scene scene) {
    this.scene = scene;
  }

  /**
   * Get camera.
   *
   * @return camera
   */
  public Camera getCamera() {
    return camera;
  }

  /**
   * Set camera.
   *
   * @param camera camera
   */
  public void setCamera(Camera camera) {
    this.camera = camera;
  }

  /**
   * Get target display.
   *
   * @return display
   */
  public Display getDisplay() {
    return display;
  }

  /**
   * Set target display.
   *
   * @param display display
   */
  public void setDisplay(Display display) {
    this.display = display;
  }

  /**
   * Get ray depth limit.
   *
   * @return ray depth limit
   */
  public int getRayDepthLimit() {
    return rayDepthLimit;
  }

  /**
   * Set ray depth limit.
   *
   * @param rayDepthLimit ray depth limit
   */
  public void setRayDepthLimit(int rayDepthLimit) {
    this.rayDepthLimit = rayDepthLimit;
  }

  /**
   * Render scene.
   */
  public void render() {

  }

  /**
   * Evaluate result of a given ray.
   *
   * @param ray        ray
   * @param rayContext ray context
   *
   * @return traced color
   */
  public Color evaluateRay(Ray ray, RayContext rayContext) {
    return Color.BLACK;
  }

  private Color getSkyColor(RayContext rayContext) {
    return scene.getMaterial().evaluate(rayContext);
  }

  private Ray getRayForPixel(Point screenCoords, Display display, Camera camera) {
    double u = (double) screenCoords.x / display.getWidth();
    double v = (double) screenCoords.y / display.getHeight();

    return camera.getRay(new Vector(u, v));
  }

  private Stream<Point> pixelStream(int width, int height) {
    int pixelCount = width * height;
    return IntStream.range(0, pixelCount)
        .map(i -> (79 + i * 97) % pixelCount)
        .mapToObj(i -> new Point(i % width, i / width));
  }
}

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
  private Stream<Point> pixelStream(int width, int height) {
    int pixelCount = width * height;
    return IntStream.range(0, pixelCount)
            .map(i -> (79 + i * 97) % pixelCount)
            .mapToObj(i -> new Point(i % width, i / width));
  }

  /**
   * Render scene viewed from camera to a display.
   *
   * @param scene   scene
   * @param camera  camera
   * @param display output display
   */
  public void render(Scene scene, Camera camera, Display display) {
    pixelStream(display.getWidth(), display.getHeight())
            .parallel()
            .forEach(screenCoords -> {
              var ray = getRayForPixel(screenCoords, display, camera);
              var direction = ray.getDirection().map(d -> (1.0 + d) / 2.0);

              var resultColor = new Color(direction.get(0), direction.get(1), direction.get(2));

              var hitResult = scene.raycast(ray);
              if (hitResult.isHit()) {
                var normal = hitResult.normal();
                var texcoords = hitResult.texcoords();
                var point = hitResult.point().map(Math::abs).scale(1.0 / 8.0);
                var df = hitResult.distance() / 16.0;

                // resultColor = new Color(normal.get(0), normal.get(1), normal.get(2));
                // resultColor = new Color(texcoords.get(0), texcoords.get(1), 0.0);
                // resultColor = new Color(df, df, df);
                // resultColor = new Color(point.get(0), point.get(1), point.get(2));
                resultColor = hitResult.shape().getMaterial().evaluate(scene, hitResult);
              }

              display.setPixel(screenCoords.x, screenCoords.y, resultColor);
            });
  }

  private Ray getRayForPixel(Point screenCoords, Display display, Camera camera) {
    double u = (double) screenCoords.x / display.getWidth();
    double v = (double) screenCoords.y / display.getHeight();

    return camera.getRay(new Vector(u, v));
  }
}

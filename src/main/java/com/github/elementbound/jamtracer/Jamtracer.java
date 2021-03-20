package com.github.elementbound.jamtracer;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.display.AWTWindowDisplay;
import com.github.elementbound.jamtracer.display.Display;
import com.github.elementbound.jamtracer.raytracing.camera.PerspectiveCamera;
import com.github.elementbound.jamtracer.raytracing.shape.CubeShape;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;

/**
 * Jamtracer application.
 */
public class Jamtracer {
  private static final int WIDTH = 320;
  private static final int HEIGHT = 240;

  /**
   * Jamtracer entry point.
   *
   * @param args CLI params
   */
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Jamtracer!");

    Display display = new AWTWindowDisplay(WIDTH, HEIGHT);
    display.onClose().subscribe(v -> System.exit(0));

    PerspectiveCamera camera = new PerspectiveCamera();
    camera.setAspectRatio(WIDTH, HEIGHT);
    camera.setFieldOfView(120.0);

    Shape shape = new CubeShape();

    System.out.println("Starting render loop!");

    var angle = 0.0;
    while (display.isOpen()) {
      angle += 20.0;

      camera.getTransform().update()
              .setRotation(new Vector(0.0, 0.0, angle))
              .done();

      // Place sphere in front of camera
      shape.getTransform().update()
              .setPosition(camera.getTransform().getMatrix()
                      .transform(Vector.FORWARD.scale(4.0).asHeterogeneous())
                      .asHomogeneous())
              .setScale(new Vector(2.0, 1.0, 1.0))
              .done();

      for (int y = 0; y < display.getHeight(); y++) {
        for (int x = 0; x < display.getWidth(); x++) {
          double u = (double) x / display.getWidth();
          double v = (double) y / display.getHeight();

          var ray = camera.getRay(new Vector(u, v));
          var direction = ray.getDirection();

          display.setPixel(x, y, new Color(direction.get(0), direction.get(1), direction.get(2)));

          var hitResult = shape.raycast(ray);
          if (hitResult.isHit()) {
            var normal = hitResult.normal();
            var texcoords = hitResult.texcoords();

            // display.setPixel(x, y, new Color(normal.get(0), normal.get(1), normal.get(2)));
            display.setPixel(x, y, new Color(texcoords.get(0), texcoords.get(1), 0.0));
          }
        }
        display.present();
      }

      System.out.println(camera.getTransform().getRotation());
    }
  }
}

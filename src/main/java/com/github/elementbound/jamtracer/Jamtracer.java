package com.github.elementbound.jamtracer;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.display.AWTWindowDisplay;
import com.github.elementbound.jamtracer.display.Display;
import com.github.elementbound.jamtracer.raytracing.camera.PerspectiveCamera;

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

    System.out.println("Starting render loop!");
    while (display.isOpen()) {
      camera.getTransform().update()
              .rotate(new Vector(0.0, 0.0, 10.0))
              .done();

      for (int y = 0; y < display.getHeight(); y++) {
        for (int x = 0; x < display.getWidth(); x++) {
          double u = (double) x / display.getWidth();
          double v = (double) y / display.getHeight();

          var ray = camera.getRay(new Vector(u, v));
          var direction = ray.getDirection();

          display.setPixel(x, y, new Color(direction.get(0), direction.get(1), direction.get(2)));
        }
        display.present();
      }

      System.out.println(camera.getTransform().getRotation());
    }
  }
}

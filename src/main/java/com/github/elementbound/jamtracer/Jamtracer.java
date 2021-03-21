package com.github.elementbound.jamtracer;

import com.github.elementbound.jamtracer.demo.JamDemo;
import com.github.elementbound.jamtracer.demo.SphereDemo;
import com.github.elementbound.jamtracer.display.AWTWindowDisplay;
import com.github.elementbound.jamtracer.display.Display;
import com.github.elementbound.jamtracer.raytracing.Raytracer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Jamtracer application.
 */
public class Jamtracer {
  private static final int WIDTH = 640;
  private static final int HEIGHT = 360;
  private static final boolean PROGRESSIVE_RENDER_ENABLED = true;

  /**
   * Jamtracer entry point.
   *
   * @param args CLI params
   */
  public static void main(String[] args) {
    Display display = new AWTWindowDisplay(WIDTH, HEIGHT);
    display.onClose().subscribe(v -> System.exit(0));

    Raytracer raytracer = new Raytracer();
    raytracer.setDisplay(display);
    raytracer.setRayDepthLimit(4);

    JamDemo demo = new SphereDemo();

    if (PROGRESSIVE_RENDER_ENABLED) {
      var timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          display.present();
        }
      }, 200, 100);
    }

    while (display.isOpen()) {
      demo.update(raytracer);
      raytracer.render();
      display.present();
    }
  }
}

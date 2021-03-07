package com.github.elementbound.jamtracer;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.display.AWTWindowDisplay;
import com.github.elementbound.jamtracer.display.Display;

/**
 * Jamtracer application.
 */
public class Jamtracer {
  /**
   * Jamtracer entry point.
   *
   * @param args CLI params
   */
  public static void main(String[] args) {
    System.out.println("Jamtracer!");

    Display display = new AWTWindowDisplay(320, 240);

    for (int y = 0; y < display.getHeight(); y++) {
      for (int x = 0; x < display.getWidth(); x++) {
        double u = (double) x / display.getWidth();
        double v = (double) y / display.getHeight();

        display.setPixel(x, y, new Color(u, v, 0));
      }
    }

    display.present();
  }
}

package com.github.elementbound.jamtracer;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.display.AWTWindowDisplay;
import com.github.elementbound.jamtracer.display.Display;
import com.github.elementbound.jamtracer.raytracing.camera.PerspectiveCamera;
import com.github.elementbound.jamtracer.raytracing.shape.CubeShape;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;
import com.github.elementbound.jamtracer.raytracing.shape.SphereShape;
import com.github.elementbound.jamtracer.raytracing.shape.scene.Scene;
import com.github.elementbound.jamtracer.raytracing.shape.scene.SimpleScene;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    camera.setFieldOfView(60.0);

    Scene scene = new SimpleScene();

    Shape floor = new CubeShape();
    floor.getTransform().update()
            .setScale(new Vector(4.0, .25, 1.0))
            .setPosition(new Vector(0.0, 0.0, -2.0))
            .done();

    scene.addShape(floor);

    Stream.of(-1.0, 0.0, 1.0)
            .map(i -> {
              var sphere = new SphereShape();
              sphere.getTransform().update()
                      .setPosition(Vector.RIGHT.scale(i))
                      .done();
              return sphere;
            }).forEach(scene::addShape);

    Shape sphere = new SphereShape();
    scene.addShape(sphere);

    System.out.println("Starting render loop!");

    var yaw = 0.0;
    var pitch = -45.0;

    scene.prepare();

    while (display.isOpen()) {
      yaw += 12.0;

      camera.getTransform().update()
              .setRotation(new Vector(pitch, 0.0, yaw))
              .done();

      var backward = camera.getTransform().getMatrix()
              .transform(Vector.BACKWARD.asHeterogeneousNormal()).asHomogeneous()
              .scale(6.0);

      camera.getTransform().update()
              .setPosition(backward)
              .done();

      IntStream.range(0, display.getWidth() * display.getHeight())
              .parallel()
              .map(i -> (97 + i * 79) % (display.getWidth() * display.getHeight()))
              .mapToObj(i -> new Vector(i % display.getWidth(), i / display.getWidth()))
              .forEach(screenCoords -> {
                int x = (int) screenCoords.get(0);
                int y = (int) screenCoords.get(1);

                double u = (double) x / display.getWidth();
                double v = (double) y / display.getHeight();

                var ray = camera.getRay(new Vector(u, v));
                var direction = ray.getDirection();

                display.setPixel(x, y,
                        new Color(direction.get(0), direction.get(1), direction.get(2)));

                var hitResult = scene.raycast(ray);
                if (hitResult.isHit()) {
                  var normal = hitResult.normal();
                  var texcoords = hitResult.texcoords();
                  var point = hitResult.point().map(Math::abs).scale(1.0 / 8.0);
                  var df = hitResult.distance() / 32.0;

                  // display.setPixel(x, y, new Color(normal.get(0), normal.get(1), normal.get(2)));
                  // display.setPixel(x, y, new Color(texcoords.get(0), texcoords.get(1), 0.0));
                  // display.setPixel(x, y, new Color(df, df, df));
                  display.setPixel(x, y, new Color(point.get(0), point.get(1), point.get(2)));
                }

                /*
                if ((x + y * display.getWidth()) % 1024 == 0)
                  display.present();
                 */
              });

      display.present();

      System.out.println(camera.getTransform().getRotation());
    }
  }
}

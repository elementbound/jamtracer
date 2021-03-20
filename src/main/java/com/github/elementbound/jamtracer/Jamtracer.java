package com.github.elementbound.jamtracer;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.display.AWTWindowDisplay;
import com.github.elementbound.jamtracer.display.Display;
import com.github.elementbound.jamtracer.raytracing.Raytracer;
import com.github.elementbound.jamtracer.raytracing.camera.PerspectiveCamera;
import com.github.elementbound.jamtracer.raytracing.shape.CubeShape;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;
import com.github.elementbound.jamtracer.raytracing.shape.SphereShape;
import com.github.elementbound.jamtracer.raytracing.shape.scene.Scene;
import com.github.elementbound.jamtracer.raytracing.shape.scene.SimpleScene;
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
  public static void main(String[] args) {
    Display display = new AWTWindowDisplay(WIDTH, HEIGHT);
    display.onClose().subscribe(v -> System.exit(0));

    PerspectiveCamera camera = new PerspectiveCamera();
    camera.setAspectRatio(WIDTH, HEIGHT);
    camera.setFieldOfView(60.0);

    Scene scene = createScene();

    Raytracer raytracer = new Raytracer();

    var yaw = 0.0;
    var pitch = -45.0;

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

      raytracer.render(scene, camera, display);
      display.present();
    }
  }

  private static Scene createScene() {
    Scene scene = new SimpleScene();

    Shape floor = new CubeShape();
    floor.getTransform().update()
            .setScale(new Vector(4.0, 4.0, 1.0))
            .setPosition(new Vector(0.0, 0.0, -1.0))
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

    scene.prepare();

    return scene;
  }
}

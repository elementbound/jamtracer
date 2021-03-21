package com.github.elementbound.jamtracer.demo;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Raytracer;
import com.github.elementbound.jamtracer.raytracing.camera.PerspectiveCamera;
import com.github.elementbound.jamtracer.raytracing.light.DirectionalLight;
import com.github.elementbound.jamtracer.raytracing.material.DiffuseMaterial;
import com.github.elementbound.jamtracer.raytracing.material.Material;
import com.github.elementbound.jamtracer.raytracing.material.ReflectiveMaterial;
import com.github.elementbound.jamtracer.raytracing.shape.CubeShape;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;
import com.github.elementbound.jamtracer.raytracing.shape.SphereShape;
import com.github.elementbound.jamtracer.raytracing.shape.scene.Scene;
import com.github.elementbound.jamtracer.raytracing.shape.scene.SimpleScene;
import java.util.stream.Stream;

/**
 * Simple demo containing a few spheres with some reflective materials.
 */
public class SphereDemo implements JamDemo {
  private final Scene scene;
  private final PerspectiveCamera camera;

  private double yaw = 0.0;
  private double pitch = -45.0;

  public SphereDemo() {
    this.scene = createScene();
    this.camera = createCamera();
  }

  @Override
  public void update(Raytracer raytracer) {
    yaw += 12.0;

    camera.setAspectRatio(raytracer.getDisplay().getWidth(), raytracer.getDisplay().getHeight());

    camera.getTransform().update()
            .setRotation(new Vector(pitch, 0.0, yaw))
            .done();

    var backward = camera.getTransform().getMatrix()
            .transform(Vector.BACKWARD.asHeterogeneousNormal()).asHomogeneous()
            .scale(8.0);

    camera.getTransform().update()
            .setPosition(backward)
            .done();

    raytracer.setCamera(camera);
    raytracer.setScene(scene);
  }

  private PerspectiveCamera createCamera() {
    PerspectiveCamera camera = new PerspectiveCamera();
    camera.setFieldOfView(60.0);

    return camera;
  }

  private Scene createScene() {
    final Scene scene = new SimpleScene();
    final Material material = new DiffuseMaterial(Color.WHITE);
    final Material reflectiveMaterial = new ReflectiveMaterial();

    DirectionalLight sun = new DirectionalLight();
    scene.addLight(sun);
    sun.setColor(Color.WHITE);
    sun.setIntensity(1.0);
    sun.setDirection(new Vector(-1.0, -1.0, -1.0));

    Shape floor = new CubeShape();
    floor.getTransform().update()
            .setScale(new Vector(4.0, 4.0, 1.0))
            .setPosition(new Vector(0.0, 0.0, -1.0))
            .done();
    floor.setMaterial(reflectiveMaterial);

    scene.addShape(floor);

    Stream.of(-1.0, 0.0, 1.0)
            .map(i -> {
              var sphere = new SphereShape();
              sphere.getTransform().update()
                      .setPosition(new Vector(i, 0.0, (1.0 + i) * 1.0))
                      .done();
              sphere.setMaterial(i != 0.0 ? reflectiveMaterial : material);
              return sphere;
            }).forEach(scene::addShape);

    scene.prepare();

    return scene;
  }
}

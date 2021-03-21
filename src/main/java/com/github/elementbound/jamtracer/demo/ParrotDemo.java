package com.github.elementbound.jamtracer.demo;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Raytracer;
import com.github.elementbound.jamtracer.raytracing.Transform;
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
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Demo with party parrot.
 */
public class ParrotDemo implements JamDemo {
  private final Color[] parrotColors = new Color[]{
      new Color(1.00, 0.55, 0.54),
      new Color(0.99, 0.83, 0.53),
      new Color(0.53, 1.00, 0.53),
      new Color(0.52, 1.00, 1.00),
      new Color(0.54, 0.71, 0.99),
      new Color(0.84, 0.54, 1.00),
      new Color(1.00, 0.54, 1.00),
      new Color(1.00, 0.40, 0.96),
      new Color(0.99, 0.42, 0.71)
  };

  private final Color beakColor = new Color(0.48, 0.54, 0.40);

  private final Scene scene;
  private final PerspectiveCamera camera;

  private DiffuseMaterial parrotMaterial;
  private double yaw = 0.0;
  private final double pitch = -30.0;
  private int parrotColorIndex = 0;

  public ParrotDemo() {
    this.scene = createScene();
    this.camera = createCamera();
  }

  @Override
  public void update(Raytracer raytracer) {
    parrotColorIndex = (parrotColorIndex + 1) % parrotColors.length;
    parrotMaterial.setColor(parrotColors[parrotColorIndex]);

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
    final Material reflectiveMaterial = new ReflectiveMaterial();
    final Material beakMaterial = new DiffuseMaterial(beakColor);
    parrotMaterial = new DiffuseMaterial(parrotColors[0]);

    final var leftLight = new DirectionalLight();
    leftLight.setColor(parrotColors[0]);
    leftLight.setIntensity(1.0);
    leftLight.setDirection(new Vector(-0.5, 0.5, -1.0));
    scene.addLight(leftLight);

    final var rightLight = new DirectionalLight();
    rightLight.setColor(parrotColors[4]);
    rightLight.setIntensity(1.0);
    rightLight.setDirection(new Vector(+0.5, -0.25, -1.0));
    scene.addLight(rightLight);

    Shape floor = new CubeShape();
    floor.getTransform().update()
        .setScale(new Vector(4.0, 4.0, 0.25))
        .setPosition(new Vector(0.0, 0.0, 0.0))
        .done();
    floor.setMaterial(reflectiveMaterial);

    scene.addShape(floor);

    var materials = new Material[]{
        parrotMaterial, // Body
        parrotMaterial, // Head
        beakMaterial, // Beak top
        beakMaterial, // Beak bottom
        reflectiveMaterial, // Left eye
        reflectiveMaterial, // Right eye
    };

    var materialsIterator = Arrays.stream(materials).iterator();

    Stream.of(
        // Body
        new Transform(
            new Vector(0.000, 0.000, 0.288),
            new Vector(0.000, 0.000, 0.000),
            new Vector(1.285, 1.218, 1.803)
        ),

        // Head
        new Transform(
            new Vector(0.000, -0.106, 1.572),
            new Vector(14.200, 0.000, 0.000),
            new Vector(1.000, 1.000, 1.361)
        ),

        // Beak top
        new Transform(
            new Vector(0.000, -1.090, 1.848),
            new Vector(0.000, 0.000, 0.000),
            new Vector(0.243, 0.243, 0.243)
        ),

        // Beak bottom
        new Transform(
            new Vector(0.000, -1.161, 1.636),
            new Vector(-5.390, 0.000, 0.000),
            new Vector(0.243, 0.255, 0.395)
        ),

        // Left eye
        new Transform(
            new Vector(0.439, -0.892, 2.210),
            new Vector(0.000, 0.000, 0.000),
            new Vector(0.179, 0.179, 0.246)
        ),

        // Right eye
        new Transform(
            new Vector(-0.439, -0.892, 2.210),
            new Vector(0.000, 0.000, 0.000),
            new Vector(0.179, 0.179, 0.246)
        )
    ).map(transform -> {
      var shape = new SphereShape();
      shape.setTransform(transform);
      shape.setMaterial(materialsIterator.next());
      return shape;
    }).forEach(scene::addShape);

    scene.prepare();

    return scene;
  }
}

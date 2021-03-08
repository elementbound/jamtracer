package com.github.elementbound.jamtracer.raytracing.camera;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PerspectiveCameraTest {
  @Test
  public void centerRayShouldReturnForward() {
    // Given
    PerspectiveCamera camera = new PerspectiveCamera();
    Ray expected = Ray.lookat(Vector.ZERO, Vector.FORWARD);

    // When
    Ray actual = camera.getRay(new Vector(0.5, 0.5));

    // Then
    assertThat(actual, is(expected));
  }
}
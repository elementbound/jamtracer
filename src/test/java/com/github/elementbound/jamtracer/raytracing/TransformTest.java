package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.core.Matrix;
import com.github.elementbound.jamtracer.core.Vector;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TransformTest {
  @Test
  public void inverseMatrixShouldNegateMatrix() {
    // Given
    Transform transform = new Transform();
    transform
            .setPosition(new Vector(1.0, 2.0, 3.0))
            .setRotation(new Vector(30.0, 45.0, 60.0))
            .setScale(new Vector(1.0, 2.0, 4.0))
            .update();

    Matrix expected = Matrix.identity(4);

    // When
    Matrix actual = transform.getInverseMatrix().multiply(transform.getMatrix());

    // Then
    assertThat(actual, is(expected));
  }
}
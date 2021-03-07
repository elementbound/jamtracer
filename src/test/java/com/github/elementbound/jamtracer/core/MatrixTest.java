package com.github.elementbound.jamtracer.core;


import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatrixTest {
  @Test
  public void identityMatrixShouldReturnMatrix() {
    // Given
    Matrix identity = Matrix.identity(2);
    Matrix matrix = Matrix.fromArray(2, 2, new double[]{1, 2, 3, 4});
    Matrix expected = matrix;

    // When
    Matrix actual = identity.multiply(matrix);

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void identityMatrixShouldReturnVector() {
    // Given
    Matrix identity = Matrix.identity(3);
    Vector vector = new Vector(1, 2, 3);
    Vector expected = vector;

    // When
    Vector actual = identity.transform(vector);

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void translateShouldReturnExpected() {
    // Given
    Vector offset = Vector.fromArray(new double[]{2.0, 3.0});
    Matrix expected = Matrix.fromArray(3, 3, new double[]{
            1.0, 0.0, 2.0,
            0.0, 1.0, 3.0,
            0.0, 0.0, 1.0
    });

    // When
    Matrix actual = Matrix.translate(offset);

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void uniformScaleShouldReturnExpected() {
    // Given
    Matrix expected = Matrix.fromArray(4, 4, new double[]{
            4.0, 0.0, 0.0, 0.0,
            0.0, 4.0, 0.0, 0.0,
            0.0, 0.0, 4.0, 0.0,
            0.0, 0.0, 0.0, 1.0
    });

    // When
    Matrix actual = Matrix.scale(4.0, 3);

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void scaleShouldReturnExpected() {
    // Given
    Vector scalar = new Vector(2.0, 3.0, 4.0);
    Matrix expected = Matrix.fromArray(4, 4, new double[]{
            2.0, 0.0, 0.0, 0.0,
            0.0, 3.0, 0.0, 0.0,
            0.0, 0.0, 4.0, 0.0,
            0.0, 0.0, 0.0, 1.0
    });

    // When
    Matrix actual = Matrix.scale(scalar);

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void translateShouldAddOffset() {
    // Given
    Vector offset = Vector.fromArray(new double[]{2.0, 3.0});
    Matrix translate = Matrix.translate(offset);
    Vector point = new Vector(1.0, 2.0, 1.0);
    Vector expected = new Vector(3.0, 5.0, 1.0);

    // When
    Vector actual = translate.transform(point);

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void multiplyShouldReturnExpected() {
    // Given
    Matrix left = Matrix.fromArray(4, 4, new double[]{
            4.0, 0.0, 0.0, 0.0,
            0.0, 4.0, 0.0, 0.0,
            0.0, 0.0, 4.0, 0.0,
            0.0, 0.0, 0.0, 1.0
    });

    Matrix right = Matrix.fromArray(4, 4, new double[]{
            1.0, 0.0, 0.0, 1.0,
            0.0, 1.0, 0.0, 2.0,
            0.0, 0.0, 1.0, 3.0,
            0.0, 0.0, 0.0, 1.0
    });

    Matrix expected = Matrix.fromArray(4, 4, new double[]{
            4.0, 0.0, 0.0, 1.0,
            0.0, 4.0, 0.0, 2.0,
            0.0, 0.0, 4.0, 3.0,
            0.0, 0.0, 0.0, 1.0
    });

    // When
    Matrix actual = left.multiply(right);

    // Then
    assertThat(actual, is(expected));
  }
}
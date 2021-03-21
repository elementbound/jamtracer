package com.github.elementbound.jamtracer.core;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VectorTest {
  @Test(dataProvider = "dotProvider")
  public void dotShouldReturnExpected(Vector left, Vector right, double expected) {
    // Given

    // When
    double actual = left.dot(right);

    // Then
    assertThat(actual, is(expected));
  }

  @Test(dataProvider = "crossProvider")
  public void crossShouldReturnExpected(Vector left, Vector right, Vector expected) {
    // Given

    // When
    Vector actual = left.cross(right);

    // Then
    assertThat(actual, is(expected));
    assertThat(left.dot(actual), is(0.0)); // result should be perpendicular to left vector
    assertThat(right.dot(actual), is(0.0)); // result should be perpendicular to right vector
  }

  @Test(dataProvider = "lengthProvider")
  public void lengthShouldReturnExpected(Vector vector, double expected) {
    // Given

    // When
    double actual = vector.length();

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void reciprocalShouldReturnExpected() {
    // Given
    Vector vector = new Vector(1.0, 2.0, 4.0);
    Vector expected = new Vector(1.0, 0.5, 0.25);

    // When
    Vector actual = vector.reciprocal();

    // Then
    assertThat(actual, is(expected));
  }

  @Test
  public void reflectShouldReturnExpected() {
    // Given
    var incidence = new Vector(1.0, -1.0).normalized();
    var normal = new Vector(0.0, 1.0);
    var expected = new Vector(1.0, 1.0).normalized();

    // When
    var actual = Vector.reflect(incidence, normal);

    // Then
    assertThat(actual, is(expected));
  }

  @DataProvider
  public Object[][] dotProvider() {
    return new Object[][]{
        {new Vector(1.0, 1.0, 1.0), new Vector(1.0, 1.0, 1.0), 3.0},
        {new Vector(1.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), 0.0},
        {new Vector(1.0, 1.0, 1.0), new Vector(-1.0, 1.0, -1.0), -1.0},
    };
  }

  @DataProvider
  public Object[][] crossProvider() {
    return new Object[][]{
        {Vector.RIGHT, Vector.FORWARD, Vector.UP},
        {Vector.LEFT, Vector.BACKWARD, Vector.UP},

        {Vector.FORWARD, Vector.UP, Vector.RIGHT},
        {Vector.BACKWARD, Vector.DOWN, Vector.RIGHT},

        {Vector.UP, Vector.RIGHT, Vector.FORWARD},
        {Vector.DOWN, Vector.LEFT, Vector.FORWARD},
    };
  }

  @DataProvider
  public Object[][] lengthProvider() {
    return new Object[][]{
        {new Vector(1.0, 0.0, 0.0), 1.0},
        {new Vector(1.0, 0.0, 0.0, 0.0), 1.0},
        {new Vector(2.0, 2.0, 0.0, 0.0), Math.sqrt(8.0)},
    };
  }
}
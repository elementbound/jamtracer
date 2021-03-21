package com.github.elementbound.jamtracer.raytracing.pigment;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GradientPigmentTest {
  private static final Vector VERTICAL_GRADIENT = new Vector(0.0, 1.0);
  private static final Vector HORIZONTAL_GRADIENT = new Vector(1.0, 0.0);

  @Test(dataProvider = "gradientProvider")
  public void evaluateShouldReturnExpected(Vector direction, Vector texcoords, Color expected) {
    // Given
    var pigment = new GradientPigment(Color.BLACK, Color.WHITE, direction);

    // When
    var actual = pigment.evaluate(texcoords);

    // Then
    assertThat(actual, is(expected));
  }

  @DataProvider
  public Object[][] gradientProvider() {
    return new Object[][]{
        {VERTICAL_GRADIENT, new Vector(0.0, 0.0), Color.BLACK},
        {VERTICAL_GRADIENT, new Vector(0.0, 0.5), Color.GRAY},
        {VERTICAL_GRADIENT, new Vector(1.0, 0.5), Color.GRAY},
        {VERTICAL_GRADIENT, new Vector(0.0, 1.0), Color.WHITE},

        {HORIZONTAL_GRADIENT, new Vector(0.0, 0.0), Color.BLACK},
        {HORIZONTAL_GRADIENT, new Vector(0.5, 0.0), Color.GRAY},
        {HORIZONTAL_GRADIENT, new Vector(0.5, 1.0), Color.GRAY},
        {HORIZONTAL_GRADIENT, new Vector(1.0, 0.0), Color.WHITE}
    };
  }
}
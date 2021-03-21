package com.github.elementbound.jamtracer.raytracing.pigment;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ColorPigmentTest {
  @Test(dataProvider = "texcoordProvider")
  public void evaluateShouldReturnFixedColor(Vector texcoords) {
    // Given
    var expected = Color.RED;
    var pigment = new ColorPigment(expected);

    // When
    var actual = pigment.evaluate(texcoords);

    // Then
    assertThat(actual, is(expected));
  }

  @DataProvider
  public Object[][] texcoordProvider() {
    return new Object[][]{
        {new Vector(0.0, 0.0)},
        {new Vector(1.0, 0.0)},
        {new Vector(0.0, 1.0)},
        {new Vector(1.0, 1.0)},
        {new Vector(0.5, 0.5)},
    };
  }
}
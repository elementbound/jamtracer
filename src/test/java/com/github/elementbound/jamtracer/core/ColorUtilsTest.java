package com.github.elementbound.jamtracer.core;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ColorUtilsTest {
  @Test(dataProvider = "asRGBProvider")
  public void asRGBShouldReturnExpected(Color color, int expected) {
    // Given

    // When
    int actual = ColorUtils.asRGB(color);

    // Then
    assertThat(actual, is(expected));
  }

  @Test(dataProvider = "asRGBHDRProvider")
  public void asRGBShouldClampHDRValues(Color color, int expected) {
    // Given

    // When
    int actual = ColorUtils.asRGB(color);

    // Then
    assertThat(actual, is(expected));
  }

  @DataProvider
  public Object[][] asRGBProvider() {
    return new Object[][]{
            {Color.WHITE, 0xFF_FF_FF_FF},
            {Color.BLACK, 0xFF_00_00_00},
            {Color.RED, 0xFF_FF_00_00},
            {Color.GREEN, 0xFF_00_FF_00},
            {Color.BLUE, 0xFF_00_00_FF}
    };
  }

  @DataProvider
  public Object[][] asRGBHDRProvider() {
    return new Object[][]{
            {new Color(2.0, 0.0, 0.0, 1.0), 0xFF_FF_00_00},
            {new Color(0.0, 2.0, 0.0, 1.0), 0xFF_00_FF_00},
            {new Color(0.0, 0.0, 2.0, 1.0), 0xFF_00_00_FF},
            {new Color(0.0, 0.0, 0.0, 2.0), 0xFF_00_00_00},

            {new Color(-2.0, 0.0, 0.0, 1.0), 0xFF_00_00_00},
            {new Color(0.0, -2.0, 0.0, 1.0), 0xFF_00_00_00},
            {new Color(0.0, 0.0, -2.0, 1.0), 0xFF_00_00_00},
            {new Color(0.0, 0.0, 0.0, -2.0), 0x00_00_00_00},
    };
  }
}
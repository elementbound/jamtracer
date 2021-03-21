package com.github.elementbound.jamtracer.core;

import java.beans.ConstructorProperties;
import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

/**
 * Class representing colors as immutable RGBA components, with each component represented as
 * a double.
 *
 * <p>Transparency is supported via the alpha component. Each component can range from [0;1], values
 * higher than one represent HDR colors.</p>
 */
public class Color {
  private static int COMPONENT_COUNT = 4;

  public static Color WHITE = new Color(1.0, 1.0, 1.0);
  public static Color RED = new Color(1.0, 0.0, 0.0);
  public static Color GREEN = new Color(0.0, 1.0, 0.0);
  public static Color BLUE = new Color(0.0, 0.0, 1.0);
  public static Color BLACK = new Color(0.0, 0.0, 0.0);

  private final double[] data;

  /**
   * Construct color from component array.
   *
   * <p>Array <em>must</em> have four items for the RGBA components.</p>
   *
   * @param data components
   */
  public Color(double[] data) {
    if (data.length != COMPONENT_COUNT) {
      throw new IllegalArgumentException("Colors must have " + COMPONENT_COUNT + " components");
    }

    this.data = Arrays.copyOf(data, data.length);
  }

  /**
   * Construct color from RGB values. The alpha value will be set to 1 ( fully opaque ).
   *
   * @param r red component
   * @param g green component
   * @param b blue component
   */
  @ConstructorProperties({"red", "green", "blue"})
  public Color(double r, double g, double b) {
    data = new double[]{r, g, b, 1.0};
  }

  /**
   * Construct color from RGBA values.
   *
   * @param r red component
   * @param g green component
   * @param b blue component
   * @param a alpha componet
   */
  public Color(double r, double g, double b, double a) {
    data = new double[]{r, g, b, a};
  }

  /**
   * Get red component.
   *
   * @return red component
   */
  public double getRed() {
    return data[0];
  }

  /**
   * Get green component.
   *
   * @return green component
   */
  public double getGreen() {
    return data[1];
  }

  /**
   * Get blue component.
   *
   * @return blue component
   */
  public double getBlue() {
    return data[2];
  }

  /**
   * Get alpha component.
   *
   * @return alpha component
   */
  public double getAlpha() {
    return data[3];
  }

  public Color add(Color that) {
    return zipMap(that, Double::sum);
  }

  /**
   * Subtract color.
   *
   * @param that right hand color
   *
   * @return subtraction result
   */
  public Color subtract(Color that) {
    return zipMap(that, (a, b) -> a - b);
  }

  /**
   * Multiply with color.
   *
   * @param that multiplier
   *
   * @return multiplication result
   */
  public Color multiply(Color that) {
    return zipMap(that, (a, b) -> a * b);
  }

  /**
   * Multiply by factor.
   *
   * @param factor factor
   *
   * @return multiplication result
   */
  public Color multiply(double factor) {
    return map(v -> v * factor);
  }

  /**
   * Dividy by color.
   *
   * @param that divisor
   *
   * @return division result
   */
  public Color divide(Color that) {
    return zipMap(that, (a, b) -> a / b);
  }

  /**
   * Create a new color by applying an operator to each of its components.
   *
   * @param operator operator
   *
   * @return result
   */
  public Color map(DoubleUnaryOperator operator) {
    return new Color(Arrays.stream(data).map(operator).toArray());
  }

  /**
   * Create a new color by zipping the two colors' components together and applying a binary
   * operator on them.
   *
   * @param that other color
   * @param operator binary operator
   *
   * @return result
   */
  public Color zipMap(Color that, DoubleBinaryOperator operator) {
    return new Color(IntStream.range(0, 4)
            .mapToDouble(i -> operator.applyAsDouble(this.data[i], that.data[i]))
            .toArray()
    );
  }
}

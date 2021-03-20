package com.github.elementbound.jamtracer.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Immutable class to represent vectors with arbitrary dimensions.
 */
public class Vector implements Iterable<Double> {
  private final double[] data;
  private double length;

  public static Vector ONE = new Vector(1.0, 1.0, 1.0);
  public static Vector ZERO = new Vector(0.0, 0.0, 0.0);
  public static Vector NEGATIVE_ONE = new Vector(-1.0, -1.0, -1.0);

  public static Vector RIGHT = new Vector(1.0, 0.0, 0.0);
  public static Vector FORWARD = new Vector(0.0, 1.0, 0.0);
  public static Vector UP = new Vector(0.0, 0.0, 1.0);

  public static Vector LEFT = new Vector(-1.0, 0.0, 0.0);
  public static Vector BACKWARD = new Vector(0.0, -1.0, 0.0);
  public static Vector DOWN = new Vector(0.0, 0.0, -1.0);

  /**
   * Create a 2D vector from coordinates.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Vector(double x, double y) {
    data = new double[]{x, y};
    precalculateProperties();
  }

  /**
   * Create a 3D vector from coordinates.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   */
  public Vector(double x, double y, double z) {
    data = new double[]{x, y, z};
    precalculateProperties();
  }

  /**
   * Create 4D vector from coordinates.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   * @param w w coordinate
   */
  public Vector(double x, double y, double z, double w) {
    data = new double[]{x, y, z, w};
    precalculateProperties();
  }

  /**
   * Create vector from data.
   *
   * <p>Data must be <em>immutable</em>, otherwise the instance itself might change values!</p>
   *
   * @param data data
   */
  private Vector(double[] data) {
    this.data = data;
    precalculateProperties();
  }

  private void precalculateProperties() {
    length = calculateLength();
  }

  /**
   * Create vector from data.
   *
   * @param data components
   *
   * @return vector
   */
  public static Vector fromArray(double[] data) {
    return new Vector(Arrays.copyOf(data, data.length));
  }

  /**
   * Add two vectors.
   *
   * @param that right hand vector
   *
   * @return piecewise sum of vectors
   */
  public Vector add(Vector that) {
    return performBinaryPiecewise(that, Double::sum);
  }

  /**
   * Subtract two vectors.
   *
   * @param that right hand vector
   *
   * @return piecewise difference of vectors
   */
  public Vector subtract(Vector that) {
    return performBinaryPiecewise(that, (a, b) -> a - b);
  }

  /**
   * Scale vector by a given value.
   *
   * @param scalar scalar
   *
   * @return scaled vector
   */
  public Vector scale(double scalar) {
    return performUnaryPiecewise(v -> v * scalar);
  }

  /**
   * Multiply piece-wise by another vector.
   *
   * @param that right hand vector
   *
   * @return piecewise multiplication of vectors
   */
  public Vector multiply(Vector that) {
    return performBinaryPiecewise(that, (a, b) -> a * b);
  }

  /**
   * Divide piece-wise by another vector.
   *
   * @param that right hand vector
   *
   * @return piecewise division of vectors
   */
  public Vector divide(Vector that) {
    return performBinaryPiecewise(that, (a, b) -> a / b);
  }

  /**
   * Calculate piecewise reciprocal.
   *
   * @return piecewise reciprocal
   */
  public Vector reciprocal() {
    return performUnaryPiecewise(v -> 1.0 / v);
  }

  /**
   * Get vector component.
   *
   * @param i component index
   *
   * @return component value
   */
  public double get(int i) {
    assert i < data.length : "Component index out of bounds!";

    return data[i];
  }

  /**
   * Get vector dimensionality.
   *
   * @return dimension count
   */
  public int dimensions() {
    return data.length;
  }

  /**
   * Get vector length.
   *
   * @return vector length.
   */
  public double length() {
    return this.length;
  }

  /**
   * Convert position to heterogeneous coordinates.
   *
   * @return heterogeneous position
   */
  public Vector asHeterogeneous() {
    var data = new double[4];

    Arrays.fill(data, 0.0);
    System.arraycopy(this.data, 0, data, 0, Math.min(4, this.data.length));
    data[3] = 1.0;

    return new Vector(data);
  }

  /**
   * Convert normal to heterogeneous coordinates.
   *
   * @return heterogeneous normal
   */
  public Vector asHeterogeneousNormal() {
    var data = new double[4];

    Arrays.fill(data, 0.0);
    System.arraycopy(this.data, 0, data, 0, Math.min(4, this.data.length));
    data[3] = 0.0;

    return new Vector(data);
  }

  /**
   * Convert position to homogeneous coordinates.
   *
   * @return homogeneous position
   */
  public Vector asHomogeneous() {
    var data = new double[3];

    Arrays.fill(data, 0.0);
    System.arraycopy(this.data, 0, data, 0, Math.min(3, this.data.length));

    return new Vector(data);
  }

  /**
   * Calculate vector dot product.
   *
   * @param that right hand vector
   *
   * @return dot product
   */
  public double dot(Vector that) {
    assert this.data.length == that.data.length
            : "Can't perform dot product on vectors with mismatching lengths!";

    return IntStream.range(0, this.data.length)
            .mapToDouble(i -> this.data[i] * that.data[i])
            .sum();
  }

  /**
   * Calculate vector cross product.
   *
   * <p>Both vectors <em>must</em> be 3D!</p>
   *
   * @param that right hand vector
   *
   * @return cross product
   */
  public Vector cross(Vector that) {
    assert this.data.length == 3 && that.data.length == 3
            : "Can't perform cross product on non-3D vectors!";

    return new Vector(
            this.data[1] * that.data[2] - this.data[2] * that.data[1],
            this.data[2] * that.data[0] - this.data[0] * that.data[2],
            this.data[0] * that.data[1] - this.data[1] * that.data[0]
    );
  }

  /**
   * Is this vector normalized ( i.e. length equals 1? )
   *
   * @return true if normalzied
   */
  public boolean isNormalized() {
    return MathUtils.fuzzyEquals(this.length(), 1.0);
  }

  /**
   * Return a normalized version of this vector ( i.e. with a length of 1 )
   *
   * @return normal vector
   */
  public Vector normalized() {
    return isNormalized()
            ? this
            : this.scale(1.0 / this.length());
  }

  private double calculateLength() {
    return Math.sqrt(Arrays.stream(data)
            .map(v -> v * v)
            .sum());
  }

  private Vector performUnaryPiecewise(DoubleUnaryOperator operator) {
    return new Vector(Arrays.stream(this.data)
            .map(operator)
            .toArray());
  }

  private Vector performBinaryPiecewise(Vector that, DoubleBinaryOperator operator) {
    assert this.data.length == that.data.length
            : "Can't perform binary operation on vectors with mismatching lengths!";

    return new Vector(IntStream.range(0, this.data.length)
            .mapToDouble(i -> operator.applyAsDouble(this.data[i], that.data[i]))
            .toArray());
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Vector{");
    sb.append(Arrays.toString(data));
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Vector vector = (Vector) o;

    return Arrays.equals(this.data, vector.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }

  @Override
  public Iterator<Double> iterator() {
    return new VectorIterator(this);
  }

  @Override
  public Spliterator<Double> spliterator() {
    return Spliterators.spliterator(data, Spliterator.IMMUTABLE
            | Spliterator.NONNULL | Spliterator.SIZED);
  }

  /**
   * Transform vector by applying a piecewise function.
   *
   * @param operator function
   *
   * @return transform result
   */
  public Vector map(DoubleUnaryOperator operator) {
    return performUnaryPiecewise(operator);
  }

  private class VectorIterator implements Iterator<Double> {
    private int index;
    private final Vector vector;

    public VectorIterator(Vector vector) {
      this.vector = vector;
    }

    @Override
    public boolean hasNext() {
      return index + 1 < vector.dimensions();
    }

    @Override
    public Double next() {
      return vector.get(index++);
    }
  }
}

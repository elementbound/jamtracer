package com.github.elementbound.jamtracer.core;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Immutable class to represent matrices of arbitrary sizes.
 *
 * <p>Internally, data is represented as a row-major array of doubles.</p>
 */
public class Matrix {
  private final int rows;
  private final int columns;
  private final double[] data;

  private Matrix(int rows, int columns, double[] data) {
    this.rows = rows;
    this.columns = columns;
    this.data = data;
  }

  /**
   * Get matrix component.
   *
   * @param row    component row
   * @param column component column
   *
   * @return component value
   */
  public double get(int row, int column) {
    assert row < rows : "Row index out of bounds!";
    assert column < columns : "Column index out of bounds!";

    return data[column * rows + row];
  }

  /**
   * Multiply by matrix.
   *
   * <p>Two matrices can only be multiplied if the first has the same number of columns as the
   * second's number of rows!</p>
   *
   * @param that matrix
   *
   * @return multiplication result.
   */
  public Matrix multiply(Matrix that) {
    assert this.columns == that.rows : "Can't multiply matrices with mismatching sizes!";

    int resultRows = this.rows;
    int resultColumns = that.rows;

    double[] result = new double[this.columns * that.columns];

    for (int row = 0; row < resultRows; row++) {
      for (int column = 0; column < resultColumns; column++) {
        result[column * resultRows + row] = 0.0;

        for (int i = 0; i < this.columns; i++) {
          result[column * resultRows + row] += this.get(row, i) * that.get(i, column);
        }
      }
    }

    return new Matrix(resultRows, resultColumns, result);
  }

  /**
   * Transform vector by matrix.
   * <p>The vector must have the same number of dimensions as the matrix has columns!</p>
   *
   * @param that vector
   *
   * @return transformed vector
   */
  public Vector transform(Vector that) {
    assert this.columns == that.dimensions();

    double[] result = IntStream.range(0, that.dimensions())
            .mapToDouble(i -> IntStream.range(0, this.columns)
                    .mapToDouble(j -> this.get(j, i) * that.get(j))
                    .sum()
            ).toArray();

    return Vector.fromArray(result);
  }

  /**
   * Create an identity matrix with size.
   *
   * @param size matrix size
   *
   * @return identity matrix
   */
  public static Matrix identity(int size) {
    return new Matrix(size, size, identityData(size));
  }

  /**
   * Create translation matrix.
   * <p>The matrix's dimensions will be the same as the vector's dimensions plus one, on both
   * sides.</p>
   *
   * @param vector translation vector
   *
   * @return translation matrix
   */
  public static Matrix translate(Vector vector) {
    int size = vector.dimensions() + 1;
    double[] data = identityData(size);

    IntStream.range(0, size - 1)
            .map(i -> (i + 1) * size - 1)
            .forEach(i -> data[i] = vector.get(i / size));

    data[size * size - 1] = 1.0;

    return new Matrix(size, size, data);
  }

  /**
   * Create a scaling matrix.
   * <p>The matrix will scale each vector's components with the value specified in the vector.</p>
   *
   * @param scalars scalars
   *
   * @return scaling matrix
   */
  public static Matrix scale(Vector scalars) {
    int size = scalars.dimensions() + 1;
    double[] data = new double[size * size];

    Arrays.fill(data, 0.0);

    IntStream.range(0, size)
            .forEach(i -> data[i * size + i] = i < scalars.dimensions()
                    ? scalars.get(i)
                    : 1.0);

    return new Matrix(size, size, data);
  }

  /**
   * Create a uniform scaling matrix.
   * <p>The matrix will scale each of the vector's components by the given scalar.</p>
   * <p>The matrix will support transforming vectors with the given dimensions, <em>meaning that
   * its size will be (dimensions+1)x(dimensions+1)</em></p>
   *
   * @param scalar     scalar
   * @param dimensions number of dimensions
   *
   * @return uniform scaling matrix
   */
  public static Matrix scale(double scalar, int dimensions) {
    int size = dimensions + 1;
    double[] data = new double[size * size];

    Arrays.fill(data, 0.0);

    IntStream.range(0, size - 1)
            .map(i -> i * size + i)
            .forEach(i -> data[i] = scalar);

    data[size * size - 1] = 1.0;

    return new Matrix(size, size, data);
  }

  /**
   * Create matrix from array.
   *
   * <p>Matrix size must match the size of the supplied data, i.e. the array's length must be equal
   * to rows * columns.</p>
   *
   * @param rows    number of rows
   * @param columns number of columns
   * @param data    matrix data
   *
   * @return matrix
   */
  public static Matrix fromArray(int rows, int columns, double[] data) {
    assert rows * columns == data.length : "Matrix size doesn't match data length!";

    return new Matrix(rows, columns, Arrays.copyOf(data, data.length));
  }

  private static double[] identityData(int size) {
    double[] data = new double[size * size];

    Arrays.fill(data, 0.0);

    IntStream.range(0, size)
            .map(i -> i * size + i)
            .forEach(i -> data[i] = 1.0);

    return data;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Matrix{");
    sb.append("rows=").append(rows);
    sb.append(", columns=").append(columns);
    sb.append(", data=").append(Arrays.toString(data));
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

    Matrix matrix = (Matrix) o;

    return this.rows == matrix.rows
            && this.columns == matrix.rows
            && Arrays.equals(this.data, matrix.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }
}

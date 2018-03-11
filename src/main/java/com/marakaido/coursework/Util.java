package com.marakaido.coursework;

public class Util {

    /**
     * Creates new 2-dimensional array from 1-dimensional
     * @param arr 1-dimensional array to be converted, is not modified
     * @param rows number of rows in new array, must be positive
     * @param cols number of columns in new array, must be positive
     * @throws IllegalArgumentException if illegal number of rows and cols is set
     * @return new 2-dimensional array
     */
    public static double[][] convertTo2D(final double[] arr, final int rows, final int cols) {
        if (arr.length != rows * cols) throw new IllegalArgumentException();

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = arr[i*cols + j];

        return result;
    }

    /**
     * Creates new image data with one double-precision color value for each pixel
     * @param img raw image data, must have 3 color values for each pixel
     * @return the grayscale version of the original image,
     * with one color value for each pixel
     */
    public static double[] grayscale(int[] img) {
        double[] grayscale = new double[img.length / 3];

        for (int i = 0, j = 0; i < img.length; i+=3, j++)
            grayscale[j] = Math.round((img[i] + img[i+1] + img[i+2]) / 3.0);

        return grayscale;
    }

    /**
     * Correlates f with kernel k
     * @param k - NxM correlation kernel, where N,M > 0, N%2,M%2 == 1
     * @param f - values of discrete function (e.g. image)
     * @return correlation of f with k
     */
    public static double[][] correlate(double[][] k, double[][] f) {
        double[][] result = new double[f.length][f[0].length];

        int rowOffset = k.length / 2;
        int colOffset = k[0].length / 2;
        for (int i = rowOffset; i < f.length - rowOffset; i++)
            for (int j = colOffset; j < f[0].length - colOffset; j++)
                for (int l = 0; l < k.length; l++)
                    for (int m = 0; m < k[0].length; m++)
                        result[i][j] += k[l][m] * f[i + l-rowOffset][j + m-colOffset];

        return result;
    }

    public static double[][] multiplyPerElement(final double[][] left, final double[][] right) {
        if(left.length != right.length || left[0].length != right[0].length)
            throw new IllegalArgumentException();

        double[][] result = new double[left.length][left[0].length];

        for (int i = 0; i < left.length; i++)
            for (int j = 0; j < left[0].length; j++)
                result[i][j] = left[i][j] * right[i][j];

        return result;
    }

    public static double[][] scalarMultiply(double scalar, double[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                result[i][j] = scalar * matrix[i][j];

        return result;
    }

    public static final double[][] xDerFilter = Util.scalarMultiply(1/3.0, new double[][] {
            {-1, 0, 1},
            {-1, 0, 1},
            {-1, 0, 1}
    });

    public static final double[][] yDerFilter = Util.scalarMultiply(1/3.0, new double[][] {
            {-1, -1, -1},
            { 0,  0,  0},
            { 1,  1,  1}
    });

    public static final double[][] gaussian5Filter = Util.scalarMultiply(1/273.0, new double[][] {
            {1,  4,  7,  4, 1},
            {4, 16, 26, 16, 4},
            {7, 26, 41, 26, 7},
            {4, 16, 26, 16, 4},
            {1,  4,  7,  4, 1}
    });
}

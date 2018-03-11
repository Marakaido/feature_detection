package com.marakaido.coursework;

import static org.junit.Assert.*;
import org.junit.Test;

public class UtilTests {

    @Test public void convertTo2DMoreColomnsTest() {
        double[] arr = new double[] {1, 2, 3, 4, 5, 6, 7, 8};
        double[][] result = Util.convertTo2D(arr, 2, 4);
        double[][] expected = new double[][] {{1, 2, 3, 4}, {5, 6, 7, 8}};

        for (int i = 0; i < result.length; i++)
            assertArrayEquals(expected[i], result[i], 0.001);
    }

    @Test public void convertTo2DMoreRowsTest() {
        double[] arr = new double[] {1, 2, 3, 4, 5, 6, 7, 8};
        double[][] result = Util.convertTo2D(arr, 4, 2);
        double[][] expected = new double[][] {{1, 2}, {3, 4}, {5, 6}, {7, 8}};

        for (int i = 0; i < result.length; i++)
            assertArrayEquals(expected[i], result[i], 0.001);
    }

    @Test public void convertTo2DEqualRowsAndColumnsTest() {
        double[] arr = new double[] {1, 2, 3, 4};
        double[][] result = Util.convertTo2D(arr, 2, 2);
        double[][] expected = new double[][] {{1, 2}, {3, 4}};

        for (int i = 0; i < result.length; i++)
            assertArrayEquals(expected[i], result[i], 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertTo2DIllegalArgumentsTest() {
        Util.convertTo2D(new double[]{1, 2}, 2, 2);
    }

    @Test public void grayscaleTest() {
        int[] img = new int[] {15, 23, 50, 255, 3, 1, 43, 23, 2};
        double[] expected = new double[] {29, 86, 23};

        assertArrayEquals(expected, Util.grayscale(img), 1);
    }

    @Test public void correlateTest() {
        double[][] k = new double[][] {
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };
        double[][] f = new double[][] {
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5}
        };
        double[][] expected = new double[][] {
                {0, 0, 0, 0, 0},
                {0, 6, 6, 6, 0},
                {0, 6, 6, 6, 0},
                {0, 6, 6, 6, 0},
                {0, 6, 6, 6, 0},
                {0, 0, 0, 0, 0}
        };

        assertArrayEquals(expected, Util.correlate(k, f));
    }

    @Test public void correlateRectangularKernelTest() {
        double[][] k = new double[][] {
                {-1, 0, 1}
        };
        double[][] f = new double[][] {
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5}
        };
        double[][] expected = new double[][] {
                {0, 2, 2, 2, 0},
                {0, 2, 2, 2, 0},
                {0, 2, 2, 2, 0},
                {0, 2, 2, 2, 0},
                {0, 2, 2, 2, 0},
                {0, 2, 2, 2, 0}
        };

        assertArrayEquals(expected, Util.correlate(k, f));
    }
}

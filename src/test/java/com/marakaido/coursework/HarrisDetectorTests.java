package com.marakaido.coursework;

import static org.junit.Assert.*;
import org.junit.Test;

public class HarrisDetectorTests {

    @Test public void test() {
        double[][] img = new double[][] {
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 2, 3, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0}
        };

        HarrisDetector detector = new HarrisDetector();
        detector.setRThreshold(0.0001);
        int[] result = detector.apply(img);

        assertArrayEquals(new int[]{2, 4}, result);
    }
}

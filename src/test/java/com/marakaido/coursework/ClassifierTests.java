package com.marakaido.coursework;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class ClassifierTests {
    @Test public void initTest() throws IOException {
        LeastDistanceClassifier classifier = new LeastDistanceClassifier(Paths.get("database.csv"));
    }

    @Test public void similarDetectTest() throws IOException {
        LeastDistanceClassifier classifier = new LeastDistanceClassifier(Paths.get("database.csv"));
        int[] target = new int[] {136, 92, 18, 93, 19, 89, 137, 69, 21, 70};
        double result = classifier.classify(target);
    }

    @Test public void differentTest() throws IOException {
        LeastDistanceClassifier classifier = new LeastDistanceClassifier(Paths.get("database.csv"));
        int[] target = new int[] {
                74, 157,
                133, 101,
                133, 59,
                87, 154,
                82, 154,
                //129, 92,
                //129, 69,
                //152, 78,
                //77, 94,
                //89, 80,
                //90, 106,
                //98, 91
        };
        double result = classifier.classify(target);
    }

    @Test public void classifySameBad() throws IOException {
        LeastDistanceClassifier classifier = new LeastDistanceClassifier(Paths.get("database.csv"));
        int[] target = new int[] {
                67, 39,
                9, 47,
                64, 50,
                10, 36
        };
        double result = classifier.classify(target);
    }
}

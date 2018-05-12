package com.marakaido.coursework;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

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

    @Test public void getResults() throws IOException {
        int[][] targets = getTargets(Paths.get("data/stop21.csv"));
        ArrayList<Double> results = new ArrayList<>(100);
        for (int i = 1; i < targets.length - 1; i++) {
            LeastDistanceClassifier.Descriptor left = new LeastDistanceClassifier.Descriptor(targets[0]);
            LeastDistanceClassifier.Descriptor right = new LeastDistanceClassifier.Descriptor(targets[i]);
            results.add(LeastDistanceClassifier.measureOfDissimilarity(left, right));
        }
        System.out.println("Max: " + Collections.max(results));
        System.out.println("Min: " + Collections.min(results));
        System.out.println("Average: " + (results.stream().mapToDouble(f -> f.doubleValue()).sum()/results.size()));
    }

    public int[][] getTargets(Path path) throws IOException {
        ArrayList<int[]> results = new ArrayList<>(30);
        Files.lines(path).forEach(line -> {
            String[] parts = line.split(",");
            int[] vector = new int[parts.length - 1];
            for (int i = 0; i < parts.length - 1; i++)
                vector[i] = Integer.parseInt(parts[i]);
            results.add(vector);
        });
        int[][] result = new int[results.size()][];
        for (int i = 0; i < results.size(); i++)
            result[i] = results.get(i);
        return result;
    }
}

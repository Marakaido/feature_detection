package com.marakaido.coursework;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
        for (int i = 0; i < targets.length - 2; i++) {
            for (int j = i+1; j < targets.length; j++) {
                LeastDistanceClassifier.Descriptor left = new LeastDistanceClassifier.Descriptor(targets[i]);
                LeastDistanceClassifier.Descriptor right = new LeastDistanceClassifier.Descriptor(targets[j]);
                results.add(LeastDistanceClassifier.measureOfDissimilarity(left, right));
            }
        }
        System.out.println("Max: " + Collections.max(results));
        System.out.println("Min: " + Collections.min(results));
        System.out.println("Average: " + (results.stream().mapToDouble(f -> f.doubleValue()).sum()/results.size()));
    }

    @Test public void getResultsBetweenDifferent() throws IOException {
        int[][] targets1 = getTargets(Paths.get("data/right30.csv"));
        int[][] targets2 = getTargets(Paths.get("data/left29.csv"));
        ArrayList<Double> results = new ArrayList<>(100);
        for (int i = 0; i < targets1.length; i++) {
            for (int j = 0; j < targets2.length; j++) {
                LeastDistanceClassifier.Descriptor left = new LeastDistanceClassifier.Descriptor(targets1[i]);
                LeastDistanceClassifier.Descriptor right = new LeastDistanceClassifier.Descriptor(targets2[j]);
                results.add(LeastDistanceClassifier.measureOfDissimilarity(left, right));
            }
        }
        System.out.println("Max: " + Collections.max(results));
        System.out.println("Min: " + Collections.min(results));
        System.out.println("Average: " + (results.stream().mapToDouble(f -> f.doubleValue()).sum()/results.size()));

    }

    @Test public void classifyTest() throws IOException {
        LeastDistanceClassifier classifier = new LeastDistanceClassifier(Paths.get("fullDatabase.csv"));
        int[] target = new int[] {112,88,118,53,133,72,110,78,112,63,60,124,67,125,81,67,70,75,125,123};
        int result = classifier.classify(target);
        System.out.println("30 = " + result);
    }

    @Test public void classifyAllTest() throws IOException {
        int[][] base = getTargetsWithClass(Paths.get("fullDatabase.csv"));
        int errors = 0;
        for (int i = 0; i < base.length; i++) {
            double minDist = Double.MAX_VALUE;
            int result = -1;
            int target = base[i][base[i].length-1];
            LeastDistanceClassifier.Descriptor left = new LeastDistanceClassifier.Descriptor(Arrays.copyOfRange(base[i], 0, base[i].length-1));
            for (int j = 0; j < base.length; j++) {
                if(Arrays.equals(base[i], base[j])) continue;

                LeastDistanceClassifier.Descriptor right = new LeastDistanceClassifier.Descriptor(Arrays.copyOfRange(base[j], 0, base[j].length-1));
                double dist = LeastDistanceClassifier.measureOfDissimilarity(left, right);
                if(dist < minDist) {
                    minDist = dist;
                    result = base[j][base[j].length-1];
                }
            }
            if(result != target) errors++;
        }
        System.out.println("Errors: " + errors);
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

    public int[][] getTargetsWithClass(Path path) throws IOException {
        ArrayList<int[]> results = new ArrayList<>(30);
        Files.lines(path).forEach(line -> {
            String[] parts = line.split(",");
            int[] vector = new int[parts.length];
            for (int i = 0; i < parts.length; i++)
                vector[i] = Integer.parseInt(parts[i]);
            results.add(vector);
        });
        int[][] result = new int[results.size()][];
        for (int i = 0; i < results.size(); i++)
            result[i] = results.get(i);
        return result;
    }
}

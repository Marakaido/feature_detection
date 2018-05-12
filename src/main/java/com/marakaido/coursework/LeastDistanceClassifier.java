package com.marakaido.coursework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class LeastDistanceClassifier implements Classifier {

    public LeastDistanceClassifier(Path file) throws IOException {
        this.database = new HashMap<>();
        Files.lines(file).forEach(line -> {
            String[] numbers = line.split(" ");
            int[] coords = new int[numbers.length];
            for (int i = 0; i < coords.length; i++)
                coords[i] = (int)Math.round(Double.parseDouble(numbers[i]));
            database.put(
                    new Descriptor(Arrays.copyOfRange(coords, 0,coords.length-1)),
                    coords[coords.length-1]);
        });
    }

    @Override
    public int classify(final int[] coords) {
        Descriptor descriptor = new Descriptor(coords);

        double min = Double.MAX_VALUE;
        int result = -1;
        for (Map.Entry<Descriptor, Integer> entry: database.entrySet()) {
            double measure = measureOfDissimilarity(descriptor, entry.getKey());
            if(measure < min) {
                min = measure;
                if(min < 0.5) result = entry.getValue();
            }
        }
        return result;
    }

    public static class Descriptor {
        public Descriptor(final int[] coords) {
            this.coords = Util.convertTo2D(normalize(coords), coords.length / 2, 2);
        }

        public double[][] getCoords() {
            return this.coords;
        }

        private double[] normalize(final int[] coords) {
            if(coords.length == 0 || coords.length % 2 != 0) throw new IllegalArgumentException();

            double[] max = maxs(coords);
            double[] result = new double[coords.length];
            for (int i = 0; i < coords.length - 1; i+=2) {
                result[i] = coords[i] / max[0];
                result[i+1] = coords[i+1] / max[1];
            }
            return result;
        }

        private double[] maxs(final int[] coords) {
            if(coords.length == 0 || coords.length % 2 != 0) throw new IllegalArgumentException();

            double xmax = coords[0];
            double ymax = coords[1];
            for (int i = 0; i < coords.length - 1; i+=2) {
                if(coords[i] > xmax) xmax = coords[i];
                if(coords[i+1] > ymax) ymax = coords[i+1];
            }
            return new double[]{xmax, ymax};
        }

        private double[][] coords;
    }

    public static double measureOfDissimilarity(final Descriptor left, final Descriptor right) {
        double[][] leftCoords = left.getCoords();
        double[][] rightCoords = right.getCoords();
        if(leftCoords.length > rightCoords.length) {
            double[][] temp = leftCoords;
            leftCoords = rightCoords;
            rightCoords = temp;
        }

        ArrayList<Integer> visited = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < leftCoords.length; i++) {
            double minDist = dist.apply(leftCoords[i], rightCoords[0]);
            int index = 0;
            for(int j = 1; j < rightCoords.length; j++) {
                final double d = dist.apply(leftCoords[i], rightCoords[j]);
                if(d < minDist) {
                    minDist = d;
                    index = j;
                }
            }
            sum += minDist;
            visited.add(index);
        }

        double unaccounted = 0;
        for (int i = 0; i < rightCoords.length; i++) {
            if(visited.contains(i)) unaccounted += 1;
        }

        return sum + 0.01 * unaccounted;
    }

    private static BiFunction<double[], double[], Double> dist = (left, right) -> {
        double sum = 0;
        for (int i = 0; i < left.length; i++)
            sum += Math.abs(left[i] - right[i]);
        return sum;
    };

    private final Map<Descriptor, Integer> database;
}

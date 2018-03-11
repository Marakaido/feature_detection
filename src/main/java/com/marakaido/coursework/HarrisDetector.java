package com.marakaido.coursework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class HarrisDetector {

    public int[] apply(final int[] img, final int rows, final int cols) {
        double[][] data = Util.convertTo2D(Util.grayscale(img), rows, cols);

        double[][] Fx = Util.correlate(Util.xDerFilter, data);
        double[][] Fy = Util.correlate(Util.yDerFilter, data);
        
        double[][] FxFx = Util.multiplyPerElement(Fx, Fx);
        double[][] FyFy = Util.multiplyPerElement(Fy, Fy);
        double[][] FxFy = Util.multiplyPerElement(Fx, Fy);

        FxFx = Util.correlate(Util.gaussian5Filter, FxFx);
        FyFy = Util.correlate(Util.gaussian5Filter, FyFy);
        FxFy = Util.correlate(Util.gaussian5Filter, FxFy);

        double[][] R = computeR(FxFx, FyFy, FxFy);

        return indexOfNonMax(R);
    }

    public BinaryOperator<Double> getRMeasure() { return rMeasure; }
    public void setRMeasure(BinaryOperator<Double> rMeasure) { this.rMeasure = rMeasure; }

    public double getRThreshold() { return rThreshold; }
    public void setRThreshold(double rThreshold) { this.rThreshold = rThreshold; }

    public int getNonMaxSpan() { return nonMaxSpan; }
    public void setNonMaxSpan(int nonMaxSpan) { this.nonMaxSpan = nonMaxSpan; }

    private double[][] computeR(final double[][] FxFx, final double[][] FyFy, final double[][] FxFy) {
        final int rows = FxFx.length;
        final int cols = FxFx[0].length;

        if (FyFy.length != rows || FyFy[0].length != cols
                || FxFy.length != rows || FxFy[0].length != cols)
            throw new IllegalArgumentException();

        double[][] result = new double[FxFx.length][FxFx[0].length];

        for (int i = 0; i < FxFx.length; i++)
            for (int j = 0; j < FxFx[0].length; j++)
                result[i][j] = computeR(FxFx[i][j], FyFy[i][j], FxFy[i][j]);

        return result;
    }

    private double computeR(final double FxFx, final double FyFy, final double FxFy) {
        // Eigen values
        double sqrtD = Math.sqrt(FxFx*FxFx + FyFy*FyFy - 2*FxFx*FyFy + 4*FxFy*FxFy);
        double l1 = (FxFx + FyFy + sqrtD) / 2.0;
        double l2 = (FxFx + FyFy - sqrtD) / 2.0;

        return rMeasure.apply(l1, l2);
    }

    private int[] indexOfNonMax(double[][] R) {
        List<Integer> indeces = new ArrayList<>();
        int offset = nonMaxSpan / 2;

        for (int i = offset; i < R.length - offset; i++)
            for (int j = offset; j < R[0].length - offset; j++)
                if(R[i][j] >= rThreshold && isLocalMax(i, j, R)) {
                    indeces.add(i);
                    indeces.add(j);
                }

        int[] result = new int[indeces.size()];
        for (int i = 0; i < indeces.size(); i++) result[i] = indeces.get(i);

        return result;
    }

    private boolean isLocalMax(final int i, final int j, final double[][] R) {
        final double v = R[i][j];
        int offset = nonMaxSpan / 2;

        for (int k = 0; k < nonMaxSpan; k++)
            for (int l = 0; l < nonMaxSpan; l++)
                if(R[i + k-offset][j + l-offset] >= v && (k != offset || l != offset))
                    return false;

        return true;
    }

    private BinaryOperator<Double> rMeasure = (l, r) -> l*r - 0.06*(l+r)*(l+r);
    private double rThreshold = 0.5;
    private int nonMaxSpan = 3;
}

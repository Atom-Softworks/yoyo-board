package com.antoci.ai.util;

public class AtomMath {
    final static double EPSILON = 1e-12;

    public static int map(int valueCoord1,
                          int startCoord1, int endCoord1,
                          int startCoord2, int endCoord2) {

        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("/ 0");
        }

        double offset = startCoord2;
        double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return (int) (ratio * (valueCoord1 - startCoord1) + offset);
    }

}

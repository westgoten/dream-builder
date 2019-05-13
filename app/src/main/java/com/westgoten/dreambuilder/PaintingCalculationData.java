package com.westgoten.dreambuilder;

import java.io.Serializable;

public class PaintingCalculationData implements Serializable {
    private static final long serialVersionUID = 1L;

    public double paintEfficiencyValue;
    public double paintBucketVolume;
    public int paintEfficiencyUnitViewId;
    public double totalWallsLength;
    public double totalGapArea;
    public int quantityOfBuckets;

    public static final double NUMBER_OF_COATS = 2.0;

    public PaintingCalculationData() {}
}

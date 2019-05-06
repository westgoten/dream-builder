package com.westgoten.dreambuilder;

import java.io.Serializable;

public class WallsCalculationData implements Serializable {
    private static final long serialVersionUID = 1L;

    public double height;
    public double totalWallsLength;
    public double totalGapArea;
    public int bricks;
    public int cementBags;
    public double sand;

    public WallsCalculationData() {}
}

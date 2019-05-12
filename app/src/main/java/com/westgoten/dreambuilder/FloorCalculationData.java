package com.westgoten.dreambuilder;

import java.io.Serializable;

public class FloorCalculationData implements Serializable {
    private static final long serialVersionUID = 1L;

    public double perimeter;
    public double floorTotalArea;
    public int quantityOfTiles;

    public static final double FOOTER_HEIGHT = 0.09;

    public FloorCalculationData() {}
}

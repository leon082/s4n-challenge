package utils;

public enum CardinalPoints {

    NORTE("N"), SUR("S"), ORIENTE("OR"), OCCIDENTE("OC");

    String name;

    private CardinalPoints(String name) {
        this.name = name;
    }
}

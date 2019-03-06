package model;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Cities {

    private int size;
    private ArrayList<Double> coordinatesX;
    private ArrayList<Double> coordinatesY;

    public Cities(int size) {
        this.size = size;
        this.coordinatesX = new ArrayList<>();
        this.coordinatesY = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Double> getCoordinatesX() {
        return coordinatesX;
    }

    public ArrayList<Double> getCoordinatesY() {
        return coordinatesY;
    }

    public Double getDistanceFrom(int cityA, int cityB) {
        var difX = coordinatesX.get(cityB) - coordinatesX.get(cityA);
        var difY = coordinatesY.get(cityB) - coordinatesY.get(cityA);

        return sqrt(pow(difX, 2) + pow(difY, 2));
    }

    public static Cities generate(int numberOfCities) {
        var half = numberOfCities / 2;
        var cities = new Cities(numberOfCities);

        for (var i = 1; i <= half; i++) {
            cities.getCoordinatesX().add(1.0);
            cities.getCoordinatesY().add((double) i + 1);
        }
        for (var i = 1; i <= numberOfCities - half; i++) {
            cities.getCoordinatesX().add((double) i + 1);
            cities.getCoordinatesY().add(1.0);
        }

        return cities;
    }
}

package model;

import java.util.ArrayList;

public class Individual {

    private Integer cost;
    private ArrayList<Integer> path;

    public Individual(ArrayList<Integer> path) {
        this.path = path;
        this.cost = null;
    }

    public Integer getCost(Cities cities) {
        if (cost == null) {
            var cost = 0;
            for (var i = 0; i < path.size() - 1; i++) {
                cost += cities.getDistanceFrom(i, i + 1);
            }

            this.cost = cost;
        }
        return cost;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path.toString();
    }
}

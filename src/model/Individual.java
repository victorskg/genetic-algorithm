package model;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.iterate;
import static java.util.stream.IntStream.range;

public class Individual {

    private Double fitness;
    private int numberOfChromosomes;
    private ArrayList<Integer> path;

    public Individual(ArrayList<Integer> path, Cities cities) {
        this.path = path;
        this.numberOfChromosomes = path.size();

        calculateFitness(cities);
    }

    public Double getFitness() {
        return fitness;
    }


    public int getNumberOfChromosomes() {
        return numberOfChromosomes;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path.toString();
    }

    private void calculateFitness(Cities cities) {
        this.fitness = range(0, path.size() - 1).mapToDouble(i -> cities.getDistanceFrom(path.get(i), path.get(i + 1))).sum();
    }

    public ArrayList<Integer> getChromosomeUntil(int randomCut) {
        return range(0, randomCut)
                .mapToObj(i -> path.get(i))
                .collect(toCollection(ArrayList::new));
    }

    public ArrayList<Integer> getChromosomeFrom(int randomCut) {
        return iterate(randomCut, i -> i < numberOfChromosomes, i -> i + 1)
                .mapToObj(i -> path.get(i))
                .collect(toCollection(ArrayList::new));
    }
}

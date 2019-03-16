package model;

import java.util.ArrayList;
import java.util.Comparator;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;

public class Population {

    private ArrayList<Individual> individuals;

    public Population(ArrayList<Individual> individuals) {
        this.individuals = individuals;
        sortByFitness();
    }

    public ArrayList<Individual> getElite(int forReproduction) {
        var elite = range(0, forReproduction).mapToObj(i -> individuals.get(i)).collect(toCollection(ArrayList::new));

        return elite;
    }

    public Individual getIndividual(int individual) {
        return individuals.get(individual);
    }

    public void addNewGeneration(ArrayList<Individual> newGeneration) {
        individuals.addAll(newGeneration);
        sortByFitness();
    }

    public Population naturalSelection(int populationSize) {
        var afterSelection = range(0, populationSize).mapToObj(i -> individuals.get(i)).collect(toCollection(ArrayList::new));

        return new Population(afterSelection);
    }

    private void sortByFitness() {
        individuals.sort(Comparator.comparing(Individual::getFitness));
    }
}

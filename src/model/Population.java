package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Population {

    private ArrayList<Individual> individuals;

    public Population(ArrayList<Individual> individuals) {
        this.individuals = individuals;
        sortByFitness();
    }


    private void sortByFitness() {
        individuals.sort(Comparator.comparing(Individual::getFitness));
    }

    public ArrayList<Individual> getElite(int forReproduction) {
        var elite = new ArrayList<Individual>();
        for (var i = 0; i < forReproduction; i++) {
            elite.add(individuals.get(i));
        }

        return elite;
    }

    public Individual getIndividual(int individual) {
        return individuals.get(individual);
    }
}

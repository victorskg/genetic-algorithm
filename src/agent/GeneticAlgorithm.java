package agent;

import model.Cities;
import model.Individual;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class GeneticAlgorithm {

    private Random random;
    private Cities cities;
    private int startCity;
    private int mutationRate;
    private int populationSize;
    private int maxGenerations;
    private int forReproduction;
    private ArrayList<Individual> initialPopulation;

    public GeneticAlgorithm(Cities cities, int startCity, int mutationRate, int populationSize, int maxGenerations, int forReproduction) {
        this.cities = cities;
        this.startCity = startCity;
        this.random = new Random();
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.forReproduction = forReproduction;
    }

    public void generateInitialPopulation() {
        initialPopulation = new ArrayList<>();
        while (initialPopulation.size() != populationSize) {
            var cities = new ArrayList<Integer>();

            for (var i = 0; i < populationSize; i++) {
                cities.add(i);
            }

            shuffle(cities);
            organize(cities);
            initialPopulation.add(new Individual(cities));
        }
        System.out.println(initialPopulation);
    }

    private void organize(ArrayList<Integer> cities) {
        cities.remove(startCity);
        cities.add(0, startCity);
        cities.add(startCity);
    }
}

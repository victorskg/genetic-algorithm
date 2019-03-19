package agent;

import model.Cities;
import model.Individual;
import model.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toCollection;

public class GeneticAlgorithm {

    private Random random;
    private Cities cities;
    private int startCity;
    private int elitePair;
    private int populationSize;
    private int maxGenerations;
    private int forReproduction;
    private Double mutationRate;
    private int pairsToReproduction;
    private int numberOfChromosomes;
    private Population initialPopulation;

    public GeneticAlgorithm(Cities cities, int startCity, int elitePair, Double mutationRate, int populationSize, int maxGenerations, int forReproduction, int pairsToReproduction) {
        this.cities = cities;
        this.startCity = startCity;
        this.elitePair = elitePair;
        this.random = new Random();
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.forReproduction = forReproduction;
        this.numberOfChromosomes = cities.getSize();
        this.initialPopulation = generatePopulation();
        this.pairsToReproduction = pairsToReproduction;
    }

    public void run() {
        var actualGeneration = 0;
        var actualPopulation = initialPopulation;

        while (actualGeneration < maxGenerations) {
            actualGeneration++;
            var bestIndividual = actualPopulation.getIndividual(0);

            System.out.println("Melhor individuo: " + bestIndividual);

            var nextGeneration = reproductionOperator(actualPopulation);

            mutate(nextGeneration);
            nextGeneration = filterGeneration(nextGeneration);
            actualPopulation.addNewGeneration(nextGeneration);

            actualPopulation = actualPopulation.naturalSelection(populationSize);
        }
    }

    private ArrayList<Individual> filterGeneration(ArrayList<Individual> nextGeneration) {
        return nextGeneration.stream().distinct().collect(toCollection(ArrayList::new));
    }

    private Population generatePopulation() {
        var individuals = new ArrayList<Individual>();
        while (individuals.size() != populationSize) {
            var path = new ArrayList<Integer>();

            for (var i = 0; i < cities.getSize(); i++) {
                path.add(i);
            }

            shuffle(path);
            organize(path);
            individuals.add(new Individual(path, cities));
        }

        return new Population(individuals);
    }

    private ArrayList<Individual> reproductionOperator(Population population) {
        var elite = population.getElite(forReproduction);
        var nextGeneration = new ArrayList<Individual>();

        for (var i = 0; i < pairsToReproduction; i++) {
            var eliteMember = random.nextInt(forReproduction);
            var parent = random.nextInt(elitePair);

            while (parent == i) {
                parent = random.nextInt(elitePair);
            }

            var newIndividuals = crossoverOperator(elite.get(eliteMember), population.getIndividual(parent));
            nextGeneration.addAll(newIndividuals);
        }

        return nextGeneration;
    }

    private List<Individual> crossoverOperator(Individual father, Individual mother) {
        var markerOne = getRandomChromosome();
        var markerTwo = getRandomChromosome();

        while (markerOne == markerTwo) markerTwo = getRandomChromosome();

        return markerOne < markerTwo ? father.crossoverWith(mother, markerOne, markerTwo) : father.crossoverWith(mother, markerTwo, markerOne);
    }

    private void mutate(ArrayList<Individual> nextGeneration) {
        var beta = random.nextDouble();

        for (var individual : nextGeneration) {
            if (beta < mutationRate) {
                var chromosomeOne = getRandomChromosome();
                var chromosomeTwo = getRandomChromosome();

                while (chromosomeOne == chromosomeTwo) {
                    chromosomeTwo = getRandomChromosome();
                }

                individual.mutate(chromosomeOne, chromosomeTwo);
            }
        }
    }

    private void organize(ArrayList<Integer> cities) {
        cities.remove(cities.indexOf(startCity));
        cities.add(0, startCity);
        cities.add(startCity);
    }

    private int getRandomChromosome() {
        return random.nextInt(numberOfChromosomes - 2) + 1;
    }
}

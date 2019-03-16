package agent;

import model.Cities;
import model.Individual;
import model.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

public class GeneticAlgorithm {

    private Random random;
    private Cities cities;
    private int startCity;
    private int populationSize;
    private int maxGenerations;
    private int forReproduction;
    private Double mutationRate;
    private int pairsToReproduction;
    private Population initialPopulation;

    public GeneticAlgorithm(Cities cities, int startCity, Double mutationRate, int populationSize, int maxGenerations, int forReproduction, int pairsToReproduction) {
        this.cities = cities;
        this.startCity = startCity;
        this.random = new Random();
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.forReproduction = forReproduction;
        this.pairsToReproduction = pairsToReproduction;

        generateInitialPopulation();
    }

    public void run() {
        var actualGeneration = 0;
        var actualPopulation = initialPopulation;

        while (actualGeneration < maxGenerations) {
            actualGeneration++;
            System.out.println("Melhor individuo: " + actualPopulation.getIndividual(0).toString());

            var nextGeneration = reproductionOperator(actualPopulation);

            mutate(nextGeneration);
            actualPopulation.addNewGeneration(nextGeneration);
            actualPopulation = actualPopulation.naturalSelection(populationSize);
        }
    }

    private void generateInitialPopulation() {
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

        initialPopulation = new Population(individuals);
    }

    private ArrayList<Individual> reproductionOperator(Population population) {
        var elite = population.getElite(forReproduction);
        var nextGeneration = new ArrayList<Individual>();

        for (var i = 0; i < pairsToReproduction; i++) {
            var eliteMember = random.nextInt(forReproduction);
            var parent = random.nextInt(cities.getSize());

            while (parent == i) {
                parent = random.nextInt(cities.getSize());
            }

            var newIndividuals = crossoverOperator(elite.get(eliteMember), population.getIndividual(parent));
            nextGeneration.addAll(newIndividuals);
        }

        return nextGeneration;
    }

    private List<Individual> crossoverOperator(Individual father, Individual mother) {
        var randomCut = random.nextInt(father.getNumberOfChromosomes() - 2) + 1;

        var fatherFirstChromosome = father.getChromosomeUntil(randomCut);
        var fatherSecondChromosome = father.getChromosomeFrom(randomCut);

        var motherFirstChromosome = mother.getChromosomeUntil(randomCut);
        var motherSecondChromosome = mother.getChromosomeFrom(randomCut);

        var firstChildChromosome = new ArrayList<>(fatherFirstChromosome);
        firstChildChromosome.addAll(motherSecondChromosome);
        var firstChild = new Individual(firstChildChromosome, cities);

        var secondChildChromosome = new ArrayList<>(motherFirstChromosome);
        secondChildChromosome.addAll(fatherSecondChromosome);
        var secondChild = new Individual(secondChildChromosome, cities);

        return List.of(firstChild, secondChild);
    }

    private void mutate(ArrayList<Individual> nextGeneration) {
        var beta = random.nextDouble();

        if (beta < mutationRate) {
            var individualToMutate = nextGeneration.get(random.nextInt(nextGeneration.size()));

            var chromosomeSize = individualToMutate.getNumberOfChromosomes();
            var chromosomeOne = random.nextInt(chromosomeSize);
            var chromosomeTwo = random.nextInt(chromosomeSize);

            while (chromosomeOne == chromosomeTwo) {
                chromosomeTwo = random.nextInt(chromosomeSize);
            }

            individualToMutate.mutate(chromosomeOne, chromosomeTwo);
        }
    }

    private void organize(ArrayList<Integer> cities) {
        cities.remove(startCity);
        cities.add(0, startCity);
        cities.add(startCity);
    }
}

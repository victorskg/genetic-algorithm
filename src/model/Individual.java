package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Individual {

    private Cities cities;
    private Double fitness;
    private int numberOfChromosomes;
    private ArrayList<Integer> path;

    public Individual(ArrayList<Integer> path, Cities cities) {
        this.path = path;
        this.cities = cities;
        this.numberOfChromosomes = path.size();

        calculateFitness();
    }

    public Double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return path.toString() + ": " + fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    private void calculateFitness() {
        this.fitness = range(0, path.size() - 1).mapToDouble(i -> cities.getDistanceFrom(path.get(i), path.get(i + 1))).sum();
    }

    public void mutate(int chromosomeOne, int chromosomeTwo) {
        var one = path.get(chromosomeOne);
        var two = path.get(chromosomeTwo);
        path.set(chromosomeOne, two);
        path.set(chromosomeTwo, one);

        calculateFitness();
    }

    public List<Individual> crossoverWith(Individual mother, int firstCut, int secondCut) {
        var firstChildChromosome = new ArrayList<>(path);
        var fathersFirstChildChromosome = rangeClosed(firstCut, secondCut).mapToObj(i -> path.get(i)).collect(toCollection(ArrayList::new));
        var mothersFirstChildChromosome = range(1, numberOfChromosomes - 1)
                .filter(i -> !fathersFirstChildChromosome.contains(mother.path.get(i)))
                .mapToObj(i -> mother.path.get(i)).collect(toCollection(ArrayList::new));


        var secondChildChromosome = new ArrayList<>(mother.path);
        var motherSecondChildChromosome = rangeClosed(firstCut, secondCut).mapToObj(i -> mother.path.get(i)).collect(toCollection(ArrayList::new));
        var fatherSecondChildChromosome = range(1, numberOfChromosomes - 1)
                .filter(i -> !motherSecondChildChromosome.contains(path.get(i)))
                .mapToObj(i -> path.get(i)).collect(toCollection(ArrayList::new));

        for (var i = 1; i < numberOfChromosomes - 1; i++) {
            if (i < firstCut || i > secondCut) {
                firstChildChromosome.set(i, mothersFirstChildChromosome.remove(0));
                secondChildChromosome.set(i, fatherSecondChildChromosome.remove(0));
            }
        }

        return List.of(new Individual(firstChildChromosome, cities), new Individual(secondChildChromosome, cities));
    }
}

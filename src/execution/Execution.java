package execution;

import agent.GeneticAlgorithm;
import model.Cities;

public class Execution {
    public static void main(String[] args) {
        var cities = Cities.generate(10);
        var ga = new GeneticAlgorithm(cities,
                1,
                0.3,
                1000,
                1000,
                10,
                250);

        ga.run();
    }
}

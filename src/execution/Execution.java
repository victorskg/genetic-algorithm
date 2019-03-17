package execution;

import agent.GeneticAlgorithm;
import model.Cities;

import static java.util.List.of;

public class Execution {
    public static void main(String[] args) {
        var cities = Cities.generate(10);

        var nc = new Cities(20);
        nc.getCoordinatesX().addAll(
                of(10.0, 8.0, 6.0, 5.0, 4.0, 4.0, 5.0, 6.0, 7.0, 8.0, 10.0, 12.0, 13.0, 14.0, 15.0, 16.0, 16.0, 15.0, 14.0, 12.0));
        nc.getCoordinatesY().addAll(
                of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 10.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0));

        var ga = new GeneticAlgorithm(nc,
                0,
                1000,
                0.5,
                1000,
                1000,
                50,
                250);

        ga.run();
    }
}

import dataGenerator.generator.ShotGenerator;
import dataGenerator.generator.TimeQuantumGenerator;
import greedyalgorithm.GreedyAlog;
import greedyalgorithm.entity.Shot;
import greedyalgorithm.entity.TimeQuantum;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        TimeQuantumGenerator timeQuantumGenerator = new TimeQuantumGenerator();
        timeQuantumGenerator.initialSchedule();
        List<TimeQuantum> timeQuantumList = timeQuantumGenerator.generateTimeQuantum(10);
        System.out.println(timeQuantumList);
        ShotGenerator shotGenerator = new ShotGenerator();
        List<Shot> shotList = shotGenerator.generateShots();
        System.out.println(shotList);
        GreedyAlog greedyAlog = new GreedyAlog();
        greedyAlog.greedyAlog(shotList,timeQuantumList);

    }
}

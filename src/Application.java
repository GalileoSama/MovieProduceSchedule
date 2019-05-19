import dataGenerator.generator.ShotGenerator;
import dataGenerator.generator.TimeQuantumGenerator;
import greedyalgorithm.GreedyAlog;
import greedyalgorithm.entity.Shot;
import greedyalgorithm.entity.TimeQuantum;

import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        TimeQuantumGenerator timeQuantumGenerator = new TimeQuantumGenerator();
        timeQuantumGenerator.initialSchedule(2000);
        List<TimeQuantum> timeQuantumList = timeQuantumGenerator.generateTimeQuantum(2000);
        System.out.println(timeQuantumList);
        ShotGenerator shotGenerator = new ShotGenerator();
        List<Shot> shotList = shotGenerator.generateShots();
        System.out.println(shotList);
        GreedyAlog greedyAlog = new GreedyAlog();
        greedyAlog.greedyAlog(shotList,timeQuantumList);

    }
}

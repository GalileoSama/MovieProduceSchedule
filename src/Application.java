import dataGenerator.generator.ShotGenerator;
import dataGenerator.generator.TimeQuantumGenerator;
import greedyalgorithm.GreedyAlog;
import greedyalgorithm.entity.Shot;
import greedyalgorithm.entity.TimeQuantum;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        int day=50;
        TimeQuantumGenerator timeQuantumGenerator = new TimeQuantumGenerator();
        timeQuantumGenerator.initialSchedule(day);
        List<TimeQuantum> timeQuantumList = timeQuantumGenerator.generateTimeQuantum(day);

        ShotGenerator shotGenerator = new ShotGenerator();
        List<Shot> shotList = shotGenerator.generateShots();

        List<TimeQuantum> timeQuantumList4 =new ArrayList<>();
        timeQuantumList4.addAll(timeQuantumList);
        List<Shot> shotList1 = new ArrayList<>();
        shotList1.addAll(shotList);
        GreedyAlog greedyAlog = new GreedyAlog();
        greedyAlog.greedyAlog(shotList1,timeQuantumList4);

        List<TimeQuantum> timeQuantumList1 = new ArrayList<>();
        timeQuantumList1.addAll(timeQuantumList);
        List<Shot> shotList2= new ArrayList<>();
        shotList2.addAll(shotList);
        timeQuantumGenerator.removeActor(timeQuantumList1,day);
        greedyAlog.greedyAlog(shotList2,timeQuantumList1);

        List<TimeQuantum> timeQuantumList2 = new ArrayList<>();
        timeQuantumList2.addAll(timeQuantumList);
        List<Shot> shotList3 = new ArrayList<>();
        shotList3.addAll(shotList);
        timeQuantumGenerator.removeTool(timeQuantumList2,day);
        greedyAlog.greedyAlog(shotList3,timeQuantumList2);

        List<TimeQuantum> timeQuantumList3 = new ArrayList<>();
        timeQuantumList3.addAll(timeQuantumList);
        List<Shot> shotList4 = new ArrayList<>();
        shotList4.addAll(shotList);
        timeQuantumGenerator.removeScene(timeQuantumList3,day);
        greedyAlog.greedyAlog(shotList4,timeQuantumList3);
    }
}

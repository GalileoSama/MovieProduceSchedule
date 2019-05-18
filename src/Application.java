import dataGenerator.generator.ShotGenerator;
import global.GlobalVar;
import greedyAlgorithm.entity.Shot;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        ShotGenerator shotGenerator = new ShotGenerator();
        System.out.println(GlobalVar.ACTORNUM);
        List<Shot> shotList = shotGenerator.generateShots();
        System.out.println("i");
    }
}

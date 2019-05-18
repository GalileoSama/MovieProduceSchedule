import dataGenerator.entity.Scene_;
import dataGenerator.generator.ShotGenerator;
import global.GlobalVar;
import greedyAlgorithm.entity.Shot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        Scene_ scene_1 = new Scene_();scene_1.setId(1);
        Scene_ scene_2 = new Scene_();scene_2.setId(1);
        Set<Scene_> scene_s = new HashSet<>();
        scene_s.add(scene_1);
        scene_s.add(scene_2);
        System.out.println(scene_1.equals( scene_2));
    }
}

package dataGenerator.generator;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Period_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;
import global.GlobalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static global.GlobalVar.actor_list;

/**
 * 生成时间段
 * @author galileo
 * @date 2019/5/18 16:02
 */
public class PeriodGenerator {

    private List<Period_> periodList = new ArrayList<>();
    private Random random = new Random();

    public List<Period_> generatePeriod(int day){
        int today = 1;
        while (today < day){
            Period_ period = new Period_();
            period.setDay(today);
            period.setActorList(getActorList());

            today++;
        }
        return periodList;
    }

    /**
     * 获取随机数
     * @param max 上限
     * @param min 下限
     * @return 随机数
     */
    private int getRandom(int max, int min){
        return random.nextInt(max)%(max-min+1) + min;
    }

    private List<Actor_> getActorList(){
        List<Actor_> actorList = new ArrayList<>();
        int actorNum = getRandom(10, 1);
        for (int i = 0;i<actorNum;i++){
            actorList.add(GlobalVar.getActor_list().get(getRandom(10, 1)));
        }
        return actorList;
    }

    private List<Tool_> getToolList(){
        List<Tool_> toolList = new ArrayList<>();
        int toolNum = getRandom(10, 0);
        for (int i = 0;i<toolNum;i++){
            toolList.add(GlobalVar.getTool_list().get(getRandom(10, 0)));
        }
        return toolList;
    }

    private List<Scene_> getSceneList(){
        List<Scene_> sceneList = new ArrayList<>();
        int sceneNum = getRandom(5, 1);
        for (int i = 0;i < sceneNum; i++){
            sceneList.add(GlobalVar.getScene_list().get(getRandom(5, 1)));
        }
        return sceneList;
    }

}

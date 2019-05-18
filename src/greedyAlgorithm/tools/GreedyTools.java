package greedyAlgorithm.tools;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;
import greedyAlgorithm.entity.Shot;
import greedyAlgorithm.entity.TimeQuantum;

import java.util.ArrayList;
import java.util.List;

/**贪婪算法的工具类*/
public class GreedyTools {
    /**
     * 从某一个时间段中选择可以满足的分镜
     * @author Jiangliangzhong
     * date 2019/5/18 19:40
     * @param shotList 未开拍的镜头
     * @param timeQuantumList 剩余时间段
    */
    public static List<Shot>  findStatisfyShotOnTimeQuantum(List<Shot> shotList, TimeQuantum timeQuantumList){
        List<Shot> result = new ArrayList<>();
        //当前时间段的演员
        List<Actor_> actorListOnTime = timeQuantumList.getActorList();
        //当前时间段的道具
        List<Tool_> toolListOnTime = timeQuantumList.getToolList();
        List<Scene_> sceneListOnTime = timeQuantumList.getSceneList();

        //遍历未开拍的分镜
        for(Shot shot:shotList){
            //判断场景是否满足

        }


        return null;
    }

    /**
     * 将当天可开拍镜头按场景分类
     * @author Jiangliangzhong
     * date 2019/5/18 19:47
     * @param shotList 当天时间段可以满足的镜头
     * @param scene_ 场景
    */
    public static List<Shot> findShotOnScene(List<Shot> shotList, Scene_ scene_){

        return null;
    }
    /**
     * 得到当前时间段某个场景下的演员list
     * @author Jiangliangzhong
     * date 2019/5/18 19:51
     * @param shotListOnScene 当前场景可以满足的分镜
     */
    public static List<Actor_> findActorList(List<Shot> shotListOnScene){

        return null;
    }
    /**
     * 得到当前时间段某个场景下的道具list
     * @author Jiangliangzhong
     * date 2019/5/18 19:53
     * @param shotListOnScene 当前场景可以满足的分镜
    */
    public static List<Tool_> findToolList(List<Shot> shotListOnScene){

        return null;
    }
}

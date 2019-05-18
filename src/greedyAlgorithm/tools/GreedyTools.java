package greedyAlgorithm.tools;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;
import greedyAlgorithm.entity.Shot;
import greedyAlgorithm.entity.TimeQuantum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**贪婪算法的工具类*/
public class GreedyTools {
    /**
     * 判断时间段的场景满足分镜
     * @author Jiangliangzhong
     * date 2019/5/18 20:01
     * @param scene_OnShot 分镜的场景
     * @param sceneList 当前时间段的场景
     */
    private static  boolean judgeSceneSatisfy(Scene_ scene_OnShot, List<Scene_> sceneList){
        for(Scene_ scene_:sceneList){
            if(scene_.getId() == scene_OnShot.getId()){
                return true;
            }
        }
        return false;
    }
    /**
     * 判断时间段的演员是否满足分镜
     * @author Jiangliangzhong
     * date 2019/5/18 20:07
     * @param actorList 当天时间段的演员
     * @param actorListOnShot  分镜的演员
     */
    private static  boolean judgeActorsSatisfy(List<Actor_> actorListOnShot, List<Actor_> actorList){
        return actorList.containsAll(actorListOnShot);
    }
    /**
     * 判断时间段的道具是否满足分镜
     * @author Jiangliangzhong
     * date 2019/5/18 20:07
     * @param toolList 当天时间段的道具
     * @param toolListOnShot  分镜的工具
     */
    private static  boolean judgeToolsSatisfy(List<Tool_> toolListOnShot, List<Tool_> toolList){
        return toolList.containsAll(toolListOnShot);
    }
    /**
     * 从某一个时间段中选择可以满足的分镜
     * @author Jiangliangzhong
     * date 2019/5/18 19:40
     * @param shotList 未开拍的镜头
     * @param timeQuantum 剩余时间段
    */
    public static List<Shot>  findStatisfyShotOnTimeQuantum(List<Shot> shotList, TimeQuantum timeQuantum){
        List<Shot> result = new ArrayList<>();
        //当前时间段的演员
        List<Actor_> actorListOnTime = timeQuantum.getActorList();
        //当前时间段的道具
        List<Tool_> toolListOnTime = timeQuantum.getToolList();
        List<Scene_> sceneListOnTime = timeQuantum.getSceneList();

        //遍历未开拍的分镜
        for(Shot shot:shotList){
            //判断场景是否满足
            if(!judgeSceneSatisfy(shot.getScene(), sceneListOnTime)){
                //不满足，继续下一个分镜
                continue;
            }
            if(!judgeActorsSatisfy(shot.getActorList(), actorListOnTime)){
                //不满足，继续下一个分镜
                continue;
            }
            if(!judgeToolsSatisfy(shot.getToolList(), toolListOnTime)){
                //不满足，继续下一个分镜
                continue;
            }
            //全部满足则添加到结果集中
            result.add(shot);
        }
        return result;
    }

    /**
     * 将当天可开拍镜头按场景分类
     * @author Jiangliangzhong
     * date 2019/5/18 19:47
     * @param shotList 当天时间段可以满足的镜头
     * @param scene_ 场景
    */
    public static List<Shot> findShotOnScene(List<Shot> shotList, Scene_ scene_){
        List<Shot> result = new ArrayList<>();
        for(Shot shot:shotList){
            if(shot.getScene().equals( scene_)){
                result.add(shot);
            }
        }
        return result;
    }
    /**
     * 得到当前时间段某个场景下的演员list
     * @author Jiangliangzhong
     * date 2019/5/18 19:51
     * @param shotListOnScene 当前场景可以满足的分镜
     */
    public static List<Actor_> findActorList(List<Shot> shotListOnScene){
        Set<Actor_> actorSet = new HashSet<>();
        for(Shot shot:shotListOnScene){
            actorSet.addAll(shot.getActorList());
        }
        return new ArrayList<>(actorSet);
    }
    /**
     * 得到当前时间段某个场景下的道具list
     * @author Jiangliangzhong
     * date 2019/5/18 19:53
     * @param shotListOnScene 当前场景可以满足的分镜
    */
    public static List<Tool_> findToolList(List<Shot> shotListOnScene){
        Set<Tool_> ToolSet = new HashSet<>();
        for(Shot shot:shotListOnScene){
            ToolSet.addAll(shot.getToolList());
        }
        return new ArrayList<>(ToolSet);
    }
}

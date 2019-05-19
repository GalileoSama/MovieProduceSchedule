package greedyalgorithm.tools;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;
import greedyalgorithm.entity.Shot;
import greedyalgorithm.entity.SimliarShot;
import greedyalgorithm.entity.TimeQuantum;

import java.util.*;

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
    /**
     * 计算两个分镜的相似程度
     * @author Jiangliangzhong
     * date 2019/5/18 21:15
    */
    public static double calShotSimliar(Shot shot1, Shot shot2){
        //求两个演员list的交集
        Set<Actor_> mixActorSet = new HashSet<>();
        Set<Actor_> unionActorSet = new HashSet<>();
        List<Actor_> actorList1 = shot1.getActorList();
        List<Actor_> actorList2 = shot2.getActorList();
        for(int i=0;i<shot1.getActorList().size();i++){
            for(int j =0;j<shot2.getActorList().size();j++){
                if(actorList1.get(i).equals(actorList2.get(j))){
                    mixActorSet.add(actorList1.get(i));
                }
            }
        }
        //求并集
        unionActorSet.addAll(actorList1);
        unionActorSet.addAll(actorList2);
        double actorSim = (double)mixActorSet.size()/unionActorSet.size();
        //求两个道具的相似度
        Set<Tool_> mixToooSet = new HashSet<>();
        Set<Tool_> unionToolSet = new HashSet<>();
        List<Tool_> toolList1 = shot1.getToolList();
        List<Tool_> toolList2 = shot2.getToolList();
        for(int i=0;i<shot1.getToolList().size();i++){
            for(int j =0;j<shot2.getToolList().size();j++){
                if(toolList1.get(i).equals(toolList2.get(j))){
                    mixToooSet.add(toolList1.get(i));
                }
            }
        }
        //求并集
        unionToolSet.addAll(toolList1);
        unionToolSet.addAll(toolList2);
        double toolSim = (double)mixToooSet.size()/unionToolSet.size();
        double toolWeight = 0.5;
        double actorWeight = 0.5;
        return toolWeight * toolSim + actorWeight * actorSim;
    }

    /**
     * 计算以一个分镜为基准，与分镜之间的相似度,选出相似度最高的分镜
     * @author Jiangliangzhong
     * date 2019/5/18 21:31
    */
    public  static SimliarShot calShotAndShotsSimliar(Shot shotSrc, List<Shot> shotDestList){
        SimliarShot simliarShot = new SimliarShot();
        simliarShot.setShotSrc(shotSrc);
        simliarShot.setShotDest(shotDestList.get(0));
        simliarShot.setSimlar(calShotSimliar(shotSrc,shotDestList.get(0)));

        for(int i=1; i< shotDestList.size();i++){
            double simliar = calShotSimliar(shotSrc, shotDestList.get(i));
            if(simliar >  simliarShot.getSimlar()){
                simliarShot.setShotDest(shotDestList.get(i));
                simliarShot.setSimlar(simliar);
            }
        }
        return simliarShot;
    }
    /**
     * 分镜选择
     * @author Jiangliangzhong
     * date 2019/5/18 22:01
     * @param shotListOnScene 当天时间该场景下的可拍镜头
     * @param Urgent  每个分镜的紧迫程度
     * @return 返回这个时间段所选择的分镜
    */
    public static List<Shot> selectShotOnTime(List<Shot> shotListOnScene, Map<Integer, Float> Urgent){
        //以分钟为单位
        int totalTime = 8 * 60;
        //选择出紧迫度最大的分镜
        Set<Integer> keySet = Urgent.keySet();
        List<Integer> keyList = new ArrayList<>(keySet);
        int max = keyList.get(0);
        //得到最大的值
        for(Integer key:keyList){
            if(Urgent.get(max)< Urgent.get(key)){
                max = key;
            }
        }
        List<Shot> result = new ArrayList<>();
        final int finalmax = max;
        //挑选起始镜头
        Shot lastShot=null;
        for(Shot shot:shotListOnScene){
            if(shot.getId()==finalmax){
                lastShot=shot;
            }
        }
        if(lastShot == null){
            System.err.println("错误：无法找到紧迫度最大的shot");
            return null;
        }
        result.add(lastShot);
        totalTime -= lastShot.getTime();
        shotListOnScene.remove(lastShot);
        //不能超过8小时
        if(totalTime <0){
            return result;
        }
        //接下来按照关联度来选择
        while(totalTime > 0 && shotListOnScene.size()!=0){
            //得到最高关联度的镜头
            SimliarShot simliarShot = calShotAndShotsSimliar(result.get(result.size()-1), shotListOnScene);
            result.add(simliarShot.getShotDest());
            totalTime -= simliarShot.getShotDest().getTime();
            shotListOnScene.remove(simliarShot.getShotDest());
        }
        return result;
    }

}

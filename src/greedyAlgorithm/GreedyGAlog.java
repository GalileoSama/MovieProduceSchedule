package greedyAlgorithm;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;
import greedyAlgorithm.entity.Shot;
import greedyAlgorithm.entity.TimeQuantum;
import greedyAlgorithm.tools.GreedyTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiang .
 * @date 2019/5/1815:55
 */
public class GreedyGAlog {
    private final static float W0=0.4f;
    private final static float W1=0.4f;
    private final static float W2=0.2f;

    private final static float W3=0.3f;
    private final static float W4=0.3f;
    private final static float W5=0.4f;

    void greedyAlog(List<Shot> shotList, List<TimeQuantum> timeQuantumList){
        for(TimeQuantum timeQuantum_dangtian:timeQuantumList){
            //该时间段的演员紧迫程度map
            Map<Integer,Float> actorMap = new HashMap<>();
            //该时间段的道具紧迫程度
            Map<Integer,Float> toolMap = new HashMap<>();
            //该时间段的场景紧迫程度
            Map<Integer,Float> sceneMap = new HashMap<>();
            //该时间段场景的优先级
            Map<Integer,Float> scenePrioMap = new HashMap<>();


            List<Actor_> actor_list = timeQuantum_dangtian.getActorList();
            List<Tool_> tool_list = timeQuantum_dangtian.getToolList();
            List<Scene_> scene_list = timeQuantum_dangtian.getSceneList();
            //该时间段内的所有演员的连续拍摄天数
            float actor_totaldays=totalActorShotDays(timeQuantumList,actor_list);
            //该时间段内的所有道具的连续拍摄天数
            float tool_totaldays=totalToolShotDays(timeQuantumList,tool_list);
            //该时间段内的所有场景的连续拍摄天数
            float scene_totaldays=totalSceneShotDays(timeQuantumList,scene_list);

            //计算单个场景的剩余连续拍摄时间，并计算紧迫程度
            for(Scene_ scene_:scene_list){
                //该时间段内的某场景连续拍摄天数
                float days =0;
                for(TimeQuantum timeQuantum_scene_total:timeQuantumList){
                    List<Scene_> scene_bianli = timeQuantum_scene_total.getSceneList();
                    //判断每个时间段内的场景数是否遍历完
                    int size = 0;
                    for(Scene_ scene_1:scene_bianli){
                        if(scene_.getId()==scene_1.getId()){
                            days++;
                            break;
                        }
                        size++;
                    }
                    if(size==scene_bianli.size()){
                        break;
                    }
                }
                //计算场景的紧迫程度
                float urgent = days/scene_totaldays;
                sceneMap.put(scene_.getId(),urgent);
            }


            //计算单个道具的剩余连续拍摄时间，并计算紧迫程度
            for(Tool_ tool_:tool_list){
                //该时间段内的某道具连续拍摄天数
                float days =0;
                for(TimeQuantum timeQuantum_tool_total:timeQuantumList){
                    List<Tool_> tool_bianli = timeQuantum_tool_total.getToolList();
                    //判断每个时间段内的道具数是否遍历完
                    int size = 0;
                    for(Tool_ tool_1:tool_bianli){
                        if(tool_.getId()==tool_1.getId()){
                            days++;
                            break;
                        }
                        size++;
                    }
                    if(size==tool_bianli.size()){
                        break;
                    }
                }
                //计算道具的紧迫程度
                float urgent = days/tool_totaldays;
                toolMap.put(tool_.getId(),urgent);

            }


            //计算单个演员的剩余连续拍摄时间，并计算紧迫程度

            for(Actor_ actor_:actor_list){
                //该时间段内的某演员连续拍摄天数
                float days =0;
                for(TimeQuantum timeQuantum_actor_single:timeQuantumList){
                    List<Actor_> actor_bianli = timeQuantum_actor_single.getActorList();
                    //判断每个时间段内的演员数是否遍历完
                    int size = 0;
                    for(Actor_ actor:actor_bianli){
                        if(actor_.getId()==actor.getId()){
                            days++;
                            break;
                        }
                        size++;
                    }
                    if(size==actor_bianli.size()){
                        break;
                    }
                }
                //计算演员的紧迫程度
                float urgent = days/actor_totaldays;
                actorMap.put(actor_.getId(),urgent);
            }


            //计算优先级
            for(Scene_ scene_:scene_list){
                float scene_prior=scenePrority(shotList,timeQuantum_dangtian,scene_,actorMap,toolMap,sceneMap);
                scenePrioMap.put(scene_.getId(),scene_prior);
            }
            //开始选场景
            List<Shot> dangtian_shot = chooseScene(scenePrioMap);
        }

    }
    /**
     * @Author:xiangchao
     * @Date:2019/5/18_20:37
     * @Description: 选场景
    */
    List<Shot> chooseScene(Map<Integer,Float> scenePrioMap){

    }

    /**
     * @Author:xiangchao
     * @Date:2019/5/18_19:51
     * @Description: 计算场景优先级
    */
    float scenePrority(List<Shot> shotList,TimeQuantum timeQuantum_dangtian,Scene_ scene_,Map<Integer,Float> actorMap,Map<Integer,Float> toolMap,Map<Integer,Float> sceneMap){
        List<Shot> shot_list =GreedyTools.findStatisfyShotOnTimeQuantum(shotList,timeQuantum_dangtian);
        List<Shot> ava_shot_list=GreedyTools.findShotOnScene(shot_list,scene_);
        List<Actor_> actor_list=GreedyTools.findActorList(ava_shot_list);
        List<Tool_> tool_list=GreedyTools.findToolList(ava_shot_list);
        float actor_total_urgent=0;
        float actor_min=999;
        float actor_average_urgent=0;
        float tool_total_urgent=0;
        float tool_min=999;
        float tool_average_urgent=0;
        for(Actor_ actor_:actor_list){
            float urgent=actorMap.get(actor_.getId());
            actor_total_urgent+=urgent;
            if(actor_min>urgent){
                actor_min=urgent;
            }
        }
        actor_average_urgent=actor_total_urgent/actor_list.size();
        for(Tool_ tool_:tool_list){
            float urgent = toolMap.get(tool_.getId());
            tool_total_urgent+=urgent;
            if(tool_min>urgent){
                tool_min=urgent;
            }
        }
        tool_average_urgent=tool_total_urgent/tool_list.size();

        float scene_prior=0;
        scene_prior=W0/(W3*actor_average_urgent+W4*tool_average_urgent+sceneMap.get(scene_.getId()))+W1/actor_min+W2/tool_min;
        return scene_prior;

    }

    /**
     * @Author:xiangchao
     * @Date:2019/5/18_17:29
     * @Description: 计算所有场景的剩余连续拍摄时间
    */
    float totalSceneShotDays(List<TimeQuantum> timeQuantumList,List<Scene_> scene_list){
        float scene_totaldays=0;
        for(Scene_ scene_:scene_list){
            for(TimeQuantum timeQuantum_scene_total:timeQuantumList){
                List<Scene_> scene_bianli = timeQuantum_scene_total.getSceneList();
                //判断每个时间段内的道具数是否遍历完
                int size = 0;
                for(Scene_ scene_1:scene_bianli){
                    if(scene_.getId()==scene_1.getId()){
                        scene_totaldays++;
                        break;
                    }
                    size++;
                }
                if(size==scene_bianli.size()){
                    break;
                }
            }
        }
        return scene_totaldays;
    }

    /**
     * @Author:xiangchao
     * @Date:2019/5/18_17:08
     * @Description: 计算所有道具的剩余连续拍摄时间
    */

    float totalToolShotDays(List<TimeQuantum> timeQuantumList,List<Tool_> tool_list){
        float tool_totaldays=0;
        for(Tool_ tool_:tool_list){
            for(TimeQuantum timeQuantum_tool_total:timeQuantumList){
                List<Tool_> tool_bianli = timeQuantum_tool_total.getToolList();
                //判断每个时间段内的道具数是否遍历完
                int size = 0;
                for(Tool_ tool_1:tool_bianli){
                    if(tool_.getId()==tool_1.getId()){
                        tool_totaldays++;
                        break;
                    }
                    size++;
                }
                if(size==tool_bianli.size()){
                    break;
                }
            }
        }
        return tool_totaldays;
    }

    /**
     * @Author:xiangchao
     * @Date:2019/5/18_17:08
     * @Description: 计算所有演员的剩余连续拍摄时间
    */

    float totalActorShotDays(List<TimeQuantum> timeQuantumList,List<Actor_> actor_list){
        float actor_totaldays=0;
        //计算该时间段所有演员的剩余连续拍摄时间
        for(Actor_ actor_:actor_list){
            for(TimeQuantum timeQuantum_actor_total:timeQuantumList){
                List<Actor_> actor_bianli = timeQuantum_actor_total.getActorList();
                //判断每个时间段内的演员数是否遍历完
                int size = 0;
                for(Actor_ actor:actor_bianli){
                    if(actor_.getId()==actor.getId()){
                        actor_totaldays++;
                        break;
                    }
                    size++;
                }
                if(size==actor_bianli.size()){
                    break;
                }
            }
        }
        return actor_totaldays;
    }



}

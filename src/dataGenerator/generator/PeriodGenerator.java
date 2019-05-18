package dataGenerator.generator;

import dataGenerator.entity.*;
import global.GlobalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static global.GlobalVar.actor_list;
import static global.GlobalVar.scene_list;
import static global.GlobalVar.tool_list;

/**
 * 生成时间段
 * @author galileo
 * @date 2019/5/18 16:02
 */
public class PeriodGenerator {

    private List<Period_> periodList = new ArrayList<>();
    private Random random = new Random(1000);

    public List<Period_> generatePeriod(int day){
        int today = 1;
        while (today < day){
            Period_ period = new Period_();
            period.setDay(today);
            //演员
            List<Actor_> actorList = new ArrayList<>();
            for (Actor_ actor : actor_list){
                List<Schedule> scheduleList = actor.getScheduleList();
                //检查是否有档期 今天
                for (Schedule schedule : scheduleList){
                    if (schedule.containDay(today)){
                        actorList.add(actor);
                        break;
                    }
                }
            }
            period.setActorList(actorList);
            //道具
            List<Tool_> toolList = new ArrayList<>();
            for (Tool_ tool : tool_list){
                List<Schedule> scheduleList = tool.getScheduleList();
                //检查是否有档期 今天
                for (Schedule schedule : scheduleList){
                    if (schedule.containDay(today)){
                        toolList.add(tool);
                        break;
                    }
                }
            }
            period.setToolList(toolList);
            //场景
            List<Scene_> sceneList = new ArrayList<>();
            for (Scene_ scene : scene_list){
                List<Schedule> scheduleList = scene.getScheduleList();
                //检查是否有档期 今天
                for (Schedule schedule : scheduleList){
                    if (schedule.containDay(today)){
                        sceneList.add(scene);
                        break;
                    }
                }
            }
            period.setSceneList(sceneList);

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

    /**
     * 初始化演员、道具、场景的使用期
     */
    private void initialSchedule(){
        //初始化每个演员的档期
        for (Actor_ actor : actor_list){
            int scheduleNum = getRandom(20, 10);
            List<Schedule> schedules = new ArrayList<>();
            int day = 1;
            day += getRandom(3, 0);
            for (int i = 0;i < scheduleNum; i++){
                Schedule schedule = new Schedule();
                //设置可工作档期
                int random = getRandom(30, 7);
                schedule.setStartTime(day);
                day += random;
                schedule.setEndTime(day);
                //设置不可工作的档期
                int randomDis = getRandom(20, 2);
                day += randomDis;
            }
            actor.setScheduleList(schedules);
        }

        //初始化每个道具的档期
        for (Tool_ tool : tool_list){
            int scheduleNum = getRandom(20, 10);
            List<Schedule> schedules = new ArrayList<>();
            int day = 1;
            day += getRandom(3, 0);
            for (int i = 0;i < scheduleNum; i++){
                Schedule schedule = new Schedule();
                //设置可工作档期
                int random = getRandom(3, 1);
                schedule.setStartTime(day);
                day += random;
                schedule.setEndTime(day);
                //设置不可工作的档期
                int randomDis = getRandom(3, 1);
                day += randomDis;
            }
            tool.setScheduleList(schedules);
        }

        //初始化每个场景的档期
        for (Scene_ scene : scene_list){
            int scheduleNum = getRandom(20, 10);
            List<Schedule> schedules = new ArrayList<>();
            int day = 1;
            day += getRandom(3, 0);
            for (int i = 0;i < scheduleNum; i++){
                Schedule schedule = new Schedule();
                //设置可工作档期
                int random = getRandom(7, 3);
                schedule.setStartTime(day);
                day += random;
                schedule.setEndTime(day);
                //设置不可工作的档期
                int randomDis = getRandom(7, 3);
                day += randomDis;
            }
            scene.setScheduleList(schedules);
        }

    }

}

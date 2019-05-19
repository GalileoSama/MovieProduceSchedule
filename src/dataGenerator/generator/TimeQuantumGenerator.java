package dataGenerator.generator;

import dataGenerator.entity.*;
import greedyAlgorithm.entity.TimeQuantum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static global.GlobalVar.*;

/**
 * 生成时间段
 * @author galileo
 * @date 2019/5/18 16:02
 */
public class TimeQuantumGenerator {

    private List<TimeQuantum> periodList = new ArrayList<>();
    private Random random = new Random(1000);

    /**
     * 生成时间段
     * @param day 生成时间段的总天数
     * @return 时间段，包含每个时间段可用的演员、特殊道具、场景
     */
    public List<TimeQuantum> generateTimeQuantum(int day){
        int today = 1;
        while (today < day){
            TimeQuantum period = new TimeQuantum();
            period.setDay(today);
            //设置当天的有档期的演员
            List<Actor_> actorList = new ArrayList<>();
            for (Actor_ actor : actor_list){
                List<Schedule> scheduleList = actor.getScheduleList();
                //检查是否有档期
                for (Schedule schedule : scheduleList){
                    if(schedule.getStartTime() > day){
                        break;
                    }
                    if (schedule.containDay(today)){
                        actorList.add(actor);
                        break;
                    }
                }
            }
            period.setActorList(actorList);
            //设置当天的可使用的道具
            List<Tool_> toolList = new ArrayList<>();
            for (Tool_ tool : tool_list){
                List<Schedule> scheduleList = tool.getScheduleList();
                //检查是否有档期
                for (Schedule schedule : scheduleList){
                    if(schedule.getStartTime() > day){
                        break;
                    }
                    if (schedule.containDay(today)){
                        toolList.add(tool);
                        break;
                    }
                }
            }
            period.setToolList(toolList);
            //设置当天的可使用的场景
            List<Scene_> sceneList = new ArrayList<>();
            for (Scene_ scene : scene_list){
                List<Schedule> scheduleList = scene.getScheduleList();
                //检查是否有档期
                for (Schedule schedule : scheduleList){
                    if(schedule.getStartTime() > day){
                        break;
                    }
                    if (schedule.containDay(today)){
                        sceneList.add(scene);
                        break;
                    }
                }
            }
            period.setSceneList(sceneList);

            periodList.add(period);
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
     * 第index个电影资源发生突发情况无法获得
     * @param resource 资源：A,S,T
     * @param index 编号，从0开始
     */
    public void disableMovieResource(String resource, int index){
        if ("A".equals(resource)){
            Actor_ actor = actor_list.get(index);
            actor.setScheduleList(new ArrayList<>());
        }else if ("S".equals(resource)){
            Scene_ scene = scene_list.get(index);
            scene.setScheduleList(new ArrayList<>());
        }else if ("T".equals(resource)){
            Tool_ tool = tool_list.get(index);
            tool.setScheduleList(new ArrayList<>());
        }
    }

    public void removeActor(Period_ period,int time){
        List<Actor_> actorList = period.getActorList();
        for (Actor_ actor:actorList){
            List<Schedule> schedules = new ArrayList<>();
            Schedule schedule = new Schedule();
            schedule.setStartTime(1);
            schedule.setEndTime(time);
            schedules.add(schedule);
            actor.setScheduleList(schedules);
        }
    }

    public void removeScene(Period_ period, int time){
        List<Scene_> actorList = period.getSceneList();
        for (Scene_ scene:actorList){
            List<Schedule> schedules = new ArrayList<>();
            Schedule schedule = new Schedule();
            schedule.setStartTime(1);
            schedule.setEndTime(time);
            schedules.add(schedule);
            scene.setScheduleList(schedules);
        }
    }

    public void removeTool(Period_ period, int time){
        List<Tool_> actorList = period.getToolList();
        for (Tool_ tool:actorList){
            List<Schedule> schedules = new ArrayList<>();
            Schedule schedule = new Schedule();
            schedule.setStartTime(1);
            schedule.setEndTime(time);
            schedules.add(schedule);
            tool.setScheduleList(schedules);
        }
    }


    /**
     * 初始化演员、道具、场景的使用期
     */
    public void initialSchedule(int time){
        //初始化每个演员的档期
        for (Actor_ actor : actor_list){
            int scheduleNum = getRandom(time+5, time);
            List<Schedule> schedules = new ArrayList<>();
            int day = 1;
//            day += getRandom(3, 0);
            for (int i = 0;i < scheduleNum; i++){
                Schedule schedule = new Schedule();
                //设置可工作档期
                int random = getRandom(30, 7);
                schedule.setStartTime(day);
                day += random;
                schedule.setEndTime(day);
                //设置不可工作的档期
                int randomDis = getRandom(4, 1);
                day += randomDis;
                schedules.add(schedule);
            }
            actor.setScheduleList(schedules);
        }

        //初始化每个道具的档期
        for (Tool_ tool : tool_list){
            int scheduleNum = getRandom(time*3 +5, time);
            List<Schedule> schedules = new ArrayList<>();
            int day = 1;
//            day += getRandom(3, 0);
            for (int i = 0;i < scheduleNum; i++){
                Schedule schedule = new Schedule();
                //设置可工作档期
                int random = getRandom(4, 1);
                schedule.setStartTime(day);
                day += random;
                schedule.setEndTime(day);
                //设置不可工作的档期
                int randomDis = getRandom(4, 1);
                day += randomDis;
                schedules.add(schedule);
            }
            tool.setScheduleList(schedules);
        }

        //初始化每个场景的档期
        for (Scene_ scene : scene_list){
            int scheduleNum = getRandom(time+15, time+5);
            List<Schedule> schedules = new ArrayList<>();
            int day = 1;
//            day += getRandom(3, 0);
            for (int i = 0;i < scheduleNum; i++){
                Schedule schedule = new Schedule();
                //设置可工作档期
                int random = getRandom(7, 3);
                schedule.setStartTime(day);
                day += random;
                schedule.setEndTime(day);
                //设置不可工作的档期
                int randomDis = getRandom(4, 1);
                day += randomDis;
                schedules.add(schedule);
            }
            scene.setScheduleList(schedules);
        }

    }

}

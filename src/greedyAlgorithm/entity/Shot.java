package greedyAlgorithm.entity;


import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;

import java.util.List;

/**
 * @author xiang .
 * @date 2019/5/1815:17
 */
public class Shot {
    private int time;
    private List<Actor_> actorList;
    private List<Tool_> toolList;
    private Scene_ scene;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Actor_> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor_> actorList) {
        this.actorList = actorList;
    }

    public List<Tool_> getToolList() {
        return toolList;
    }

    public void setToolList(List<Tool_> toolList) {
        this.toolList = toolList;
    }

    public Scene_ getScene() {
        return scene;
    }

    public void setScene(Scene_ scene) {
        this.scene = scene;
    }
}

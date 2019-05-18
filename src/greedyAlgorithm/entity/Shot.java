package greedyAlgorithm.entity;

import entity.Actor;
import entity.Scene;
import entity.Tool;

import java.util.List;

/**
 * @author xiang .
 * @date 2019/5/1815:17
 */
public class Shot {
    private int time;
    private List<entity.Actor> actorList;
    private List<entity.Tool> toolList;
    private entity.Scene scene;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<Tool> getToolList() {
        return toolList;
    }

    public void setToolList(List<Tool> toolList) {
        this.toolList = toolList;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}

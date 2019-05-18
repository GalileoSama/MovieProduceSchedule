package src.entity;

import entity.Actor;
import entity.Scene;
import entity.Tool;

import java.util.List;

/**
 * @author xiang .
 * @date 2019/5/1815:16
 */
public class TimeQuantum {
    private List<entity.Scene> sceneList;
    private List<entity.Actor> actorList;
    private List<entity.Tool> toolList;

    public List<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
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
}

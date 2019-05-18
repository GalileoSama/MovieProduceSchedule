package greedyAlgorithm.entity;


import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;

import java.util.List;

/**
 * @author xiang .
 * @date 2019/5/1815:16
 */
public class TimeQuantum {
    private List<Scene_> sceneList;
    private List<Actor_> actorList;
    private List<Tool_> toolList;

    public List<Scene_> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene_> sceneList) {
        this.sceneList = sceneList;
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
}

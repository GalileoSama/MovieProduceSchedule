package dataGenerator.entity;

import java.util.List;

/**
 * 时间段类
 * @author galileo
 * @date 2019/5/18 15:56
 */
public class Period_ {
    private List<Actor_> actorList;
    private List<Scene_> sceneList;
    private List<Tool_> toolList;

    public List<Actor_> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor_> actorList) {
        this.actorList = actorList;
    }

    public List<Scene_> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene_> sceneList) {
        this.sceneList = sceneList;
    }

    public List<Tool_> getToolList() {
        return toolList;
    }

    public void setToolList(List<Tool_> toolList) {
        this.toolList = toolList;
    }
}

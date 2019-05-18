package global;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Shot_;
import dataGenerator.entity.Tool_;

import java.util.List;

/**
 * 全部变量，演员，道具，场景
 * */
public class GlobalVar {
    private List<Actor_> actor_list;
    private List<Scene_> scene_list;
    private List<Tool_> tool_list;

    public GlobalVar() {

    }

    public List<Actor_> getActor_list() {
        return actor_list;
    }

    public void setActor_list(List<Actor_> actor_list) {
        this.actor_list = actor_list;
    }

    public List<Scene_> getScene_list() {
        return scene_list;
    }

    public void setScene_list(List<Scene_> scene_list) {
        this.scene_list = scene_list;
    }

    public List<Tool_> getTool_list() {
        return tool_list;
    }

    public void setTool_list(List<Tool_> tool_list) {
        this.tool_list = tool_list;
    }
}

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
    private static List<Actor_> actor_list;
    private static List<Scene_> scene_list;
    private static List<Tool_> tool_list;

    static {
        //生成演员数据
        for(int i =0;i< 10;i++){
            Actor_ actor_ = new Actor_();
            actor_.setId(i);
            actor_list.add(actor_);
        }
        //生成场景数据
        for(int i =0;i< 5;i++){
            Scene_ scene_ = new Scene_();
            scene_.setId(i);
            scene_list.add(scene_);
        }
        //生成道具数据
        for(int i =0;i< 10;i++){
            Tool_ tool_ = new Tool_();
            tool_.setId(i);
            tool_list.add(tool_);
        }
    }
    public GlobalVar() {

    }

    public static List<Actor_> getActor_list() {
        return actor_list;
    }

    public static List<Scene_> getScene_list() {
        return scene_list;
    }

    public static List<Tool_> getTool_list() {
        return tool_list;
    }
}

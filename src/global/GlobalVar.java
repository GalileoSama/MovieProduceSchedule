package global;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部变量，演员，道具，场景
 * */
public class GlobalVar {
    public static List<Actor_> actor_list=new ArrayList<>();
    public static List<Scene_> scene_list=new ArrayList<>();
    public static List<Tool_> tool_list=new ArrayList<>();
    /**演员数量*/
    public static final int ACTORNUM = 6;
    /**场景数量*/
    public static final int SCENENUM = 5;
    /**道具数量*/
    public static final int TOOLNUM = 2;

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



}

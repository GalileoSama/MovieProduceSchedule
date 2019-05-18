package dataGenerator.generator;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Tool_;
import global.GlobalVar;
import greedyAlgorithm.entity.Shot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成数据
 * */
public class ShotGenerator {
    /**分镜的数量*/
    private static final int SHOTNUM = 3;
    /**
     * 生成分镜
     * @author Jiangliangzhong
     * date 2019/5/18 16:13
    */
    public List<Shot> generateShots(){
        List<Shot> shotList = new ArrayList<>();
        //每个分镜分别生成
        for(int n = 0;n< SHOTNUM;n++){
            Shot shot = new Shot();
            //1.随机生成时长
            Random random = new Random(1000);//指定种子数字
            //2.随机生成演员数量
            int actorNum = randomOneDigit(0, GlobalVar.ACTORNUM);
            //3.随机抽取演员, 在演员编号范围内
            int[] actorArr = randomCommon(0, GlobalVar.ACTORNUM, actorNum);
            if(actorArr == null){
                System.err.println("产生空数组actorArr");
                return null;
            }
            List<Actor_> actorList = new ArrayList<>();
            //添加每个演员到actorList
            for(int i=0;i<actorNum;i++){
                actorList.add(GlobalVar.actor_list.get(actorArr[i]));
            }
            shot.setActorList(actorList);
            //4.随机生成道具数量
            int toolNum = randomOneDigit(0, GlobalVar.TOOLNUM);
            //5.随机抽取道具
            int[] toolArr = randomCommon(0, GlobalVar.TOOLNUM, toolNum);
            if(toolArr == null){
                System.err.println("产生空数组toolArr");
                return null;
            }
            List<Tool_> toolList = new ArrayList<>();
            //添加每个道具到toolList
            for(int i=0;i<toolNum;i++){
                toolList.add(GlobalVar.tool_list.get(toolArr[i]));
            }
            shot.setToolList(toolList);
            //6.随机抽取场景
            int sceneNum = randomOneDigit(0, GlobalVar.TOOLNUM);
            shot.setScene(GlobalVar.scene_list.get(sceneNum));

            //将生成的分镜添加到分镜list中
            shotList.add(shot);
        }
        return shotList;
    }
    /**
     * 随机生成多个不重复数字
     * @author Jiangliangzhong
     * date 2019/5/18 16:22
    */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
    /**
     * 随机生成一个数字
     * @author Jiangliangzhong
     * date 2019/5/18 16:28
    */
    public static int randomOneDigit(int min, int max){
        return (int) (Math.random() * (max - min)) + min;

    }
}

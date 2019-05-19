package greedyalgorithm;

import dataGenerator.entity.Actor_;
import dataGenerator.entity.Resource;
import dataGenerator.entity.Scene_;
import dataGenerator.entity.Tool_;
import greedyalgorithm.entity.Shot;
import greedyalgorithm.entity.ShotResult;
import greedyalgorithm.entity.TimeQuantum;
import greedyalgorithm.tools.GreedyTools;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiang .
 * @date 2019/5/1815:55
 */
public class GreedyAlog {
    private final static float W0=0.2f;
    private final static float W1=0.4f;
    private final static float W2=0.4f;

    private final static float W3=0.4f;
    private final static float W4=0.4f;
    private final static float W5=0.2f;

    private final static float W6=0.5f;
    private final static float W7=0.5f;



   public void greedyAlog(List<Shot> shotList, List<TimeQuantum> timeQuantumList) throws Exception {
       resourceExcel(shotList,timeQuantumList);
       List<ShotResult> shotResultList = new ArrayList<>();
            for (TimeQuantum timequantumDangtian : timeQuantumList) {
                //该时间段的演员紧迫程度map
                Map<Integer, Float> actorMap = new HashMap<>(20);
                //该时间段的道具紧迫程度
                Map<Integer, Float> toolMap = new HashMap<>(20);
                //该时间段的场景紧迫程度
                Map<Integer, Float> sceneMap = new HashMap<>(20);
                //该时间段场景的优先级
                Map<Integer, Float> scenePrioMap = new HashMap<>(20);


                List<Actor_> actorList = timequantumDangtian.getActorList();
                List<Tool_> toolList = timequantumDangtian.getToolList();
                List<Scene_> sceneList = timequantumDangtian.getSceneList();
                //该时间段内的所有演员的连续拍摄天数
                float actorTotaldays = totalActorShotDays(timeQuantumList, actorList);
                //该时间段内的所有道具的连续拍摄天数
                float toolTotaldays = totalToolShotDays(timeQuantumList, toolList);
                //该时间段内的所有场景的连续拍摄天数
                float sceneTotaldays = totalSceneShotDays(timeQuantumList, sceneList);

                //计算单个场景的剩余连续拍摄时间，并计算紧迫程度
                for (Scene_ scene : sceneList) {
                    //该时间段内的某场景连续拍摄天数
                    float days = singleResourceShot(timeQuantumList,scene);
                    //计算场景的紧迫程度
                    float urgent = days / sceneTotaldays;
                    sceneMap.put(scene.getId(), urgent);
                }
                //计算单个道具的剩余连续拍摄时间，并计算紧迫程度
                for (Tool_ tool : toolList) {
                    //该时间段内的某道具连续拍摄天数
                    float days = singleResourceShot(timeQuantumList,tool);
                    //计算道具的紧迫程度
                    float urgent = days / toolTotaldays;
                    toolMap.put(tool.getId(), urgent);

                }
                //计算单个演员的剩余连续拍摄时间，并计算紧迫程度
                for (Actor_ actor : actorList) {
                    //该时间段内的某演员连续拍摄天数
                    float days = singleResourceShot(timeQuantumList,actor);
                    //计算演员的紧迫程度
                    float urgent = days / actorTotaldays;
                    actorMap.put(actor.getId(), urgent);
                }
                //计算优先级
                for (Scene_ scene : sceneList) {
                    float scenePrior = scenePrority(shotList, timequantumDangtian, scene, actorMap, toolMap, sceneMap);
                    scenePrioMap.put(scene.getId(), scenePrior);
                }

                //开始选场景,并返回该时间段拍摄了哪些镜头
                List<Shot> dangtianShot = chooseScene(shotList, scenePrioMap, sceneList, actorMap, toolMap,timequantumDangtian);
                if(dangtianShot.size()==0){
                    System.out.println("天数:"+timequantumDangtian.getDay()+"镜头号：无！");
                }
                ShotResult shotResult=new ShotResult();
                shotResult.setList(dangtianShot);
                shotResult.setTimeQuantum(timequantumDangtian);
                shotResultList.add(shotResult);

                dangtianShot.forEach(shot -> System.out.println("天数："+timequantumDangtian.getDay()+"镜头号:"+shot.getId()));

                for (Shot shot : dangtianShot) {
                    shotList.removeIf(shot1 -> shot == shot1);
                }
                if(shotList.size()==0){
                    System.out.println("天数："+timequantumDangtian.getDay()+"镜头拍摄完毕！");
                    break;
                }
            }
            stuList2Excel(shotResultList);
            if(shotList.size()!=0){
                shotList.forEach(shot -> System.out.println("时间用完，还有剩余镜头"+shot.getId()+"没拍，拍摄无法完成！"));
            }
    }
    /**
     * 计算每个资源的连续可拍摄天数
     * @author XiangChao
     * @date 2019/5/19_16:58
    */
    private float singleResourceShot(List<TimeQuantum> timeQuantumList, Resource resource){
            float days=0;
            for (TimeQuantum timeQuantum : timeQuantumList){
                List<Resource> resourceList =new ArrayList<>();
                if(resource instanceof Scene_) {
                    List<Scene_> sceneList = timeQuantum.getSceneList();
                    resourceList.addAll(sceneList);
                }
                if(resource instanceof Actor_){
                    List<Actor_> actorList = timeQuantum.getActorList();
                    resourceList.addAll(actorList);
                }
                if(resource instanceof Tool_){
                    List<Tool_> toolList = timeQuantum.getToolList();
                    resourceList.addAll(toolList);
                }
                //判断每个时间段内的演员数是否遍历完
                int size = 0;
                for (Resource resource1 : resourceList) {
                    if (resource.getId() == resource1.getId()) {
                        days++;
                        break;
                    }
                    size++;
                }
                if (size == resourceList.size()) {
                    break;
                }
            }
            return days;
    }
    /**
     * 选场景,返回当天拍摄的镜头数
     * @author XiangChao
     * @date 2019/5/19_16:39
    */
    private List<Shot> chooseScene(List<Shot> shotList, Map<Integer, Float> scenePrioMap, List<Scene_> sceneList, Map<Integer, Float> actorMap, Map<Integer, Float> toolMap, TimeQuantum timequantumDangtian){
        float max=0;
        Scene_ scene1 = new Scene_();
        for(Scene_ scene :sceneList){
            if(scenePrioMap.get(scene .getId())>max){
                max = scenePrioMap.get(scene .getId());
                scene1 =scene ;
            }
        }
        if(max==0){

            return new ArrayList<>();
        }
        List<Shot> dangtianManzuShotlist =GreedyTools.findStatisfyShotOnTimeQuantum(shotList,timequantumDangtian);
        List<Shot> manzuShotlist =GreedyTools.findShotOnScene(dangtianManzuShotlist ,scene1 );
        //每个镜头的紧迫程度
        Map<Integer,Float> shotMap = new HashMap<>(100);

        //计算每个镜头的紧迫度
        for(Shot shot:manzuShotlist ){
            List<Actor_> actorList =shot.getActorList();
            List<Tool_> toolList =shot.getToolList();
            float totalActorUrgent =0;
            float actorMin =999;
            float toolActorUrgent =0;
            float toolMin =999;
            for(Actor_ actor :actorList ){
                float urgent=actorMap.get(actor .getId());
                totalActorUrgent +=urgent;
                if(actorMin >urgent){
                    actorMin =urgent;
                }
            }
            for(Tool_ tool :toolList ){
                float urgent=toolMap.get(tool .getId());
                toolActorUrgent +=urgent;
                if(toolMin >urgent){
                    toolMin =urgent;
                }
            }
            float shotUrgent = W0/(W6*(totalActorUrgent /actorList .size())+W7*(toolActorUrgent /toolList .size()))+W1/actorMin +W2*toolMin ;
            shotMap.put(shot.getId(),shotUrgent);

        }
        List<Shot> dangtianPaisheShot;
        dangtianPaisheShot = GreedyTools.selectShotOnTime(manzuShotlist ,shotMap);
        return dangtianPaisheShot;
    }
    /**
     * 计算场景优先级
     * @author XiangChao
     * @date 2019/5/19_16:40
    */
    private float scenePrority(List<Shot> shotList, TimeQuantum timequantumDangtian, Scene_ scene, Map<Integer, Float> actorMap, Map<Integer, Float> toolMap, Map<Integer, Float> sceneMap){
        List<Shot> shotList1 =GreedyTools.findStatisfyShotOnTimeQuantum(shotList,timequantumDangtian);
        List<Shot> avaShotList =GreedyTools.findShotOnScene(shotList1,scene);
        if(avaShotList .size()==0){
            return 0;
        }
        List<Actor_> actorList =GreedyTools.findActorList(avaShotList );
        List<Tool_> toolList =GreedyTools.findToolList(avaShotList );
        float actorTotalUrgent =0;
        float actorMin =999;
        float actorAverageUrgent;
        float toolTotalUrgent =0;
        float toolMin =999;
        float toolAverageUrgent;
        for(Actor_ actor :actorList ){
            float urgent=actorMap.get(actor .getId());
            actorTotalUrgent +=urgent;
            if(actorMin >urgent){
                actorMin =urgent;
            }
        }
        actorAverageUrgent =actorTotalUrgent /actorList .size();
        for(Tool_ tool :toolList ){
            float urgent = toolMap.get(tool .getId());
            toolTotalUrgent +=urgent;
            if(toolMin >urgent){
                toolMin =urgent;
            }
        }
        toolAverageUrgent =toolTotalUrgent /toolList .size();

        float scenePrior ;
        scenePrior=W0/(W3*actorAverageUrgent +W4*toolAverageUrgent +W5*sceneMap.get(scene.getId()))+W1/actorMin +W2/toolMin ;
        return scenePrior ;

    }

    /**
     * 计算所有场景的剩余连续拍摄时间
     * @author XiangChao
     * @date 2019/5/19_16:40
    */

    private float totalSceneShotDays(List<TimeQuantum> timeQuantumList, List<Scene_> sceneList){
        float sceneTotaldays =0;
        for(Scene_ scene :sceneList){
            for(TimeQuantum timequantumSceneTotal :timeQuantumList){
                List<Scene_> sceneBianli = timequantumSceneTotal .getSceneList();
                //判断每个时间段内的道具数是否遍历完
                int size = 0;
                for(Scene_ scene1 :sceneBianli){
                    if(scene .getId()==scene1 .getId()){
                        sceneTotaldays ++;
                        break;
                    }
                    size++;
                }
                if(size==sceneBianli.size()){
                    break;
                }
            }
        }
        return sceneTotaldays ;
    }

    /**
     * 计算所有道具的剩余连续拍摄时间
     * @author XiangChao
     * @date 2019/5/19_16:40
    */
    private float totalToolShotDays(List<TimeQuantum> timeQuantumList, List<Tool_> toolList){
        float toolTotaldays =0;
        for(Tool_ tool :toolList){
            for(TimeQuantum timequantumToolTotal :timeQuantumList){
                List<Tool_> toolBianli = timequantumToolTotal .getToolList();
                //判断每个时间段内的道具数是否遍历完
                int size = 0;
                for(Tool_ tool1 :toolBianli){
                    if(tool .getId()==tool1 .getId()){
                        toolTotaldays ++;
                        break;
                    }
                    size++;
                }
                if(size==toolBianli.size()){
                    break;
                }
            }
        }
        return toolTotaldays ;
    }

   /**
    * 计算所有演员的剩余连续拍摄时间
    * @author XiangChao
    * @date 2019/5/19_16:40
   */
    private float totalActorShotDays(List<TimeQuantum> timeQuantumList, List<Actor_> actorList){
        float actorTotaldays =0;
        //计算该时间段所有演员的剩余连续拍摄时间
        for(Actor_ actor :actorList){
            for(TimeQuantum timequantumActorTotal :timeQuantumList){
                List<Actor_> actorBianli = timequantumActorTotal .getActorList();
                //判断每个时间段内的演员数是否遍历完
                int size = 0;
                for(Actor_ actor1 :actorBianli){
                    if(actor .getId()==actor1 .getId()){
                        actorTotaldays ++;
                        break;
                    }
                    size++;
                }
                if(size==actorBianli.size()){
                    break;
                }
            }
        }
        return actorTotaldays ;
    }
    /**
     * 将数据存入excel中
     * @author XiangChao
     * @date 2019/5/19_19:46
    */
    private static void stuList2Excel(List<ShotResult> shotResultList) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd hhmmss");
        Workbook wb = new XSSFWorkbook();
        //标题行抽出字段
        String[] title = {"天数","拍摄镜头数的编号"};
        //设置sheet名称，并创建新的sheet对象
        String sheetName = "每天拍摄的镜头";
        Sheet shotSheet = wb.createSheet(sheetName);
        //获取表头行
        Row titleRow = shotSheet.createRow(0);
        //创建单元格，设置style居中，字体，单元格大小等
        CellStyle style = wb.createCellStyle();
        Cell cell;
        //把已经写好的标题行写入excel文件中
        for (int i = 0; i < title.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //把从数据库中取得的数据一一写入excel文件中
         Row row;
        int j=1;
        for (int i = 0; i < shotResultList.size(); i++) {
            //创建list.size()行数据
            row = shotSheet.createRow(j++);
            //把值一一写进单元格里
            //设置第一列为自动递增的序号
            for(Shot shot:shotResultList.get(i).getList()){
                row.createCell(0).setCellValue(shotResultList.get(i).getTimeQuantum().getDay());
                row.createCell(1).setCellValue(shot.getId());
                row = shotSheet.createRow(j++);
            }


        }
        //设置单元格宽度自适应，在此基础上把宽度调至1.5倍
        for (int i = 0; i < title.length; i++) {
            shotSheet.autoSizeColumn(i, true);
            shotSheet.setColumnWidth(i, shotSheet.getColumnWidth(i) * 15 / 10);
        }
        //获取配置文件中保存对应excel文件的路径，本地也可以直接写成F：excel/stuInfoExcel路径
        String folderPath = "C:\\result\\shotExcel";
        //创建上传文件目录
        File folder = new File(folderPath);
        //如果文件夹不存在创建对应的文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        String fileName = sdf1.format(new Date()) + sheetName + ".xlsx";
        String savePath = folderPath + File.separator + fileName;
        OutputStream fileOut = new FileOutputStream(savePath);
        wb.write(fileOut);
        fileOut.close();
        //返回文件保存全路径
    }

    /**
     * 将资源存入excel中
     * @author XiangChao
     * @date 2019/5/19_21:27
    */
    private static void resourceExcel(List<Shot> shotList, List<TimeQuantum> timeQuantumList) throws Exception{
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd hhmmss");
        Workbook wb = new XSSFWorkbook();
        //标题行抽出字段
        String[] title = {"天数","场景","演员","道具"};
        //设置sheet名称，并创建新的sheet对象
        String sheetName = "档期资源表";
        Sheet shotSheet = wb.createSheet(sheetName);
        //获取表头行
        Row titleRow = shotSheet.createRow(0);
        //创建单元格，设置style居中，字体，单元格大小等
        CellStyle style = wb.createCellStyle();
        Cell cell;
        //把已经写好的标题行写入excel文件中
        for (int i = 0; i < title.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //把从数据库中取得的数据一一写入excel文件中
        Row row;

        for (int i = 0; i < timeQuantumList.size(); i++) {
            //创建list.size()行数据
            row = shotSheet.createRow(i+1);
            //把值一一写进单元格里
            //设置第一列为自动递增的序号
            row.createCell(0).setCellValue(timeQuantumList.get(i).getDay());
            List<Integer> list = new ArrayList<>();
            for(Scene_ scene:timeQuantumList.get(i).getSceneList()){
                list.add(scene.getId());
            }
            row.createCell(1).setCellValue(list.toString());
            List<Integer> list1 = new ArrayList<>();
            for(Actor_ actor:timeQuantumList.get(i).getActorList()){
                list1.add(actor.getId());
            }
            row.createCell(2).setCellValue(list1.toString());
            List<Integer> list2 = new ArrayList<>();
            for(Tool_ tool:timeQuantumList.get(i).getToolList()){
                list2.add(tool.getId());
            }
            row.createCell(3).setCellValue(list2.toString());


        }
        //设置单元格宽度自适应，在此基础上把宽度调至1.5倍
//        for (int i = 0; i < title.length; i++) {
//            shotSheet.autoSizeColumn(i, true);
//            shotSheet.setColumnWidth(i, shotSheet.getColumnWidth(i) * 50 / 10);
//        }
        //获取配置文件中保存对应excel文件的路径，本地也可以直接写成F：excel/stuInfoExcel路径
        String folderPath = "C:\\result\\resourceExcel";
        //创建上传文件目录
        File folder = new File(folderPath);
        //如果文件夹不存在创建对应的文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        String fileName = sdf1.format(new Date()) + sheetName + ".xlsx";
        String savePath = folderPath + File.separator + fileName;
        OutputStream fileOut = new FileOutputStream(savePath);
        wb.write(fileOut);
        fileOut.close();
        //返回文件保存全路径
    }
}

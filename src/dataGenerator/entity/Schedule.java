package dataGenerator.entity;

/**
 * @author galileo
 * @date 2019/5/18 17:20
 */
public class Schedule {
    private int startTime;
    private int endTime;

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public boolean containDay(int day){
        boolean flag = false;
        if (day >= startTime && day <= endTime){
            flag = true;
        }
        return flag;
    }
}

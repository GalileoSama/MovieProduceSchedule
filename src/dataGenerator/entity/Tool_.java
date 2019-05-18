package dataGenerator.entity;

import java.util.List;

/**
 * @author galileo
 * @date 2019/5/18 15:27
 */
public class Tool_ {
    private int id;
    private List<Schedule> scheduleList;

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

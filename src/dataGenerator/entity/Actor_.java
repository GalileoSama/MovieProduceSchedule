package dataGenerator.entity;

import java.util.List;

/**
 * @author galileo
 * @date 2019/5/18 15:26
 */
public class Actor_ {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Actor_) {
            Actor_ obj1= (Actor_) obj;
            return this.id == obj1.getId();
        }
        return false;
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        return result;
    }
}

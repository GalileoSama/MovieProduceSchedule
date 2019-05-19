package greedyalgorithm.entity;

import java.util.List;

/**
 * @author XiangChao
 * @date 2019/5/1920:56
 * $params$
 * $return$
 */
public class ShotResult {
    private TimeQuantum timeQuantum;
    private List<Shot> list;

    public TimeQuantum getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(TimeQuantum timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public List<Shot> getList() {
        return list;
    }

    public void setList(List<Shot> list) {
        this.list = list;
    }
}

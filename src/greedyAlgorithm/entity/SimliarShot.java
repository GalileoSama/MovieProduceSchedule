package greedyAlgorithm.entity;

/**保存两个镜头之间的相似度*/
public class SimliarShot {
    private double simlar;
    private Shot shotSrc;
    private Shot shotDest;

    public double getSimlar() {
        return simlar;
    }

    public void setSimlar(double simlar) {
        this.simlar = simlar;
    }

    public Shot getShotSrc() {
        return shotSrc;
    }

    public void setShotSrc(Shot shotSrc) {
        this.shotSrc = shotSrc;
    }

    public Shot getShotDest() {
        return shotDest;
    }

    public void setShotDest(Shot shotDest) {
        this.shotDest = shotDest;
    }
}

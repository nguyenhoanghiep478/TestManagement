package Entity;

public class TopicEntity {
    private int tpID;
    private String tpTitle;
    private Integer tpParent;
    private int tpStatus;

    public TopicEntity() {
    }

    public TopicEntity(int tpID, String tpTitle, Integer tpParent, int tpStatus) {
        this.tpID = tpID;
        this.tpTitle = tpTitle;
        this.tpParent = tpParent;
        this.tpStatus = tpStatus;
    }

    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID;
    }

    public String getTpTitle() {
        return tpTitle;
    }

    public void setTpTitle(String tpTitle) {
        this.tpTitle = tpTitle;
    }

    public Integer getTpParent() {
        return tpParent;
    }

    public void setTpParent(Integer tpParent) {
        this.tpParent = tpParent;
    }

    public int getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(int tpStatus) {
        this.tpStatus = tpStatus;
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "tpID=" + tpID +
                ", tpTitle='" + tpTitle + '\'' +
                ", tpParent=" + tpParent +
                ", tpStatus=" + tpStatus +
                '}';
    }
}

package Entity;

import java.sql.Date;

public class QuestionEntity {
    private int qID;
    private String qContent;
    private String qPictures;
    private int qTopicID;
    private String qLevel;
    private int qStatus;

    public QuestionEntity(int qID, String qContent, String qPictures, int qTopicID, String qLevel, int qStatus) {
        this.qID = qID;
        this.qContent = qContent;
        this.qPictures = qPictures;
        this.qTopicID = qTopicID;
        this.qLevel = qLevel;
        this.qStatus = qStatus;
    }

    public QuestionEntity() {
    }

    public int getqID() {
        return qID;
    }

    public void setqID(int qID) {
        this.qID = qID;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getqPictures() {
        return qPictures;
    }

    public void setqPictures(String qPictures) {
        this.qPictures = qPictures;
    }

    public int getqTopicID() {
        return qTopicID;
    }

    public void setqTopicID(int qTopicID) {
        this.qTopicID = qTopicID;
    }

    public String getqLevel() {
        return qLevel;
    }

    public void setqLevel(String qLevel) {
        this.qLevel = qLevel;
    }

    public int getqStatus() {
        return qStatus;
    }

    public void setqStatus(int qStatus) {
        this.qStatus = qStatus;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "qID=" + qID +
                ", qContent='" + qContent + '\'' +
                ", qPictures='" + qPictures + '\'' +
                ", qTopicID=" + qTopicID +
                ", qLevel='" + qLevel + '\'' +
                ", qStatus=" + qStatus +
                '}';
    }
}

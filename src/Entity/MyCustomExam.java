package Entity;

import java.sql.Date;

public class MyCustomExam {
    private String exCode;
    private String testTitle;
    private int testTime;
    private Date testDate;
    private String ex_QuestIDs;

    public MyCustomExam(String exCode, String testTitle, int testTime, Date testDate, String ex_QuestIDs) {
        this.exCode = exCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
        this.testDate = testDate;
        this.ex_QuestIDs = ex_QuestIDs;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getEx_QuestIDs() {
        return ex_QuestIDs;
    }

    public void setEx_QuestIDs(String ex_QuestIDs) {
        this.ex_QuestIDs = ex_QuestIDs;
    }
}

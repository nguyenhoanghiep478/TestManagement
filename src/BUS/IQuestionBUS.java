package BUS;

import Entity.QuestionEntity;
import Entity.UserEntity;

import java.util.List;

public interface IQuestionBUS{
    QuestionEntity findQuestionById(int questID);
    List<QuestionEntity> getAllQuestion();
    long createQuestion(QuestionEntity question);
    boolean updateQuestion(QuestionEntity question);
    boolean deleteQuestion(int questionId);
    List<QuestionEntity>searchByField(String field,String content);
    int countAnswers(int id);
    void addQuestionFromExcel(String filePath);
}

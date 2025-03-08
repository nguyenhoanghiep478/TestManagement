package BUS;

import DAO.impl.QuestionDAO;
import Entity.QuestionEntity;

import java.util.List;

public class QuestionBUS implements IQuestionBUS{
    private QuestionDAO questionDAO=new QuestionDAO();

    @Override
    public QuestionEntity findQuestionById(int questID) {
       return  questionDAO.findBy(questID);
    }

    @Override
    public List<QuestionEntity> getAllQuestion() {
        return questionDAO.findAll();
    }

    @Override
    public long createQuestion(QuestionEntity question) {
        return questionDAO.insert(question);
    }

    @Override
    public boolean updateQuestion(QuestionEntity question) {
         questionDAO.update(question);
         return true;
    }

    @Override
    public boolean deleteQuestion(int questionId) {
          questionDAO.delete(questionId);
          return true;
    }
}

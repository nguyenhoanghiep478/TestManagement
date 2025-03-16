package BUS;

import DAO.impl.QuestionDAO;
import Entity.AnswerEntity;
import Entity.QuestionEntity;
import Entity.TopicEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionBUS implements IQuestionBUS{
    private QuestionDAO questionDAO=new QuestionDAO();
    private TopicBUS topicBUS=new TopicBUS();
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
    public List<QuestionEntity>searchByField(String field,String content){
        List<QuestionEntity>l=questionDAO.findAll();
        if(field.equals("Id")){
            return l.stream().filter(question -> String.valueOf(question.getqID()).equals(content)).toList();
        } else if (field.equals("Content")) {
          return  l.stream().filter(question -> question.getqContent().contains(content)).toList();
        }else{
            TopicEntity topicEntity=topicBUS.findTopicByTitle(content);
          if(topicEntity==null) {
              return new ArrayList<>();
          }
            return l.stream().filter(question ->question.getqTopicID()==topicEntity.getTpID()).toList();
        }
    }
    public List<QuestionEntity>findAllInId(String listId){
        return questionDAO.findAllInId(listId);
    }
}

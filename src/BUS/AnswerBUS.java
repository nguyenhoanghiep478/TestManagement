package BUS;

import DAO.impl.AnswerDAO;
import Entity.AnswerEntity;

import java.util.ArrayList;
import java.util.List;

public class AnswerBUS implements IAnswerBUS {
    private AnswerDAO answerDAO=new AnswerDAO();


    @Override
    public AnswerEntity findAnswerById(int answerId) {
        return answerDAO.findBy(answerId);
    }

    @Override
    public List<AnswerEntity> getAllAnswer() {
        return answerDAO.findAll();
    }

    @Override
    public boolean createAnswer(AnswerEntity answer) {
         answerDAO.insert(answer);
         return true;
    }

    @Override
    public boolean updateAnswer(AnswerEntity answer) {
        answerDAO.update(answer);
        return true;
    }

    @Override
    public boolean removeAnswer(int id) {
         answerDAO.delete(id);
         return true;
    }


}

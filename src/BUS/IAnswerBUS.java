package BUS;

import Entity.AnswerEntity;
import Entity.QuestionEntity;

import java.util.List;

public interface IAnswerBUS {
    AnswerEntity findAnswerById(int answerId);
    List<AnswerEntity> getAllAnswer();
    boolean createAnswer(AnswerEntity answer);
}

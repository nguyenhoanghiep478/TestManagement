package BUS;

import DAO.impl.QuestionDAO;
import Entity.AnswerEntity;
import Entity.QuestionEntity;
import Entity.TopicEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionBUS implements IQuestionBUS{
    private QuestionDAO questionDAO=new QuestionDAO();
    private TopicBUS topicBUS=new TopicBUS();
    private AnswerBUS answerBUS=new AnswerBUS();

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

    public void addQuestionFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Bỏ qua hàng tiêu đề

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String questionContent = row.getCell(0) != null ? row.getCell(0).getStringCellValue().trim() : "";
                String questionImageName = row.getCell(1) != null ? row.getCell(1).getStringCellValue().trim() : "";

                int topicID = (int) row.getCell(2).getNumericCellValue();
                String level = row.getCell(3) != null ? row.getCell(3).getStringCellValue().trim() : "";
                int status = 1;

                if (questionContent.isEmpty() && questionImageName.isEmpty()) {
                    continue;
                }

                long questionID = createQuestion(new QuestionEntity(0, questionContent, questionImageName, topicID, level, status));

                for (int i = 4; i < row.getLastCellNum(); i += 3) {
                    String answerContent = row.getCell(i) != null ? row.getCell(i).getStringCellValue().trim() : "";
                    String answerImageName = row.getCell(i + 1) != null ? row.getCell(i + 1).getStringCellValue().trim() : "";
                    int isRight = (int) row.getCell(i + 2).getNumericCellValue();

                    if (answerContent.isEmpty() && answerImageName.isEmpty()) {
                        continue;
                    }
                    answerBUS.createAnswer(new AnswerEntity(0, questionID, answerContent, answerImageName, isRight, 1));

                }
            }
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu từ Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int countAnswers(int questionID) {
        String query = "SELECT COUNT(*) FROM answers WHERE qID = ?";
        return questionDAO.count(query, questionID);
    }

}

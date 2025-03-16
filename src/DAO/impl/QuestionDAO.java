package DAO.impl;

import DAO.IQuestDAO;
import Entity.Criteria;
import Entity.QuestionEntity;
import Utils.Mapper.impl.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@RequiredArgsConstructor
public class QuestionDAO extends AbstractDAO<QuestionEntity> implements IQuestDAO {
    private  QuestionMapper rowMapper=new QuestionMapper();
    @Override
    public void delete(int questID) {
        String deleteAnswersQuery = "DELETE FROM answers WHERE qID = ?";
        String deleteQuestionQuery = "DELETE FROM questions WHERE qID = ?";

        update(deleteAnswersQuery, questID);
        update(deleteQuestionQuery, questID);
    }


    @Override
    public QuestionEntity findBy(int rsNum) {
        List<Criteria> criterias = List.of(new Criteria("qID", "=", rsNum));
        return searchBy(criterias, rowMapper, "questions").stream().findFirst().orElse(null);
    }
    @Override
    public List<QuestionEntity> findAll() {
        return searchBy(Collections.emptyList(),rowMapper, "questions");
    }
    public List<QuestionEntity>findAllInId(String listId){
        List<QuestionEntity>l=new ArrayList<>();
        List<String> idList = Arrays.asList(listId.split(","));
        String placeholders = String.join(",", idList.stream().map(id -> "?").toArray(String[]::new));

        String sql = "SELECT * FROM questions WHERE qID IN (" + placeholders + ")";
        try{
            Connection con=getConnection();
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            for (int i = 0; i < idList.size(); i++) {
                preparedStatement.setInt(i + 1, Integer.parseInt(idList.get(i).trim()));
            }
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                l.add(rowMapper.mapRow(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return l;
    }
    @Override
    public long insert(QuestionEntity result) {
        String query = """
            INSERT INTO questions
            (qContent, qPictures, qTopicID, qLevel, qStatus)
            VALUES
            (?, ?, ?, ?, ?);
        """;

        return save(query,
                result.getqContent(),
                result.getqPictures(),
                result.getqTopicID(),
                result.getqLevel(),
                result.getqStatus()
        );
    }

    private String saveImage(File imageFile) {
        try {
            File destFolder = new File("src/IMAGE/");
            if (!destFolder.exists()) {
                destFolder.mkdirs(); // Tạo thư mục nếu chưa có
            }
            File destFile = new File(destFolder, imageFile.getName());
            Files.copy(imageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return "src/IMAGE/" + imageFile.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void addQuestionFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Bỏ qua hàng tiêu đề

            String insertQuestionQuery = "INSERT INTO questions (qContent, qPictures, qTopicID, qLevel, qStatus) VALUES (?, ?, ?, ?, ?)";
            String insertAnswerQuery = "INSERT INTO answers (qID, awContent, awPictures, isRight) VALUES (?, ?, ?, ?)";

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String questionContent = row.getCell(0) != null ? row.getCell(0).getStringCellValue().trim() : "";
                String questionImageName = row.getCell(1) != null ? row.getCell(1).getStringCellValue().trim() : ""; // Chỉ lấy tên file

                int topicID = (int) row.getCell(2).getNumericCellValue();
                String level = row.getCell(3) != null ? row.getCell(3).getStringCellValue().trim() : "";
                int status = 1;

                if (questionContent.isEmpty() && questionImageName.isEmpty()) {
                    continue;
                }

                long questionID = save(insertQuestionQuery, questionContent, questionImageName, topicID, level, status);

                for (int i = 4; i < row.getLastCellNum(); i += 3) {
                    String answerContent = row.getCell(i) != null ? row.getCell(i).getStringCellValue().trim() : "";
                    String answerImageName = row.getCell(i + 1) != null ? row.getCell(i + 1).getStringCellValue().trim() : ""; // Chỉ lấy tên file

                    int isRight = (int) row.getCell(i + 2).getNumericCellValue();

                    if (answerContent.isEmpty() && answerImageName.isEmpty()) {
                        continue;
                    }

                    update(insertAnswerQuery, questionID, answerContent, answerImageName, isRight);
                }
            }
            JOptionPane.showMessageDialog(null, "Nhập dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu từ Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }




    @Override
    public void update(QuestionEntity result) {
        String query = """
            UPDATE questions
            SET
                qContent = ?,
                qPictures = ?, 
                qTopicID = ?, 
                qLevel = ?, 
                qStatus = ?
            WHERE qID = ?;
        """;

        update(query,
                result.getqContent(),
                result.getqPictures(),
                result.getqTopicID(),
                result.getqLevel(),
                result.getqStatus(),
                result.getqID()
        );
    }

    public int countAnswers(int questionID) {
        String query = "SELECT COUNT(*) FROM answers WHERE qID = ?";
        return count(query, questionID);
    }

}

package Service.impl;

import BUS.AnswerBUS;
import Entity.AnswerEntity;
import Entity.MyCustomExam;
import Entity.QuestionEntity;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WordExportService {
    private AnswerBUS answerBUS = new AnswerBUS();

    public void exportExamToWord(MyCustomExam exam, List<QuestionEntity> questions, String filePath) throws IOException {
        try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(filePath)) {
            // Tiêu đề bài thi
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText(exam.getTestTitle());
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            // Thông tin bài thi
            XWPFParagraph info = document.createParagraph();
            XWPFRun run = info.createRun();
            run.setText("Môn thi: " + exam.getTestTitle() + "  |  Mã đề: " + exam.getExCode());
            run.addBreak();
            run.setText("Ngày thi: " + exam.getTestDate() + "  |  Thời gian: " + exam.getTestTime() + " phút");

            int count = 1;
            for (QuestionEntity q : questions) {
                // In câu hỏi
                XWPFParagraph questionPara = document.createParagraph();
                XWPFRun questionRun = questionPara.createRun();
                questionRun.setBold(true);
                questionRun.setFontSize(12);
                questionRun.setText(count++ + ". " + q.getqContent());

                // Thêm hình ảnh câu hỏi nếu có, căn chỉnh hợp lý
                if (q.getqPictures() != null && Files.exists(Paths.get(q.getqPictures()))) {
                    questionRun.addBreak(); // Xuống dòng trước khi thêm ảnh
                    addInlineImageToDocument(questionRun, q.getqPictures(), 150, 100);
                }

                // In danh sách câu trả lời
                List<AnswerEntity> answers = answerBUS.findAnswerByQuestId(q.getqID());
                char[] answerLabels = {'A', 'B', 'C', 'D', 'E'};

                for (int i = 0; i < answers.size(); i++) {
                    AnswerEntity answer = answers.get(i);
                    XWPFParagraph answerPara = document.createParagraph();
                    answerPara.setIndentationLeft(400);
                    XWPFRun answerRun = answerPara.createRun();
                    answerRun.setText(answerLabels[i] + ". " + answer.getAwContent());

                    // Thêm hình ảnh câu trả lời nếu có, căn chỉnh hợp lý
                    if (answer.getAwPictures() != null && Files.exists(Paths.get(answer.getAwPictures()))) {
                        answerRun.addBreak(); // Xuống dòng trước khi thêm ảnh
                        addInlineImageToDocument(answerRun, answer.getAwPictures(), 150, 100);
                    }
                }
            }
            document.write(out);
        }
    }

    private void addInlineImageToDocument(XWPFRun run, String imagePath, int width, int height) {
        try {
            InputStream imageStream = new FileInputStream(imagePath);
            run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, imagePath, Units.toEMU(width), Units.toEMU(height));
            imageStream.close();
        } catch (Exception e) {
            System.out.println("Không thể thêm ảnh: " + imagePath);
        }
    }
}

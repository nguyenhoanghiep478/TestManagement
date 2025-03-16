package Service.impl;

import BUS.AnswerBUS;
import Entity.AnswerEntity;
import Entity.MyCustomExam;
import Entity.QuestionEntity;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordExportService {
    private AnswerBUS answerBUS=new AnswerBUS();
    public  void exportExamToWord(MyCustomExam exam, List<QuestionEntity> questions, String filePath) throws IOException {
        try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(filePath)) {
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText(exam.getTestTitle());
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFParagraph info = document.createParagraph();
            XWPFRun run = info.createRun();
            run.setText("Môn thi: " + exam.getTestTitle() + "  |  Mã đề: " + exam.getExCode());
            run.addBreak();
            run.setText("Ngày thi: " + exam.getTestDate() + "  |  Thời gian: " + exam.getTestTime() + " phút");

            int count = 1;
            for (QuestionEntity q : questions) {
                // In câu hỏi
                XWPFParagraph questionPara = document.createParagraph();
                questionPara.createRun().setText(count++ + ". " + q.getqContent());

                // In danh sách câu trả lời theo thứ tự A, B, C, D
                List<AnswerEntity> answers = answerBUS.findAnswerByQuestId(q.getqID());
                char[] answerLabels = {'A', 'B', 'C', 'D','E'}; // Danh sách nhãn câu trả lời
                for (int i = 0; i < answers.size(); i++) {
                    XWPFParagraph answerPara = document.createParagraph();
                    answerPara.createRun().setText("    " + answerLabels[i] + ". " + answers.get(i).getAwContent());
                }
            }
            document.write(out);
        }
    }
}

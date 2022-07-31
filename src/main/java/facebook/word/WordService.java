package facebook.word;

import facebook.account.Account;
import facebook.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class WordService {

    public void createSingleWord(Transfer transfer, Account account){
        try {
            log.info("Start creating single word document for 1 transfer.");
            XWPFDocument document = new XWPFDocument();
            makeHeader(document, "Transfer confirmation");
            makeText(document, "Sender: " + account.getFirstName() + " " + account.getLastName());
            String amountCurrency = "Amount: " + transfer.getAmount() + " " + transfer.getCurrency();
            makeText(document, amountCurrency);
            log.info("Processing single word document for 1 transfer.");
            LocalDate localDate = transfer.getLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = "Executed: " + localDate.format(formatter);
            makeText(document, formattedDate);
            timeStamp(document);
            saveWord(document, account.getLastName());
            log.info("End creating single word document for 1 transfer.");
        }catch(Exception e){
            System.out.println("Error occurred while executing: createSingleWord.");
        }
    }

    public void createWordByUser(Account account){
        log.info("Start creating word document for 1 user.");
        XWPFDocument document = new XWPFDocument();
        makeHeader(document,"History of all transfers of user: " +account.getFirstName()+" "+account.getLastName());
        List<Transfer> transferList = account.getTransferList();
        for (Transfer transfer : transferList) {
            createTextWord(document,transfer);
        }
        try {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String stringDate = localDate.format(formatterLocalDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
            String stringTime = localTime.format(formatterLocalTime);
            File file = new File("D:/AllTransfers_" + account.getLastName() + "_" + stringDate + "_" + stringTime + ".docx");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            document.write(out);
            out.close();
            log.info("End creating word document for 1 user.");
        } catch (Exception e){
            System.out.println("\"Error occurred while executing: createWordByUser");
        }
    }

    private void createTextWord(XWPFDocument document,Transfer transfer){
            makeText(document, "Transfer ID: " + transfer.getId());
            String amountCurrency = "Amount: " + transfer.getAmount() + " " + transfer.getCurrency();
            makeText(document, amountCurrency);
            LocalDate localDate = transfer.getLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = "Executed: " + localDate.format(formatter);
            makeText(document, formattedDate);
    }

    private void makeHeader(XWPFDocument document, String header){
        XWPFParagraph paragraphTitle = document.createParagraph();
        paragraphTitle.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTitleRun = paragraphTitle.createRun();
        paragraphTitleRun.setText(header);
        paragraphTitleRun.setBold(true);
        paragraphTitleRun.setFontFamily("Arial");
        paragraphTitleRun.setFontSize(16);
    }

    private void makeText(XWPFDocument document, String text) {
        XWPFParagraph paragraphText = document.createParagraph();
        paragraphText.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTextRun = paragraphText.createRun();
        paragraphTextRun.setText(text+ "\r\n"+System.lineSeparator()+"\r\n");
    }

    private void timeStamp(XWPFDocument document){
        XWPFParagraph paragraphText = document.createParagraph();
        paragraphText.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTextRun = paragraphText.createRun();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
        String stringTime = localTime.format(formatterLocalTime);
        paragraphTextRun.setText("Time: "+ stringTime);
    }

    private void saveWord(XWPFDocument document, String fileName) throws IOException {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringDate =localDate.format(formatterLocalDate);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
        String stringTime = localTime.format(formatterLocalTime);
        File file = new File("D:/Transfer_"+fileName+"_"+stringDate +"_"+stringTime+".docx");
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
    }
}

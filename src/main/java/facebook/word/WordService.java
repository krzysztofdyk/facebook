package facebook.word;

import facebook.account.Account;
import facebook.account.AccountRepository;
import facebook.image.Image;
import facebook.image.ImageRepository;
import facebook.transfer.Transfer;
import facebook.transfer.TransferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class WordService {

    AccountRepository accountRepository;
    TransferRepository transferRepository;
    ImageRepository imageRepository;

    public void createTransferConfirmationReport(Long transferId){
        Transfer transfer = transferRepository.getById(transferId);
        Account fromAccount = transfer.getFromAccount();
        Account toAccount = transfer.getToAccount();
        try {
            log.info("Start creating single word document for 1 transfer.");
            XWPFDocument document = new XWPFDocument();
            String header = "Transfer confirmation for transfer ID: " + transfer.getId();
            String amountCurrency = "Amount: " + transfer.getAmount() + " " + transfer.getCurrency();
            String sender = "Sender: " + fromAccount.getFirstName() + " " + fromAccount.getLastName();
            String receiver = "Receiver: " + toAccount.getFirstName() + " " + toAccount.getLastName();
            makeHeader(document, header);
            makeText(document,"");
            makeText(document,sender );
            makeText(document, receiver);
            makeText(document, amountCurrency);
            dateStamp(document,transfer);
            timeStamp(document,transfer);
            saveWord(document,"Transfer_confirmation_", fromAccount.getLastName());
            log.info("End creating single word document for 1 transfer.");
        }catch(Exception e){
            System.out.println("Error occurred while executing: createSingleWord.");
        }
    }

    public void createAllTransfersReport(Long accountId){
        Account account = accountRepository.getById(accountId);
        log.info("Start creating word document for 1 user.");
        XWPFDocument document = new XWPFDocument();
        String header = "History of all transfers of user: " +account.getFirstName()+" "+account.getLastName();
        makeHeader(document,header);
        List<Transfer> transferList = account.getTransferList();
        for (Transfer transfer : transferList) {
            createOneTransferBody(document,transfer);
        }
        try {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String stringDate = localDate.format(formatterLocalDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
            String stringTime = localTime.format(formatterLocalTime);
            File file = new File("D:/Transfers_" + account.getLastName() + "_" + stringDate + "_" + stringTime + ".docx");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            document.write(out);
            out.close();
            log.info("End creating word document for 1 user.");
        } catch (Exception e){
            System.out.println("\"Error occurred while executing: createWordByUser");
        }
    }

    private void createOneTransferBody(XWPFDocument document,Transfer transfer){
            String transferId =  "Transfer ID: " + transfer.getId();
            String amountCurrency = "Amount: " + transfer.getAmount() + " " + transfer.getCurrency();
            makeText(document, transferId);
            makeText(document, amountCurrency);
            timeStamp(document,transfer);
            dateStamp(document,transfer);
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setBorderBottom(Borders.SINGLE);
    }

    private void makeHeader(XWPFDocument document, String header){
        XWPFParagraph paragraphTitle = document.createParagraph();
        paragraphTitle.setAlignment(ParagraphAlignment.LEFT);
        paragraphTitle.setBorderBottom(Borders.CELTIC_KNOTWORK);
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

    private void dateStamp(XWPFDocument document , Transfer transfer){
        XWPFParagraph paragraphText = document.createParagraph();
        paragraphText.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTextRun = paragraphText.createRun();
        LocalDate localDate = transfer.getLocalDate();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String stringDate =localDate.format(formatterDate);
        paragraphTextRun.setText("Date: "+ stringDate);
    }

    private void timeStamp(XWPFDocument document, Transfer transfer){
        XWPFParagraph paragraphText = document.createParagraph();
        paragraphText.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTextRun = paragraphText.createRun();
        LocalTime localTime = transfer.getLocalTime();
        DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
        String stringTime = localTime.format(formatterLocalTime);
        paragraphTextRun.setText("Time: "+ stringTime);
    }

    private void saveWord(XWPFDocument document,String typeName, String fileName) throws IOException {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringDate =localDate.format(formatterLocalDate);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
        String stringTime = localTime.format(formatterLocalTime);
        File file = new File("D:/"+typeName+fileName+"_"+stringDate +"_"+stringTime+".docx");
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
    }

    public void createProfileByAccountId(Long accountId) {
        try {
        Account account = accountRepository.getById(accountId);
        XWPFDocument document = new XWPFDocument();
        String header = "Profile of: " + account.getFirstName() + " " + account.getLastName();
        makeHeader(document, header);
        makeText(document,"");
        String email = "Email: " + account.getEmail();
        makeText(document,email);
        attachPhoto(document,account);
        saveWord(document,"Profile_", account.getLastName());
        } catch(Exception e){
            System.out.println("Some problem occurred while saving the profile as word file.");
        }
    }

    private void attachPhoto(XWPFDocument document,Account account) {
        try {
        Image image = imageRepository.getById(account.getImage().getId());
        XWPFParagraph paragraphText = document.createParagraph();
        XWPFRun paragraphTextRun = paragraphText.createRun();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getImageByte());
        paragraphTextRun.addPicture(byteArrayInputStream,XWPFDocument.PICTURE_TYPE_JPEG,image.getImageName(), 0,0);
        }
        catch(Exception e){
            System.out.println("some problems wit attaching photo");
        }
    }
}

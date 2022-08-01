package facebook.excel;

import facebook.account.Account;
import facebook.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@AllArgsConstructor
@Service
@Slf4j
public class ExcelService {

    AccountRepository accountRepository;

    public void downloadAccountsExcel(){
        log.info("Downloading the excel with accounts: started.");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet accountsSheet = workbook.createSheet("Accounts");
        try {
            buildHeader(accountsSheet);
        } catch(Exception e){
            System.out.println("Error occurred while executing: downloadAccountsExcel(): build header().");
        }
        try {
            buildBody(accountsSheet);
        } catch(Exception e){
            System.out.println("Error occurred while executing: downloadAccountsExcel(): buildBody().");
        }
        try {
            saveFile(accountsSheet.getWorkbook());
        } catch(Exception e){
            System.out.println("Error occurred while executing: downloadAccountsExcel(): saveFile().");
        }
        log.info("Downloading the excel with accounts: ended.");
    }

    private void buildHeader(XSSFSheet accountsSheet) {
        XSSFRow row = accountsSheet.createRow(1);
        accountsSheet.setDefaultColumnWidth(15);
        buildCells(row,1,blackCellStyle(accountsSheet.getWorkbook()),"LP" );
        buildCells(row,2,blackCellStyle(accountsSheet.getWorkbook()),"FIRST NAME" );
        buildCells(row,3,blackCellStyle(accountsSheet.getWorkbook()),"LAST NAME" );
        buildCells(row,4,blackCellStyle(accountsSheet.getWorkbook()),"EMAIL" );
        buildCells(row,5,blackCellStyle(accountsSheet.getWorkbook()),"CITY" );
        buildCells(row,6,blackCellStyle(accountsSheet.getWorkbook()),"BALANCE" );
    }

    private void buildCells(XSSFRow row, int columnIndex,  XSSFCellStyle cellStyle, String columnValue ) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(columnValue);
    }

    private XSSFCellStyle blackCellStyle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private Object[] mapAccountToObjectArr(Account account){
        return new Object[] {   account.getId(),
                                account.getFirstName(),
                                account.getLastName(),
                                account.getEmail(),
                                account.getCity(),
                                account.getBalance()
        };
    }

    private void buildBody(XSSFSheet sheet){
        List<Account> accountList = accountRepository.findAll();
        Map<Long, Object[]> accountMap = new TreeMap<>();
        for (Account account : accountList) {
            accountMap.put(account.getId(), mapAccountToObjectArr(account));
            }
        Set<Long> accountIdSet = accountMap.keySet();
        int rowNumber = 2;

        for (Long id : accountIdSet) {
            XSSFRow row = sheet.createRow(rowNumber++);
            Object[] objectArr = accountMap.get(id);
            int cellNumber = 1;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellNumber++);
                cell.setCellValue(String.valueOf(obj));
            }
        }
    }

    private void saveFile(XSSFWorkbook workbook){
        try{
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String stringDate = localDate.format(formatterLocalDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
            String stringTime = localTime.format(formatterLocalTime);
            FileOutputStream out = new FileOutputStream( new File("D:/Accounts_"+stringDate+"_"+stringTime+".xlsx"));
            workbook.write(out);
            out.close();}
        catch (IOException e){
            System.out.println("Error occurred while executing: saveFile.");
        }
    }

}

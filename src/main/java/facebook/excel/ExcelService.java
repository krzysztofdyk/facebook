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
    }

    private void buildHeader(XSSFSheet accountsSheet) {
        XSSFRow row = accountsSheet.createRow(0);
        //accountsSheet.setDefaultColumnWidth(5000);
        buildCells(row,1,blackCellStyle(accountsSheet.getWorkbook()),"LP" );
        buildCells(row,2,blackCellStyle(accountsSheet.getWorkbook()),"FIRST NAME" );
        buildCells(row,3,blackCellStyle(accountsSheet.getWorkbook()),"LAST NAME" );
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
        return new Object[] {account.getId(),account.getFirstName(),account.getLastName()};
    }

    private void buildBody(XSSFSheet sheet){
        List<Account> accountList = accountRepository.findAll();
        Map<Long, Object[]> accountMap = new TreeMap<>();
        for (Account account : accountList) {
            accountMap.put(account.getId(), mapAccountToObjectArr(account));
            }
        Set<Long> accountIdSet = accountMap.keySet();
        int rowNumber = 1;

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
            FileOutputStream out = new FileOutputStream( new File("D:/Accounts.xlsx"));
            workbook.write(out);
            out.close();}
        catch (IOException e){
            System.out.println("Error occurred while executing: saveFile.");
        }
    }

}

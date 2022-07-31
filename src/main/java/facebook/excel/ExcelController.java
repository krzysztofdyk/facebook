package facebook.excel;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class ExcelController {

    ExcelService excelService;

    @GetMapping("/xlsx")
    public void downloadAccountsExcel() {
        excelService.downloadAccountsExcel();
    }
}

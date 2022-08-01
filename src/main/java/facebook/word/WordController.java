package facebook.word;


import facebook.account.AccountRepository;
import facebook.transfer.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class WordController {

    AccountRepository accountRepository;
    WordService wordService;
    TransferRepository transferRepository;

    @GetMapping("/transfers-history/{transferId}")
    public HttpStatus downloadTransferConfirmationByTransferId(@PathVariable(name = "transferId") Long transferId){
        wordService.createTransferConfirmationReport(transferId);
        return HttpStatus.OK;
    }

    @GetMapping("/accounts-history/{accountId}")
    public HttpStatus downloadTransfersByAccountId(@PathVariable(name = "accountId") Long accountId){
        wordService.createAllTransfersReport(accountId);
        return HttpStatus.OK;
    }

    @GetMapping("/profile/{accountId}")
    public HttpStatus downloadProfileByAccountId(@PathVariable(name = "accountId") Long accountId){
        wordService.createProfileByAccountId(accountId);
        return HttpStatus.OK;
    }
}

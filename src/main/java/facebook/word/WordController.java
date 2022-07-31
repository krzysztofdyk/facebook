package facebook.word;


import facebook.account.Account;
import facebook.account.AccountRepository;
import facebook.transfer.Transfer;
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
    public HttpStatus downloadTransferByTransferId(@PathVariable(name = "transferId") Long transferId){
        Transfer transfer = transferRepository.getById(transferId);
        Account account = accountRepository.getById(transfer.getFromAccount().getId());
        wordService.createSingleWord(transfer, account);
        return HttpStatus.CREATED;
    }

    @GetMapping("/accounts-history/{accountId}")
    public HttpStatus downloadTransfersByAccountId(@PathVariable(name = "accountId") Long accountId){
        Account account = accountRepository.getById(accountId);
        wordService.createWordByUser(account);
        return HttpStatus.CREATED;
    }
}

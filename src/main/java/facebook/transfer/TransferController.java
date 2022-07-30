package facebook.transfer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    TransferService transferService;

    @GetMapping
    //@Operation(summary = "Find transfers.")
    @ResponseStatus(HttpStatus.OK)
    public List<TransferDtoResponse> findTransfersByCurrency(@RequestParam(name = "currencyName", required = false) String currencyName) {
        return transferService.findAllTransfers(currencyName);
    }

    @GetMapping("/{transferId}")
    //@Operation(summary = "Find transfer by ID.")
    @ResponseStatus(HttpStatus.OK)
    public TransferDtoResponse findTransferById(@PathVariable(name = "transferId") Long transferId) {
        return transferService.findTransferById(transferId);
    }

    @PostMapping("/{senderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createTransfer(@PathVariable Long senderId , @RequestBody TransferDtoRequest transferDtoRequest) {
        Transfer entity = transferService.createTransfer(senderId,transferDtoRequest);
        //return transferService.mapDto(entity);
        return HttpStatus.CREATED;
    }

    @PutMapping("/{transferId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransferDtoResponse updateTransfer(@PathVariable(name = "transferId") Long transferId, @RequestBody TransferDtoRequest transferDtoRequest) {
        Transfer transfer = transferService.updateTransfer(transferId, transferDtoRequest);
        return transferService.mapToDto(transfer);
    }

    @DeleteMapping("/{transferId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteTransfer(@PathVariable Long transferId) {
        transferService.deleteTransfer(transferId);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public Long sumTransfers(){
        return transferService.sumTransfers();
    }

    @GetMapping("/statistics/{currencyName}")
    @ResponseStatus(HttpStatus.OK)
    public Long sumTransfersByCurrency(@PathVariable(name = "currencyName") String currencyName){
        return transferService.sumTransfersByCurrencyQuery(currencyName);
    }

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> typeOfCurrency(){
        return transferService.typeOfCurrency();
    }
}

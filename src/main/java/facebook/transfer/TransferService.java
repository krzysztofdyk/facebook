package facebook.transfer;

import facebook.account.Account;
import facebook.account.AccountRepository;
import facebook.enums.KeyStatus;
import facebook.exeptions.ExceptionInfo;
import facebook.exeptions.TransferException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;


    public List<TransferDtoResponse> findAllTransfers(String currencyName) {
        if (currencyName != null) {
            log.info("Transfers with currency: {} were shown.", currencyName);
            return mapDtoList(transferRepository.findByCurrency(currencyName));

        } else {
            log.info("Transfers were shown.");
            return mapDtoList(transferRepository.findAll());
        }

    }

    public TransferDtoResponse findTransferById(Long transferId) {
        log.info("Transfer with ID: {} was shown.", transferId);
        return mapToDto(transferRepository.getById(transferId));
    }

    public Transfer createTransfer(Long senderId, TransferDtoRequest transferDtoRequest) {
        Account sender = accountRepository.getById(senderId);
        Account receiver = accountRepository.getById(transferDtoRequest.getToAccountId());
        checkActivityAccount(sender, receiver);
        checkAmountTransfer(transferDtoRequest.getAmount());
        LocalDate localDateNow = LocalDate.now();
        LocalTime localTimeNow = LocalTime.now();
        Transfer transfer = mapToEntity(transferDtoRequest, sender, localDateNow, localTimeNow);
        setAccountBalances(sender, receiver, transferDtoRequest.getAmount());
        transferRepository.save(transfer);
        log.info("New transfer with ID: {} was created", transfer.getId());
        return transfer;
    }

    private void checkActivityAccount(Account sender, Account receiver) {
        if (sender.getKeyStatus().equals(KeyStatus.Inactive)) {
            throw new TransferException("Sender is inactive.", HttpStatus.BAD_REQUEST);
        }
        if (receiver.getKeyStatus().equals(KeyStatus.Inactive)) {
            throw new TransferException("Sender is inactive.", HttpStatus.BAD_REQUEST);
        }
    }

    private void setAccountBalances(Account sender, Account receiver, Long money) {
        Long senderBalance = sender.getBalance();
        senderBalance = senderBalance - money;
        Long receiverBalance = receiver.getBalance();
        receiverBalance = receiverBalance + money;
        sender.setBalance(senderBalance);
        receiver.setBalance(receiverBalance);
        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    private void checkAmountTransfer(Long amount) {
        if (amount == 0) {
            throw new TransferException(ExceptionInfo.AMOUNT_WAS_PROVIDED_AS_ZERO.name(), HttpStatus.BAD_REQUEST);
        }
    }

    public Transfer updateTransfer(Long transferId, TransferDtoRequest transferDtoRequest) {
        Transfer transfer = transferRepository.getById(transferId);
        transfer.setTitle(transferDtoRequest.getTitle());
        transfer.setAmount(transferDtoRequest.getAmount());
        transfer.setCurrency(transferDtoRequest.getCurrency());
        transferRepository.save(transfer);
        log.info("Transfer with ID: {} was updated.", transferId);
        return transfer;
    }

    public void deleteTransfer(Long transferId) throws TransferException {
        transferRepository.findById(transferId).orElseThrow(() -> {
            throw new TransferException(ExceptionInfo.ID_WAS_NOT_FOUND.name(), HttpStatus.NOT_FOUND);
        });
        transferRepository.deleteById(transferId);
        log.info("Transfer with ID: {} was deleted.", transferId);
        // return ResponseEntity.noContent().build();
    }

    private Transfer mapToEntity(TransferDtoRequest transferDtoRequest, Account sender, LocalDate localDate, LocalTime localTime) {
        Account receiver = accountRepository.getById(transferDtoRequest.getToAccountId());
        return Transfer.builder()
                .title(transferDtoRequest.getTitle())
                .amount(transferDtoRequest.getAmount())
                .currency(transferDtoRequest.getCurrency())
                .fromAccount(sender)
                .toAccount(receiver)
                .localDate(localDate)
                .localTime(localTime)
                .build();
    }

    public TransferDtoResponse mapToDto(Transfer transfer) {
        return TransferDtoResponse.builder()
                .id(transfer.getId())
                .title(transfer.getTitle())
                .amount(transfer.getAmount())
                .currency(transfer.getCurrency())
                .fromAccountId(transfer.getFromAccount().getId())
                .toAccountId(transfer.getToAccount().getId())
                .localDate(transfer.getLocalDate())
                .localTime(transfer.getLocalTime())
                .build();
    }

    public List<TransferDtoResponse> mapDtoList(List<Transfer> transferList) {
        return transferList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long sumTransfers() {
        log.info("Get total sum finished.");
        return transferRepository.amountQuery();
    }

    public Long sumTransfersByCurrencyQuery(String currencyName) {
        log.info("Get sum by currency finished.");
        return transferRepository.amountByCurrencyQuery(currencyName);
    }

    public Set<String> typeOfCurrency() {
        List<Transfer> transfers = transferRepository.findAll();
        return transfers.stream()
                .map(Transfer::getCurrency)
                .collect(Collectors.toSet());
    }

    public boolean hasTransferFilledCurrencyName(Transfer transfer) {
        return Optional.ofNullable(transfer.getCurrency()).isEmpty();
    }

}

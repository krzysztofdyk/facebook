package facebook.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long> {

    List<Transfer> findByCurrency (String currencyName);

    //List<Transfer> findAllByAccountIdIn (List<Long> accountId);

    //@Query(value =
    //        "SELECT DISTINCT name" +
    //        "FROM TransferEntity" , nativeQuery = true )
    //List<String> find();

    @Query(value =
            "SELECT sum(t.amount)" +
            "FROM Transfer t", nativeQuery = true)
    Long amountQuery();

    @Query (value =
            "SELECT sum(t.amount)" +
            "FROM Transfer t" +
            "WHERE t.name = :currency" , nativeQuery = true)
    Long amountByCurrencyQuery(String currency);
}

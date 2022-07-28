package facebook.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Reservation findByHouse(String name);
    Reservation findByAccount(String name);
    List<Reservation> findAllByHouse(String name);
}

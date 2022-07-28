package facebook.reservation;

import facebook.account.Account;
import facebook.account.AccountRepository;
import facebook.house.House;
import facebook.house.HouseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HouseRepository houseRepository;
    private final AccountRepository accountRepository;

    public void createReservation(Long accountId,Long houseId, ReservationDto reservationDto)  {
        log.info("Reservation: start creation");
        Account account = accountRepository.getById(accountId);
        House house = houseRepository.getById(houseId);
        checkDates(house, reservationDto);
        log.info("Reservation: check dates done ");
        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        //reservation.setHouse(house);
        reservation.setAccount(account);
        reservation.setTotalPrice(countTheTotalPrice(reservationDto.getStartDate(), reservationDto.getEndDate(), house.getUnitPrice()));
        reservationRepository.save(reservation);
        log.info("Reservation: {} was done.",reservation.getId());
    }

    private void checkDates(House house,ReservationDto reservationDto){
        LocalDate startDate = reservationDto.getStartDate();
        LocalDate endDate = reservationDto.getEndDate();
        checkIfDatesAreChronological(startDate,endDate);
        checkIfStartDateIsNotInThePast(startDate);
        //checkIfHouseIsAvailable(startDate,endDate, house);
    }

    private void checkIfDatesAreChronological(LocalDate from, LocalDate to){
        if (to.isBefore(from)){
            throw new IllegalArgumentException(String.format("End date: %s can't be earlier than start date: %s" , to, from ));
        }
    }
    private void checkIfStartDateIsNotInThePast(LocalDate from){
        if (from.isBefore(LocalDate.now())){
            throw new IllegalArgumentException(String.format("Wrong dates, start date: %s can't be before: %s.", from, LocalDate.now()));
        }
    }

  /*  private void checkIfHouseIsAvailable(LocalDate from, LocalDate to, House house){
        List<Reservation> reservationList = reservationRepository.findAllByHouse(house.getName());

        boolean check = reservationList.stream()
                .map(reservation -> from.isAfter(reservation.getStartDate()) && from.isBefore(reservation.getEndDate())
                        && to.isAfter(reservation.getStartDate()) && to.isBefore(reservation.getEndDate()))
                .anyMatch(result-> result.equals(Boolean.TRUE));

        if(check){
            throw new IllegalArgumentException("Reservation dates are overlapping") ;
        }
    }*/

    public Reservation findReservationByHouse(String name){
        if (name != null && !name.isBlank()) {
            return reservationRepository.findByHouse(name);
        } return null;
    }


    public Reservation updateReservation(Long reservationId, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.getById(reservationId);
        House house = reservation.getHouse();
        reservation.setHouse(house);
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        log.info("Reservation: {} was updated.", reservation.getId());
        return reservation;
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.getById(id);
        reservationRepository.delete(reservation);
        log.info("Reservation: {} was deleted", reservation.getId());
    }

    public ReservationDtoResponse mapEntityToDto(Reservation reservation) {
        return ReservationDtoResponse.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .totalPrice(reservation.getTotalPrice())
                .accountId(reservation.getId())
                .houseId(reservation.getId())
                .build();
    }

    public List<ReservationDtoResponse> mapEntityToDtoList(List<Reservation> reservationEntities) {
        return reservationEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private Long countTheTotalPrice (LocalDate from,LocalDate to, long unitPrice){
        long amountOfDays = ChronoUnit.DAYS.between(from,to);
        return amountOfDays*unitPrice;
    }
}

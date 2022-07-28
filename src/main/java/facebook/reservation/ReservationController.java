package facebook.reservation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @PostMapping("/{accountId}/{houseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createReservation(@PathVariable Long accountId, @PathVariable Long houseId, @RequestBody ReservationDto reservationDto){
        reservationService.createReservation(accountId, houseId, reservationDto);
        return HttpStatus.CREATED;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDtoResponse> findAllReservations(){
        List<Reservation> reservationList = reservationRepository.findAll();
        return reservationService.mapEntityToDtoList(reservationList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDtoResponse findReservation(@PathVariable Long id){
        Reservation reservation = reservationRepository.getById(id);
        return reservationService.mapEntityToDto(reservation);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReservationDtoResponse updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto){
        Reservation reservation = reservationService.updateReservation(id, reservationDto);
        return reservationService.mapEntityToDto(reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return HttpStatus.NO_CONTENT;
    }
}

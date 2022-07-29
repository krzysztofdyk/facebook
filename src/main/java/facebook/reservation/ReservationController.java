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

    @GetMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDtoResponse findReservation(@PathVariable Long reservationId){
        Reservation reservation = reservationRepository.getById(reservationId);
        return reservationService.mapEntityToDto(reservation);
    }

    @PutMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReservationDtoResponse updateReservation(@PathVariable Long reservationId, @RequestBody ReservationDto reservationDto){
        Reservation reservation = reservationService.updateReservation(reservationId, reservationDto);
        return reservationService.mapEntityToDto(reservation);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteReservation(@PathVariable Long reservationId){
        reservationService.deleteReservation(reservationId);
        return HttpStatus.NO_CONTENT;
    }
}

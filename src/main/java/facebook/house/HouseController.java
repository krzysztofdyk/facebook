package facebook.house;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createHouse(@RequestBody HouseDto houseDto){
        houseService.createHouse(houseDto);
        return HttpStatus.CREATED;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<House> getAllHouses(){
        return houseService.findAllHouses();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus updateHouse(@PathVariable Long id, @RequestBody HouseDto houseDto) {
        houseService.updateHouse(id, houseDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return HttpStatus.NO_CONTENT;
    }
}

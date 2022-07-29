package facebook.house;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;

    public void createHouse(HouseDto houseDto){
        House house = mapDtoToEntity(houseDto);
        houseRepository.save(house);
        log.info("House: {} was created.",houseDto.getName());
    }

    private House mapDtoToEntity(HouseDto houseDto) {
        return  House.builder()
                .name(houseDto.getName())
                .unitPrice(houseDto.getUnitPrice())
                .area(houseDto.getArea())
                .description(houseDto.getDescription())
                .build();
    }

    public List<House> findAllHouses() {
        log.info("All houses were provided.");
        return houseRepository.findAll();

    }

    public List<HouseDto> mapEntityToDtoList(List<House> houseList){
        return houseList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private HouseDto mapEntityToDto(House house) {
        return HouseDto.builder()
                .name(house.getName())
                .unitPrice(house.getUnitPrice())
                .area(house.getArea())
                .description(house.getDescription())
                .build();
    }

    public void deleteHouse(Long houseId) {
        House house = houseRepository.getById(houseId);
        houseRepository.delete(house);
        log.info("House: " + house.getName() + " was deleted.");
    }

    public void  updateHouse(Long houseId, HouseDto houseDto) {
        House house = houseRepository.getById(houseId);
        house.setName(houseDto.getName());
        house.setUnitPrice(houseDto.getUnitPrice());
        house.setArea(houseDto.getArea());
        house.setDescription(houseDto.getDescription());
        houseRepository.save(house);
        log.info("House: {} was updated.", house.getName());
    }
}

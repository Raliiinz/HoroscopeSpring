package ru.itis.horoscope.controller.mapper;

import org.springframework.stereotype.Component;
import ru.itis.horoscope.controller.dto.StoneDto;
import ru.itis.horoscope.controller.dto.ZodiacDto;
import ru.itis.horoscope.entity.Stone;
import ru.itis.horoscope.entity.Zodiac;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZodiacMapper {
    public ZodiacDto toDto(Zodiac zodiac) {
        ZodiacDto dto = new ZodiacDto();
        dto.setSignName(zodiac.getSignName());
        dto.setStrongSide(zodiac.getStrongSide());
        dto.setWeakness(zodiac.getWeakness());
        dto.setInfo(zodiac.getInfo());
        dto.setImagePath(zodiac.getImagePath());

        if (zodiac.getStones() != null && !zodiac.getStones().isEmpty()) {
            List<StoneDto> stoneDto = zodiac.getStones().stream()
                    .map(this::stoneToDto)
                    .collect(Collectors.toList());
            dto.setStones(stoneDto);
        }

        return dto;
    }

    public StoneDto stoneToDto(Stone stone) {
        if (stone == null) {
            return null;
        }

        return StoneDto.builder()
                .stoneId(stone.getStoneId())

                .stoneName(stone.getStoneName())
                .description(stone.getDescription())
                .build();
    }
}

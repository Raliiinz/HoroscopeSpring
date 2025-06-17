package ru.itis.horoscope.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZodiacDto {
    private String signName;
    private String strongSide;
    private String weakness;
    private String info;
    private String imagePath;
    private List<StoneDto> stones;
}

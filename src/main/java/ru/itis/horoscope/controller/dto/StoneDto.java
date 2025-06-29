package ru.itis.horoscope.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoneDto {
    private Integer stoneId;
    private String stoneName;
    private String description;
}

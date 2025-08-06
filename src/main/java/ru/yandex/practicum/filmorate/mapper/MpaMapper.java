package ru.yandex.practicum.filmorate.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.dtos.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
public class MpaMapper {
    public static MpaDto mapMpaToDto(Mpa mpa) {
        return MpaDto.builder()
                .id(mpa.getId())
                .name(mpa.getName())
                .build();
    }

    public static Mpa mapToMpa(MpaDto dto) {
        return Mpa.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}

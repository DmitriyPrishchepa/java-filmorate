package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.dtos.MpaDto;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
public class MpaService {

    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<MpaDto> getRatings() {
        return mpaStorage.getRatings().stream()
                .map(MpaMapper::mapMpaToDto)
                .toList();
    }

    public MpaDto getMpaById(Long id) {
        return mpaStorage.getRatingById(id)
                .map(MpaMapper::mapMpaToDto)
                .orElseThrow(() -> new ElementNotFoundException("Mpa not found"));
    }
}

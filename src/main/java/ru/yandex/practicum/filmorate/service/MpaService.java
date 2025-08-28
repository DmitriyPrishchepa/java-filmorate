package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MpaService {

    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getRatings() {
        List<Mpa> uniqueMpaByName = mpaStorage.getRatings().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Mpa::getName,
                                mpa -> mpa,
                                (existing, ignored) -> existing),
                        map -> new ArrayList<>(map.values())))
                .stream()
                .sorted(Comparator.comparingInt(Mpa::getId))
                .limit(6)
                .collect(Collectors.toList());

        log.debug("size {}", uniqueMpaByName.size());
        return uniqueMpaByName;
    }

    public Mpa getMpaById(Integer id) {
        return mpaStorage.getRatingById(id)
                .orElseThrow(() -> new ElementNotFoundException("Mpa not found"));
    }
}

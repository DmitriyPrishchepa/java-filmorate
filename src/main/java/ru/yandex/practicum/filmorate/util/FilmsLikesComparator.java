package ru.yandex.practicum.filmorate.util;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

@Component
public class FilmsLikesComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        return Long.compare(o1.getLikes(), o2.getLikes());
    }
}

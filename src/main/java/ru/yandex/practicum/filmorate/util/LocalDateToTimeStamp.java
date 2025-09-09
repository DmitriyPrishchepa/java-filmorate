package ru.yandex.practicum.filmorate.util;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LocalDateToTimeStamp {

    public static <T> Timestamp localDateToTimeStamp(T entity) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (entity instanceof User user) {
            LocalDate localDate = user.getBirthday();
            LocalDateTime localDateTime = localDate.atStartOfDay();
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            timestamp = Timestamp.from(instant);
        }

        if (entity instanceof Film) {
            LocalDate localDate = ((Film) entity).getReleaseDate();
            LocalDateTime localDateTime = localDate.atStartOfDay();
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            timestamp = Timestamp.from(instant);
        }

        return timestamp;
    }
}


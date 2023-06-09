package ru.yandex.practicum.filmorate.exception;

public class NotFoundObject extends RuntimeException {
    public NotFoundObject(String object, Long id) {
        super(object + " c id: " + id + " не найден");
    }
}

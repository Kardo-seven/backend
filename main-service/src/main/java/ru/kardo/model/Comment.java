package ru.kardo.model;

import java.util.Set;

public class Comment {
    Long id;
    String text;
    Boolean isMyLike;
    Set<Long> likes;
    Set<Long> replies;
}

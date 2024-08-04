package ru.kardo.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Link;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAboutDto {

    private Long id;

    private String name;

    private String lastName;

    private String surName;

    private Set<Link> linkSet;

    private String about;

    private String avatarLink;
}
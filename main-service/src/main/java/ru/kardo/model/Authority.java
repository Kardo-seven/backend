package ru.kardo.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.EnumAuth;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Enumerated(EnumType.STRING)
    private EnumAuth authority;
}

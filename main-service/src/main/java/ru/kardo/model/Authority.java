package ru.kardo.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.EnumAuth;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements Serializable {

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnumAuth authority = EnumAuth.PARTICIPANT ;
}

package ru.kardo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscribers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "subscriber_id")
    private Long subscriberId;
}

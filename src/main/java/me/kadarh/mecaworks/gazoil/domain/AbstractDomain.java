package me.kadarh.mecaworks.gazoil.domain;

import lombok.Getter;
import lombok.Setter;
import me.kadarh.mecaworks.gazoil.config.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author kadarH
 */

@MappedSuperclass
@Getter
@Setter
public class AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    protected int version;

    @Convert(converter = LocalDateTimeConverter.class)
    protected LocalDateTime createdAt;

    @Convert(converter = LocalDateTimeConverter.class)
    protected LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
    }

    @PostPersist
    protected void onUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

}

package me.kadarh.mecaworks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author kadarH
 */

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

    @JsonIgnore
    protected LocalDateTime createdAt;

    @JsonIgnore
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

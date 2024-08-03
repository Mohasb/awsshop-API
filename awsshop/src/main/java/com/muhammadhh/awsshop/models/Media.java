package com.muhammadhh.awsshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "media")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Media {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "url cannot be empty")
    @Size(min = 2, max = 255)
    private String url;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    public enum MediaType {
        IMAGE, VIDEO, OBJ3D,
    }
}

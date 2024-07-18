package com.muhammadhh.awsshop.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
	@Size(min = 2, max = 50, message = "First name has to be between 2-50 letters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
	@Size(min = 2, max = 50, message = "Last name has to be between 2-50 letters")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}

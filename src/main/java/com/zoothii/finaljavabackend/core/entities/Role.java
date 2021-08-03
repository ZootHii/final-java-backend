package com.zoothii.finaljavabackend.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles",uniqueConstraints = {
		@UniqueConstraint(columnNames = "name")
})
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//@Enumerated(EnumType.STRING)
	@Pattern(regexp = "^ROLE_.*$", message = "Role name must start with ROLE_")
	@NotBlank
	@Column(length = 20)
	private String name;

}
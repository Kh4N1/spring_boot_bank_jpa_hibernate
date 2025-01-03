package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @ManyToMany(mappedBy = "roles")
  @Builder.Default
  private Set<User> users = new HashSet<>();
}

package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch extends BaseEntity {

  @Column(nullable = false)
  private String name;

  private String address;

  private String phoneNumber;

  @Column(unique = true)
  private String code;

  @OneToMany(mappedBy = "branch")
  @Builder.Default
  private Set<User> users = new HashSet<>();

  @OneToMany(mappedBy = "branch")
  @Builder.Default
  private Set<Account> accounts = new HashSet<>();
}

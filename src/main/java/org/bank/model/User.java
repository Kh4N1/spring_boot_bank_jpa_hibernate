package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserProfile profile;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @Builder.Default
  private Set<Account> accounts = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "branch_id")
  private Branch branch;
}

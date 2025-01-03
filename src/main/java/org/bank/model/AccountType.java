package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountType extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  private Double minimumBalance;

  private Double interestRate;

  @OneToMany(mappedBy = "accountType")
  @Builder.Default
  private Set<Account> accounts = new HashSet<>();
}

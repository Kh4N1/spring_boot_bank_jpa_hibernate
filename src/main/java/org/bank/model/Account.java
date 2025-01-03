package org.bank.model;

import jakarta.persistence.*;
import lombok.*;
import org.bank.model.enums.Currency;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {

  @Column(unique = true, nullable = false)
  private String accountNumber;

  @Column(nullable = false)
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Currency currency;

  private boolean isActive;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "account_type_id", nullable = false)
  private AccountType accountType;

  @ManyToOne
  @JoinColumn(name = "branch_id")
  private Branch branch;

  @OneToMany(mappedBy = "sourceAccount")
  @Builder.Default
  private Set<Transaction> outgoingTransactions = new HashSet<>();

  @OneToMany(mappedBy = "targetAccount")
  @Builder.Default
  private Set<Transaction> incomingTransactions = new HashSet<>();

  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
  private Card card;
}

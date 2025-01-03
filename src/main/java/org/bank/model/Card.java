package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String cardNumber;

  @Column(nullable = false)
  private String cvv;

  @Column(nullable = false)
  private LocalDate expiryDate;

  private boolean isActive;

  @OneToOne
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @OneToMany(mappedBy = "card")
  @Builder.Default
  private Set<CardTransaction> transactions = new HashSet<>();
}

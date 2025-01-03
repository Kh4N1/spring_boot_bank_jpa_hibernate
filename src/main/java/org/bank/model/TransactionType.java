package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transaction_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionType extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @OneToMany(mappedBy = "type")
  @Builder.Default
  private Set<Transaction> transactions = new HashSet<>();
}

package org.bank.model;

import jakarta.persistence.*;
import lombok.*;
import org.bank.model.enums.Currency;
import org.bank.model.enums.TransactionStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;
    
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;
    
    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;
    
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TransactionType type;
    
    private String referenceNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
}

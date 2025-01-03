package org.bank.model;

import jakarta.persistence.*;
import lombok.*;
import org.bank.model.enums.TransactionStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "card_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardTransaction extends BaseEntity {
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
    
    private String merchantName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
}

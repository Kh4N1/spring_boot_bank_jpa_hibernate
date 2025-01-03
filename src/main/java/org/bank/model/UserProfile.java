package org.bank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseEntity {
    
    private String nationalId;
    
    private String address;
    
    private String phoneNumber;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

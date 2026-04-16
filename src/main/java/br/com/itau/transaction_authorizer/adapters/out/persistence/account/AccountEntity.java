package br.com.itau.transaction_authorizer.adapters.out.persistence.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Long createdAt;

    @Version
    private Long version;
}

package com.mobiledger.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.math.BigDecimal;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;



@Entity
@Table (name = "accounts")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private BigDecimal balance;

    @JdbcTypeCode(SqlTypes.CHAR)
@Column(nullable = false, length = 3)
private String currency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Account() {
    }


public Account(
        User user,
        String name,
        String type,
        BigDecimal balance,
        String currency) {
    this.user = user;
    this.name = name;
    this.type = type;
    this.balance = balance;
    this.currency = currency;
}

public Long getId() {
    return id;
}

public User getUser() {
    return user;
}

public String getName() {
    return name;
}

public String getType() {
    return type;
}

public BigDecimal getBalance() {
    return balance;
}

public String getCurrency() {
    return currency;
}

public Instant getCreatedAt() {
    return createdAt;
}

public Instant getUpdatedAt() {
    return updatedAt;
}

public void updateDetails(
        String name,
        String type,
        BigDecimal balance,
        String currency) {
    this.name = name;
    this.type = type;
    this.balance = balance;
    this.currency = currency;
}

}

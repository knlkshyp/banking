package com.bank.card.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {

    @Id
    @Column(name = "card_id")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @NotNull
    @Column(name = "mobile_number")
    @Size(max = 15)
    private String mobileNumber;

    @NotNull
    @Size(max = 100)
    @Column(name = "card_number")
    private String cardNumber;

    @NotNull
    @Size(max = 100)
    @Column(name = "card_type")
    private String cardType;

    @NotNull
    @Column(name = "total_limit")
    private Integer totalLimit;

    @NotNull
    @Column(name = "amount_used")
    private Integer amountUsed;

    @NotNull
    @Column(name = "available_amount")
    private Integer availableAmount;
}

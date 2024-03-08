package com.bank.card.mapper;

import com.bank.card.dto.CardDto;
import com.bank.card.entity.Card;

public class CardMapper {

    public static CardDto mapToCardDto(Card card) {
        return CardDto.builder()
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .totalLimit(card.getTotalLimit())
                .amountUsed(card.getAmountUsed())
                .availableAmount(card.getAvailableAmount())
                .build();
    }

    public static Card mapToCard(CardDto cardDto) {
        return Card.builder()
                .cardNumber(cardDto.getCardNumber())
                .cardType(cardDto.getCardType())
                .totalLimit(cardDto.getTotalLimit())
                .amountUsed(cardDto.getAmountUsed())
                .availableAmount(cardDto.getAvailableAmount())
                .build();
    }

    public static void mapToCard(CardDto cardDto, Card card) {
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(card.getAvailableAmount());
    }
}

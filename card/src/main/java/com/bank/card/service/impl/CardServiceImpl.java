package com.bank.card.service.impl;

import com.bank.card.dto.CardDto;
import com.bank.card.entity.Card;
import com.bank.card.exception.CardAlreadyExistsException;
import com.bank.card.exception.ResourceNotFoundException;
import com.bank.card.mapper.CardMapper;
import com.bank.card.repository.CardRepository;
import com.bank.card.service.CardService;
import com.bank.card.util.CardConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with the given mobile number : " + mobileNumber);
        }
        cardRepository.save(initialiseCard(mobileNumber));
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardMapper.mapToCardDto(card);
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(() ->
                new ResourceNotFoundException("Card", "cardNumber", cardDto.getCardNumber()));
        CardMapper.mapToCard(cardDto, card);
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        cardRepository.deleteById(card.getCardId());
        return true;
    }

    private Card initialiseCard(String mobileNumber) {
        return Card.builder()
                .cardNumber(Long.toString(1000000000L + new Random().nextLong(9000000000L)))
                .mobileNumber(mobileNumber)
                .cardType(CardConstants.CREDIT_CARD)
                .totalLimit(CardConstants.NEW_CREDIT_LIMIT)
                .amountUsed(0)
                .availableAmount(CardConstants.NEW_CREDIT_LIMIT)
                .build();
    }
}

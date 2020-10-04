package zan.api.privat24.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BalanceInfo {
    @JacksonXmlProperty(localName = "cardbalance")
    private CardBalance cardBalance;

    public CardBalance getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(CardBalance cardBalance) {
        this.cardBalance = cardBalance;
    }
}

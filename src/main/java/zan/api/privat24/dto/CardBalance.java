package zan.api.privat24.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CardBalance {
    private Card card;

    @JacksonXmlProperty(localName = "av_balance")
    private double availableBalance;

    @JacksonXmlProperty(localName = "bal_date")
    private LocalDateTime balanceDate;

    @JacksonXmlProperty(localName = "bal_dyn")
    private String balDyn;

    @JacksonXmlProperty(localName = "balance")
    private double balance;

    @JacksonXmlProperty(localName = "fin_limit")
    private double financialLimit;

    @JacksonXmlProperty(localName = "trade_limit")
    private double tradeLimit;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public LocalDateTime getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(LocalDateTime balanceDate) {
        this.balanceDate = balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = LocalDateTime.parse(balanceDate, DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
    }

    public String getBalDyn() {
        return balDyn;
    }

    public void setBalDyn(String balDyn) {
        this.balDyn = balDyn;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFinancialLimit() {
        return financialLimit;
    }

    public void setFinancialLimit(double financialLimit) {
        this.financialLimit = financialLimit;
    }

    public double getTradeLimit() {
        return tradeLimit;
    }

    public void setTradeLimit(double tradeLimit) {
        this.tradeLimit = tradeLimit;
    }

    public class Card {
        @JacksonXmlProperty(localName = "account")
        private String accountNumber;

        @JacksonXmlProperty(localName = "card_number")
        private String cardNumber;

        @JacksonXmlProperty(localName = "acc_name")
        private String accountName;

        @JacksonXmlProperty(localName = "acc_type")
        private String accountType;

        @JacksonXmlProperty(localName = "currency")
        private String currencyCode;

        @JacksonXmlProperty(localName = "card_type")
        private String cardType;

        @JacksonXmlProperty(localName = "main_card_number")
        private String mainCardNumber;

        @JacksonXmlProperty(localName = "card_stat")
        private String cardStatus;

        @JacksonXmlProperty(localName = "src")
        private String source;

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getMainCardNumber() {
            return mainCardNumber;
        }

        public void setMainCardNumber(String mainCardNumber) {
            this.mainCardNumber = mainCardNumber;
        }

        public String getCardStatus() {
            return cardStatus;
        }

        public void setCardStatus(String cardStatus) {
            this.cardStatus = cardStatus;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}

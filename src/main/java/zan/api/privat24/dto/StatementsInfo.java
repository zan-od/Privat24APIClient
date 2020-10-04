package zan.api.privat24.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class StatementsInfo {
    @JacksonXmlProperty(localName = "statements")
    private AccountStatementsInfo accountStatementsInfo;

    public AccountStatementsInfo getCardStatements() {
        return accountStatementsInfo;
    }

    public void setCardStatements(AccountStatementsInfo accountStatementsInfo) {
        this.accountStatementsInfo = accountStatementsInfo;
    }
}

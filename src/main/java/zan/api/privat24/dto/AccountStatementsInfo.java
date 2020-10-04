package zan.api.privat24.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

//@JacksonXmlRootElement(localName = "")
public class AccountStatementsInfo {
    @JacksonXmlProperty(isAttribute = true)
    private String status;

    @JacksonXmlProperty(localName = "debet", isAttribute = true)
    private double debit;

    @JacksonXmlProperty(isAttribute = true)
    private double credit;

    @JacksonXmlProperty(localName="statement")
    List<AccountStatement> statements;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public List<AccountStatement> getStatements() {
        return statements;
    }

    public void setStatements(List<AccountStatement> statements) {
        this.statements = statements;
    }
}

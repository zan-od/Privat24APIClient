package zan.api.privat24.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties
public class CurrencyExchangeRate {
    @JsonAlias("ccy")
    String currencyCode;

    @JsonAlias("base_ccy")
    String baseCurrencyCode;

    @JsonAlias("buy")
    double buyRate;

    @JsonAlias("sale")
    double saleRate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(double saleRate) {
        this.saleRate = saleRate;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeRate{" +
                "currencyCode='" + currencyCode + '\'' +
                ", baseCurrencyCode='" + baseCurrencyCode + '\'' +
                ", buyRate=" + buyRate +
                ", saleRate=" + saleRate +
                '}';
    }
}

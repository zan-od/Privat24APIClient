package zan.api.privat24.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.HashMap;
import java.util.Map;

public class PaymentInfo {
    @JacksonXmlProperty(isAttribute = true)
    String id;
    String cardNumber;
    String countryCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @JsonIgnore
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Map<String, String> getProp() {
        Map<String, String> map = new HashMap<>();
        map.put("cardnum", getCardNumber());
        map.put("country", getCountryCode());

        return map;
    }

//    @JacksonXmlProperty(localName = "prop")
//    public Property getCardNumberProperty() {
//        return new Property("cardnum", getCardNumber());
//    }
//
//    @JacksonXmlProperty(localName = "prop")
//    public Property getCountryProperty() {
//        return new Property("country", getCountryCode());
//    }
//
//    private class Property {
//        @JacksonXmlProperty(isAttribute = true)
//        private String name;
//        @JacksonXmlProperty(isAttribute = true)
//        private String value;
//
//        public Property(String name, String value) {
//            this.name = name;
//            this.value = value;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//    }
}

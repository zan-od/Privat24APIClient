package zan.api.privat24;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import zan.api.privat24.builder.SignedRequestBuilder;
import zan.api.privat24.builder.TemplateBuilder;
import zan.api.privat24.connector.HttpApiConnector;
import zan.api.privat24.connector.HttpApiResponse;
import zan.api.privat24.dto.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zan.api.privat24.ApplicationConfig.ENVIRONMENT_DEFAULT;

public class Privat24ApiClient {

    private static final String CASH_CURRENCY_EXCHANGE_RATE_URL = "/pubinfo?json&exchange&coursid=5";
    private static final String BANK_CURRENCY_EXCHANGE_RATE_URL = "/pubinfo?json&exchange&coursid=11";
    private static final String BANK_OFFICES_URL = "/pboffice";
    private static final String BALANCE_URL = "/balance";
    private static final String STATEMENTS_URL = "/rest_fiz";
    private static final String PAYMENT_PRIVAT_URL = "/pay_pb";

    private final String REQUEST_TEMPLATE;
    private final String BALANCE_TEMPLATE;
    private final String STATEMENTS_TEMPLATE;
    private final String PAY_PRIVAT_TEMPLATE;

    private String merchantId;
    private String password;

    private final ApplicationConfig applicationConfig;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    public Privat24ApiClient() throws IOException {
        this(new ApplicationConfig(ENVIRONMENT_DEFAULT));
    }

    public Privat24ApiClient(ApplicationConfig config) throws IOException {
        this.applicationConfig = config;

        REQUEST_TEMPLATE = TemplateBuilder.loadTemplateFromFile("request_template.txt");
        BALANCE_TEMPLATE = TemplateBuilder.loadTemplateFromFile("balance_template.txt");
        STATEMENTS_TEMPLATE = TemplateBuilder.loadTemplateFromFile("statements_template.txt");
        PAY_PRIVAT_TEMPLATE = TemplateBuilder.loadTemplateFromFile("pay_privat_template.txt");
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private HttpApiConnector createConnector() throws IOException {
        return new HttpApiConnector(applicationConfig);
    }

    public List<CurrencyExchangeRate> getCashCurrencyExchangeRates() throws IOException {
        return getCurrencyExchangeRates(CASH_CURRENCY_EXCHANGE_RATE_URL);
    }

    public List<CurrencyExchangeRate> getBankCurrencyExchangeRates() throws IOException {
        return getCurrencyExchangeRates(BANK_CURRENCY_EXCHANGE_RATE_URL);
    }

    private List<CurrencyExchangeRate> getCurrencyExchangeRates(String url) throws IOException {
        HttpApiConnector connector = createConnector();
        HttpApiResponse response = connector.get(url);

        return jsonMapper.readValue(response.getResponse(), new TypeReference<List<CurrencyExchangeRate>>(){});
    }

    public List<BankOffice> listOffices() throws IOException {
        return findOffices(null, null);
    }

    public List<BankOffice> findOfficesByCity(String city) throws IOException {
        return findOffices(city, null);
    }

    public List<BankOffice> findOffices(String city, String address) throws IOException {
        String url = new UrlBuilder(BANK_OFFICES_URL)
                .withFlagParameter("json")
                .withParameter("city", city)
                .withParameter("address", address)
                .build();

        HttpApiConnector connector = createConnector();
        HttpApiResponse response = connector.get(url);

        return jsonMapper.readValue(response.getResponse(), new TypeReference<List<BankOffice>>(){});
    }

    public CardBalance getAccountBalance(String cardNumber) throws IOException, NoSuchAlgorithmException {
        String data = new TemplateBuilder()
                .withTemplate(BALANCE_TEMPLATE)
                .withParameter("oper", "cmt")
                .withParameter("wait", "0")
                .withParameter("test", "0")
                .withParameter("cardnumber", cardNumber)
                .withParameter("country", "UA")
                .build();

        String request = new SignedRequestBuilder()
                .withRequestTemplate(REQUEST_TEMPLATE)
                .withData(data)
                .withId(merchantId)
                .withPassword(password)
                .build();

        HttpApiConnector connector = createConnector();
        HttpApiResponse response = connector.post(BALANCE_URL, request);

        if (hasError(response.getResponse())) {
            handleApiError(response);
        }

        ApiResponse<ResponseInfo<BalanceInfo>> balanceResponse = xmlMapper.readValue(response.getResponse(),
                new TypeReference<ApiResponse<ResponseInfo<BalanceInfo>>>(){});

        return balanceResponse.getData().getInfo().getCardBalance();
    }

    public List<AccountStatement> getAccountStatements(String cardNumber, LocalDate dateFrom, LocalDate dateTo) throws IOException, NoSuchAlgorithmException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String data = new TemplateBuilder()
                .withTemplate(STATEMENTS_TEMPLATE)
                .withParameter("oper", "cmt")
                .withParameter("wait", "0")
                .withParameter("test", "1")
                .withParameter("cardnumber", cardNumber)
                .withParameter("dateFrom", dateFrom.format(formatter))
                .withParameter("dateTo", dateTo.format(formatter))
                .build();

        String request = new SignedRequestBuilder()
                .withRequestTemplate(REQUEST_TEMPLATE)
                .withData(data)
                .withId(merchantId)
                .withPassword(password)
                .build();

        HttpApiConnector connector = createConnector();
        HttpApiResponse response = connector.post(STATEMENTS_URL, request);

        if (hasError(response.getResponse())) {
            handleApiError(response);
        }

        System.out.println(response.getResponse());

        ApiResponse<ResponseInfo<StatementsInfo>> statementsResponse = xmlMapper.readValue(response.getResponse(),
                new TypeReference<ApiResponse<ResponseInfo<StatementsInfo>>>(){});

        return statementsResponse.getData().getInfo().getCardStatements().getStatements();
    }

    public void payToPrivatBankCard(String cardNumber, double amount, String currencyCode, String paymentId, String details) throws IOException, NoSuchAlgorithmException {
        String data = new TemplateBuilder()
                .withTemplate(PAY_PRIVAT_TEMPLATE)
                .withParameter("oper", "cmt")
                .withParameter("wait", "0")
                .withParameter("test", "0")
                .withParameter("cardnumber", cardNumber)
                .withParameter("payment_id", paymentId)
                .withParameter("amount", Double.toString(amount))
                .withParameter("currency", currencyCode)
                .withParameter("details", details)
                .build();

        String request = new SignedRequestBuilder()
                .withRequestTemplate(REQUEST_TEMPLATE)
                .withData(data)
                .withId(merchantId)
                .withPassword(password)
                .build();

        HttpApiConnector connector = createConnector();
        HttpApiResponse response = connector.post(PAYMENT_PRIVAT_URL, request);

        if (hasError(response.getResponse())) {
            handleApiError(response);
        }

        System.out.println(response.getResponse());

//        ApiResponse<ResponseInfo<StatementsInfo>> statementsResponse = xmlMapper.readValue(response.getResponse(),
//                new TypeReference<ApiResponse<ResponseInfo<StatementsInfo>>>(){});

        //return statementsResponse.getData().getInfo().getCardStatements().getStatements();
    }

    private boolean hasError(String response) {
        return response.contains("<error");
    }

    private void handleApiError(HttpApiResponse response) throws JsonProcessingException {
        ApiErrorResponse errorResponse = xmlMapper.readValue(response.getResponse(), ApiErrorResponse.class);
        throw new RuntimeException(String.format("API error %s: %s", response.getCode(), errorResponse));
    }

    private static class UrlBuilder {
        private final String url;
        private final Map<String, String> parameters = new HashMap<>();

        UrlBuilder(String url) {
            this.url = url;
        }

        UrlBuilder withFlagParameter(String name) {
            if (name != null) {
                parameters.put(name, null);
            }

            return this;
        }

        UrlBuilder withParameter(String name, String value) {
            if (name != null && value != null) {
                parameters.put(name, value);
            }

            return this;
        }

        String build() {
            if (parameters.isEmpty()) {
                return url;
            }

            StringBuilder builder = new StringBuilder(url).append("?");
            int i = 0;
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                i++;
                if (i > 1) {
                    builder.append("&");
                }
                builder.append(entry.getKey());
                if (entry.getValue() != null) {
                    builder.append("=").append(entry.getValue());
                }
            }

            return builder.toString();
        }
    }

}

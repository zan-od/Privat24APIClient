package zan.api.privat24;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import zan.api.privat24.dto.AccountStatement;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;
import static zan.api.privat24.ApplicationConfig.ENVIRONMENT_TEST;

public class Privat24ApiClientTest {

    private final ApplicationConfig testConfig = new ApplicationConfig(ENVIRONMENT_TEST);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(testConfig.getServerPort());

    public Privat24ApiClientTest() throws IOException {
    }


    @Test
    public void getAccountStatements() throws IOException, NoSuchAlgorithmException {
        // given
        Privat24ApiClient client = new Privat24ApiClient(testConfig);
        client.setMerchantId("1");
        client.setPassword("pass");

        wireMockRule.stubFor(post("/rest_fiz")
                .willReturn(okXml("" +
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response version=\"1.0\"><merchant><id>1</id><signature>12345</signature></merchant><data><oper>cmt</oper><info><statements status=\"excellent\" credit=\"1.0\" debet=\"2.52\">\n" +
                        "<statement card=\"card1\" appcode=\"app1\" trandate=\"2020-01-01\" trantime=\"16:03:00\" amount=\"323.62 UAH\" cardamount=\"1.00 UAH\" rest=\"10.00 UAH\" terminal=\"PrivatBank, 11\" description=\"Перевод с карты ПриватБанка через приложение Приват24. Отправитель: Иванов\"/>\n" +
                        "<statement card=\"card1\" appcode=\"app2\" trandate=\"2020-01-02\" trantime=\"14:00:00\" amount=\"703.52 UAH\" cardamount=\"-2.52 UAH\" rest=\"7.48 UAH\" terminal=\"PrivatBank, 22\" description=\"Перевод на карту ПриватБанка через Приват24. Получатель: Петров\"/>\n" +
                        "</statements></info></data></response>")));

        // when
        List<AccountStatement> statements = client.getAccountStatements("card1", LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-02"));

        // then
        assertNotNull(statements);
        assertEquals(1, statements.size());

    }
}
package com.zendesk.client;

import com.zendesk.client.entity.DealInfo;
import com.zendesk.client.entity.Items;
import com.zendesk.client.service.ZendeskService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

/**
 * The {@link ZendeskClientJavaApplicationTests} is a
 * Spring Boot application
 * that manages a bounded context for
 * Zendesk
 *
 * @author Olga Savin
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ZendeskClientJavaApplicationTests {

    @Value("${zendesk.token}")
    String token;

    @Value("${test.email}")
    String email;

    @Value("${test.name}")
    String name;

    @Value("${test.phone}")
    String phone;

    long dealId;

    @Autowired
    private ZendeskService zendeskService;

    @Test
    @Order(1)
    public void testZendeskContacts() {
        zendeskService.setToken(token);
        List<Items> contacts = zendeskService.getContacts(zendeskService.getLocalDateTime("2022-01-01T00:00:00Z"));
        contacts.forEach(System.out::println);
    }

    @Test
    @Order(2)
    public void testZendeskCreateContact() throws Exception {
        zendeskService.setToken(token);
        zendeskService.createClientIfNotExists(name, email, phone);
    }

    @Test
    @Order(3)
    public void testZendeskCreateOrganization() throws Exception {
        zendeskService.setToken(token);
        HashMap<String, Object> f = new HashMap<>();
        f.put("Horario", "9:00h a 21:00h");
        f.put("Teléfono información", "933 93 24 10");
        f.put("Site", "serveiesoutlet.com");
        System.out.println(zendeskService.createOrganization("Outlet online", null, f, "Barcelona", "Spain", "Carrer de l'Espaseria 19", "08003"));
    }

    @Test
    @Order(4)
    public void testGetContactInfo() throws Exception {
        zendeskService.setToken(token);
        List<Items> contacts = zendeskService.getContactsByEmail(email);
        contacts.forEach(System.out::println);
    }

    @Test
    @Order(5)
    public void testCreateDeal() throws Exception {
        zendeskService.setToken(token);
        List<Items> contacts = zendeskService.getContactsByEmail(email);
        contacts.forEach((c) -> {
            try {
                DealInfo deal = zendeskService.createDeal("Zara Home", c.getData().getId(), "Outlet online", "Barcelona Outlet", "The non-stop designer sale. Shop designer brands up to 80% off.", 1, "X-clock", "4 years");
                dealId=deal.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @Order(6)
    public void testGetDealInfo() {
        zendeskService.setToken(token);
        zendeskService.getDealInfo(dealId);
    }
}

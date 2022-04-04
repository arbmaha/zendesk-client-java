package com.zendesk.client.service;

import com.google.gson.Gson;
import com.zendesk.client.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The {@link ZendeskService}
 * provides interaction
 * with methods
 *
 * @author Olga Savin
 */
@Service
public class ZendeskService {

    final String PARTNER = "Partner";
    final String DISTRIBUTOR = "Distributor";
    final String PRODUCT_NAME = "Product name";
    final String PRODUCT_TIME = "Product time";
    final String DEAL_DESCRIPTION = "Deal Description";
    final String QUANTITY = "Quantity";
    final String PARTNER_A = "Partner 1";
    @Value("${zendesk.url}")
    String url;
    int PAGE_SIZE_READ = 100;
    int PAGE_SIZE = 20;
    String token;

    RestTemplate restTemplate;

    public ZendeskService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * To set an API key
     *
     * @param token access key
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method for getting contacts
     *
     * @param fromTime search time
     * @return List<Items> list of contact details
     */
    public List<Items> getContacts(LocalDateTime fromTime) {

        List<Items> items = new LinkedList<>();
        int pageSize = fromTime == null ? PAGE_SIZE_READ : PAGE_SIZE;
        boolean firstUpdatedItem = false;
        for (int numberPage = 1; ; numberPage++) {
            HttpEntity<String> requestEntity = new HttpEntity<>(getHttpHeaders());
            ResponseEntity<String> responseEntity;
            String rqString = "page=" + numberPage + "&per_page=" + pageSize + "&sort_by=updated_at:desc&is_organization=true";
            try {
                responseEntity = restTemplate.exchange(url + "/v2/contacts?" + rqString, HttpMethod.GET, requestEntity, String.class);
            } catch (Exception e) {
                return null;
            }

            try {
                Gson gson = new Gson();
                ContactsResponse resp = gson.fromJson(responseEntity.getBody(), ContactsResponse.class);
                if (resp.getItems() != null) {
                    for (Items it : resp.getItems()) {
                        System.out.println(it.getData().toString());
                        LocalDateTime updated = getLocalDateTime(it.getData().getUpdated_at());
                        if (fromTime != null && updated.isBefore(fromTime)) {
                            firstUpdatedItem = true;
                            break;
                        }
                        items.add(it);

                    }
                } else {
                    break;
                }
                if (firstUpdatedItem || resp.getMeta().getCount() < pageSize) break;

            } catch (Exception e) {
                return null;
            }

        }
        return items;
    }

    /**
     * Method for searching for a contact
     *
     * @param email contact email address
     * @return List<Items> list of contact details
     */
    public List<Items> getContactsByEmail(String email) throws Exception {
        List<Items> items = new LinkedList<>();

        HttpEntity<String> requestEntity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<String> responseEntity;
        String rqString = "is_organization=false&email=" + email;
        try {
            responseEntity = restTemplate.exchange(url + "/v2/contacts?" + rqString, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            throw new Exception("Request failed: " + e.getResponseBodyAsString(), e);
        }
        Gson gson = new Gson();
        ContactsResponse resp;
        try {
            resp = gson.fromJson(responseEntity.getBody(), ContactsResponse.class);
            if (resp.getItems() != null) {
                Collections.addAll(items, resp.getItems());
            }
            return items;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method for creating contact and organization
     *
     * @param name  contact name
     * @param email contact email address
     * @param phone contact phone number
     */
    public void createClientIfNotExists(String name, String email, String phone) throws Exception {
        List<Items> contacts = getContactsByEmail(email);

        if (contacts.size() == 0) {
            createOrganizationContact(name, email, phone);
        }
    }

    /**
     * Method for creating contact
     *
     * @param item contact details
     */
    private Items createContact(Items item) throws Exception {
        Gson gson = new Gson();
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(item), getHttpHeaders());
        ResponseEntity<String> responseEntity;
        gson = new Gson();
        Items resp;
        try {
            responseEntity = restTemplate.exchange(url + "/v2/contacts", HttpMethod.POST, requestEntity, String.class);
            resp = gson.fromJson(responseEntity.getBody(), Items.class);
        } catch (HttpClientErrorException e) {
            throw new Exception("Request failed: " + e.getResponseBodyAsString(), e);
        }

        return resp;
    }

    /**
     * Method for creating a deal
     *
     * @param dealResponse contact details
     */
    private DealResponse createDealRequest(DealResponse dealResponse) throws Exception {
        Gson gson = new Gson();
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(dealResponse), getHttpHeaders());
        ResponseEntity<String> responseEntity;
        gson = new Gson();
        DealResponse resp;
        try {
            responseEntity = restTemplate.exchange(url + "/v2/deals", HttpMethod.POST, requestEntity, String.class);
            resp = gson.fromJson(responseEntity.getBody(), DealResponse.class);
        } catch (HttpClientErrorException e) {
            throw new Exception("Request failed: " + e.getResponseBodyAsString(), e);
        }

        return resp;
    }

    /**
     * Method for getting information about a deal
     *
     * @param id of the deal
     * @return DealInfo
     */
    public DealInfo getDealInfo(long id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<String> responseEntity;
        new DealResponse();
        DealResponse resp;

        try {
            responseEntity = restTemplate.exchange(url + "/v2/deals/" + id, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {

            return null;
        }
        Gson gson = new Gson();
        resp = gson.fromJson(responseEntity.getBody(), DealResponse.class);
        if (resp.data.custom_fields != null) {
            if (resp.data.custom_fields.containsKey(PARTNER)) {
                resp.data.partner = "" + resp.data.custom_fields.get(PARTNER);
            } else {
                resp.data.partner = "" + resp.data.custom_fields.get(PARTNER_A);
            }
        }
        return resp.data;
    }

    /**
     * Method for creating an organization
     *
     * @param name  contact name
     * @param email contact email address
     * @param phone contact phone
     * @return DealInfo
     */
    public Data createOrganizationContact(String name, String email, String phone) throws Exception {
        Data data = new Data();
        data.setName(name);
        data.setIs_organization(false);
        data.setEmail(email);
        data.setPhone(phone);
        Items item = new Items();
        item.setData(data);
        return createContact(item).getData();
    }

    /**
     * Method for creating an organization in Zendesk
     *
     * @param name         contact name
     * @param tags         attributes
     * @param customFields additional fields
     * @param city         city
     * @param state        country
     * @param line         additional fields
     * @param zip          zip code
     * @return Data
     */
    public Data createOrganization(String name, String[] tags, HashMap<String, Object> customFields, String city, String state, String line, String zip) throws Exception {
        Data data = new Data();
        Address address = new Address();
        address.setCity(city);
        address.setState(state);
        address.setLine1(line);
        address.setPostal_code(zip);
        data.setName(name);
        data.setTags(tags);
        data.setIs_organization(true);
        data.setCustom_fields(customFields);
        data.setCustomer_status("current");
        data.setProspect_status("current");
        data.setAddress(address);
        Items item = new Items();
        item.setData(data);

        return createContact(item).getData();
    }

    /**
     * Method for creating a deal in Zendesk
     *
     * @param name        deal name
     * @param contactId   contact
     * @param distributor information
     * @param partner     deals
     * @param description description
     * @param quantity    quantity
     * @param productName product info
     * @param productTime transaction time
     * @return DealInfo
     */
    public DealInfo createDeal(String name, Long contactId, String distributor, String partner, String description, int quantity, String productName, String productTime) throws Exception {
        DealResponse d = new DealResponse();
        d.data = new DealInfo();
        d.data.name = name;
        d.data.contact_id = contactId;
        d.data.customized_win_likelihood = 100;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        d.data.estimated_close_date = LocalDate.now().format(df);
        d.data.custom_fields = new HashMap<>();
        d.data.custom_fields.put(PARTNER, partner);
        d.data.custom_fields.put(DISTRIBUTOR, distributor);
        d.data.custom_fields.put(PRODUCT_NAME, productName);
        d.data.custom_fields.put(PRODUCT_TIME, productTime);
        d.data.custom_fields.put(DEAL_DESCRIPTION, description);
        d.data.custom_fields.put(QUANTITY, "" + quantity);
        d = createDealRequest(d);
        return d == null ? null : d.data;
    }

    /**
     * Method for getting headers
     *
     * @return HttpHeaders
     */
    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

    /**
     * Method for getting time
     *
     * @param strDate time in format
     * @return Date
     */
    public LocalDateTime getLocalDateTime(@NonNull String strDate) {
        Date date = getDate(strDate);
        return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Method to find time formats
     *
     * @param strDate time in format
     * @return Date
     */
    public Date getDate(@NonNull String strDate) {
        SimpleDateFormat[] formats = new SimpleDateFormat[]{
                new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("HH:mm dd.MM.yyyy"),
                new SimpleDateFormat("yyyy-MM-dd")
        };
        for (SimpleDateFormat format : formats) {
            try {
                return format.parse(strDate);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

}

# Zendesk API Connector Java
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This is a lightweight program that works as a connector to Zendesk

Zendesk is a customer service and sales CRM software

CRM systems are changeless. They serve for collecting, storage and use of information about customers. Customers are one of the most important assets of any company.

**CRM include programs**
--------------------------

1. Customer data management

2. Deal management

Test cases and examples

1. Customizable base URL, request timeout and HTTP proxy

2. Response Metadata


**Run Example**
--------------------------

The examples are located under src/test/java/com/zendesk/client. Before running the ZendeskClientJavaApplicationTests, set up your zendesk.token in application.properties.

Customer data management
1. Create a contact

    ```
    public void testZendeskCreateContact() throws Exception {
        zendeskService.setToken(token);
        zendeskService.registerClientIfNotExists(name, email, phone);
    }
    ```
2. Get info

   ```
   public void testGetContactInfo() throws Exception {
       zendeskService.setToken(token);
       List<Items> contacts = zendeskService.getContactsByEmail(email);
       contacts.forEach(System.out::println);
   }
   ```

Deal management

3. Create a deal

    ```
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
    ```
3. Get info about the deal

    ```
   public void testGetDealInfo() throws Exception {
        zendeskService.setToken(token);
        zendeskService.getDealInfo(dealId);
    }
    ```

## Contributing

Contributions are welcome.<br/>
If you've found a bug within this project, please open an issue to discuss what you would like to change.<br/>
If it's an issue with the API, please open a topic.

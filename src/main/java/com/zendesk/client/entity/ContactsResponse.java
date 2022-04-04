package com.zendesk.client.entity;
/**
 * This entity stores information about
 * {@link ContactsResponse}.
 *
 * @author Olga Savin
 */
public class ContactsResponse {
    private Items[] items;
    private Meta meta;

    public Items[] getItems() {
        return items;

    }

    public void setItems(Items[] items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}

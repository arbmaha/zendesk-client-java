package com.zendesk.client.entity;
/**
 * This entity stores information about
 * {@link DealResponse}.
 *
 * @author Olga Savin
 */
public class DealResponse {

    public DealInfo data;

    public DealInfo getData() {
        return data;
    }

    public void setData(DealInfo data) {
        this.data = data;
    }
}

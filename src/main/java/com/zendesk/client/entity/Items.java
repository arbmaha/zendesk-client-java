package com.zendesk.client.entity;
/**
 * This entity stores information about
 * {@link Items}.
 *
 * @author Olga Savin
 */
public class Items {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Items [data = " + data + "]";
    }
}

package com.zendesk.client.entity;
/**
 * This entity stores information about
 * {@link ContactsResponse}.
 *
 * @author Olga Savin
 */
public class Meta {
    private int total_count;
    private int count;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "Meta [type = " + type + "]";
    }
}

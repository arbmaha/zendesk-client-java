package com.zendesk.client.entity;

import java.util.Map;
/**
 * This entity stores information about
 * {@link Data}.
 *
 * @author Olga Savin
 */
public class Data {
    private Address address;

    private String updated_at;

    private Map<String, Object> custom_fields;

    private Long id;
    private Long contact_id;

    private String name;
    private String email;
    private String phone;

    private String version;

    private String[] tags;

    private boolean  is_organization;

    private String customer_status;
    private String prospect_status;


    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Map<String, Object> getCustom_fields() {
        return custom_fields;
    }

    public void     setCustom_fields(Map<String, Object> custom_fields) {
        this.custom_fields = custom_fields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isIs_organization() {
        return is_organization;
    }

    public void setIs_organization(boolean is_organization) {
        this.is_organization = is_organization;
    }

    public String getCustomer_status() {
        return customer_status;
    }

    public void setCustomer_status(String customer_status) {
        this.customer_status = customer_status;
    }

    public String getProspect_status() {
        return prospect_status;
    }

    public void setProspect_status(String prospect_status) {
        this.prospect_status = prospect_status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Data [address = " + address + ", updated_at = " + updated_at + ", custom_fields = " + custom_fields + ", id = " + id + ", name = " + name + ", version = " + version + ", tags = " + tags + "]";
    }
}

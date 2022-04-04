package com.zendesk.client.entity;

import java.util.HashMap;
/**
 * This entity stores information about
 * {@link DealInfo}.
 *
 * @author Olga Savin
 */
public class DealInfo {
    public Long id;
    public Long contact_id;
    public String name;
    public String partner;
    public Integer stage_id;
    public Integer customized_win_likelihood;
    public String estimated_close_date;
    public HashMap<String, String> custom_fields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContact_id() {
        return contact_id;
    }

    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Integer getStage_id() {
        return stage_id;
    }

    public void setStage_id(Integer stage_id) {
        this.stage_id = stage_id;
    }

    public Integer getCustomized_win_likelihood() {
        return customized_win_likelihood;
    }

    public void setCustomized_win_likelihood(Integer customized_win_likelihood) {
        this.customized_win_likelihood = customized_win_likelihood;
    }

    public String getEstimated_close_date() {
        return estimated_close_date;
    }

    public void setEstimated_close_date(String estimated_close_date) {
        this.estimated_close_date = estimated_close_date;
    }

    public HashMap<String, String> getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(HashMap<String, String> custom_fields) {
        this.custom_fields = custom_fields;
    }
}

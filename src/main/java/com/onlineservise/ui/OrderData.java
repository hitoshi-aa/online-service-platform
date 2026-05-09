package com.onlineservise.ui;

public class OrderData {

    private Integer id;
    private String client;
    private String phone;
    private String service;
    private String master;
    private String status;

    public OrderData(
            Integer id,
            String client,
            String phone,
            String service,
            String master,
            String status
    ) {

        this.id = id;
        this.client = client;
        this.phone = phone;
        this.service = service;
        this.master = master;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public String getPhone() {
        return phone;
    }

    public String getService() {
        return service;
    }

    public String getMaster() {
        return master;
    }

    public String getStatus() {
        return status;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.onlineservise.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceOrder {

    private Long id;

    private LocalDateTime orderDate;

    private Client client;

    private Master master;

    private String status;

    private List<Service> services = new ArrayList<>();
}

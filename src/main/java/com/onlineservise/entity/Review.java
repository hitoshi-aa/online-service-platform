package com.onlineservise.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {

    private Long id;

    private Integer rating;

    private String comment;

    private Client client;
}

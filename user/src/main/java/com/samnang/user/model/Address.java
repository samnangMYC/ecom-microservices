package com.samnang.user.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Addresses")
public class Address {
    @Id
    private String id;
    private String street;
    private String city;
    private String state;
    private String code;
    private String country;
}
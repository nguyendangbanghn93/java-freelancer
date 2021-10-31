package com.example.freelancer.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShortTransactionDTO {
    private int id;
    private Date createdAt;
    private double amount;
}

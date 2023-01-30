package com.example.TransportCompany.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryDto {
    
    @NotNull
    private String type;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private String origin;
    
    @NotNull
    private String destination;

    @NotNull
    private String startDate;

    @NotNull
    private String finishDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String companyName;

    @NotNull
    private String clientUCN;

    private String isPaid;

    private String isDelivered;
}
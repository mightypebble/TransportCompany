package com.example.TransportCompany.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleRegisterDto {
    
    @NotNull
    private String brand;

    @NotNull
    private String category;

    @NotNull
    private String registrationNumber;

    @NotNull
    private String companyName;
}

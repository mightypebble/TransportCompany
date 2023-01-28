package com.example.TransportCompany.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientRegisterDto {
    
    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    private String UCN;

    @NotNull
    private String companyName;
}
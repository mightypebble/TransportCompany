package com.example.TransportCompany.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "brand")
    private String brand;
    
    @NotNull
    @Column(name = "category")
    private String category;

    @NotNull
    @Column(name = "reg_number")
    private String registrationNumber;

    @OneToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyEntity company;
    
}

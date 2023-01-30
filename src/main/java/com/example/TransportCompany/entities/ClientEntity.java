package com.example.TransportCompany.entities;

import java.util.List;

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
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "UCN")
    private String UCN;

    @OneToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyEntity company;

    // @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Delivery> deliveries;
    
}

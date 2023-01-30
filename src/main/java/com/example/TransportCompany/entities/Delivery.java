package com.example.TransportCompany.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
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
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "origin")
    private String origin;
    
    @Column(name = "destination")
    private String destination;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "finish_date")
    private String finishDate;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_paid", columnDefinition = "tinyint(1) default 0")
    private boolean isPaid;

    @Column(name = "is_delivered", columnDefinition = "tinyint(1) default 0")
    private boolean isDelivered;

    @OneToOne
    @JoinColumn(name = "client", nullable = false)
    private ClientEntity client;

    @OneToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyEntity company;
    
}
package it.softengunina.userservice.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "real_estate_agencies")
public class RealEstateAgency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String iban;

    private String name;

    public RealEstateAgency(String iban, String name) {
        this.iban = iban;
        this.name = name;
    }

    protected RealEstateAgency(){}

    public String getIban() {
        return iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstateAgency that)) return false;
        return Objects.equals(iban, that.iban) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, name);
    }
}


package it.city.tokenvalidation.entity;

import it.city.tokenvalidation.entity.template.AbsNameEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends AbsNameEntity {
    @Column(nullable = false)
    private double price;
    @OneToOne(optional = false)
    private Attachment attachment;

    private String description;
    @ManyToOne
    private Category category;

}

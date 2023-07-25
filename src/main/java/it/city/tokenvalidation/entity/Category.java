package it.city.tokenvalidation.entity;

import it.city.tokenvalidation.entity.template.AbsNameEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsNameEntity {
    @OneToOne(optional = false)
    private Attachment attachment;
    private boolean isDisplay;
}
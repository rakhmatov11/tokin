package it.city.tokenvalidation.entity;

import it.city.tokenvalidation.entity.template.AbsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//Biz ko'rib turgan contentning o'lchami, nomi, turi(mp3, mp4, mpeg va hokazolar)
public class Attachment extends AbsEntity {

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contentType;
}

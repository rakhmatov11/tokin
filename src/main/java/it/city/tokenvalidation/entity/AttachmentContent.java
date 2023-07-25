package it.city.tokenvalidation.entity;

import it.city.tokenvalidation.entity.template.AbsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//Bu contentning byte larda yuklanishi
public class AttachmentContent extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    @Column(nullable = false)
    private byte[] content;

    @Column(columnDefinition = "text")
    private String stringContent;
}

package org.kuali.student.enrollment.class2.courseofferingset.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_SOC_ATTR")
public class SocAttributeEntity extends BaseAttributeEntityNew<SocEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private SocEntity owner;

    public SocAttributeEntity() {}

    public SocAttributeEntity(String key, String value) {
        super(key, value);
    }

    public SocAttributeEntity(Attribute att, SocEntity owner) {
        super(att);
        setOwner(owner);
    }

    @Override
    public void setOwner(SocEntity owner) {
        this.owner = owner;

    }

    @Override
    public SocEntity getOwner() {
        return owner;
    }
}

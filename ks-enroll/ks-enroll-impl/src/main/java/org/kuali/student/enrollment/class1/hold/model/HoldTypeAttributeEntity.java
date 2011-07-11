package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_HOLD_TYPE_ATTR")
public class HoldTypeAttributeEntity extends BaseAttributeEntity<HoldTypeEntity> {
    public HoldTypeAttributeEntity () {
    }
    
    public HoldTypeAttributeEntity(String key, String value) {
        super(key, value);
    }

    public HoldTypeAttributeEntity(Attribute att) {
        super(att);
    }
}

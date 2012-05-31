package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.infc.Expenditure;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_LUI_EXPENDITURE")
public class LuiExpenditureEntity extends MetaEntity implements AttributeOwner<LuiExpenditureAttributeEntity> {

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LuiExpenditureAttributeEntity> attributes;

    public LuiExpenditureEntity() {}

    public LuiExpenditureEntity(Expenditure expenditure) {
        super(expenditure);
        this.setId(expenditure.getId());

        // Attributes
        this.setAttributes(new HashSet<LuiExpenditureAttributeEntity>());
        if (null != expenditure.getAttributes()) {
            for (Attribute att : expenditure.getAttributes()) {
                LuiExpenditureAttributeEntity attEntity = new LuiExpenditureAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }
    }

    public ExpenditureInfo toDto() {
        ExpenditureInfo obj = new ExpenditureInfo();
        obj.setId(this.getId());

        // Attributes
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiExpenditureAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        obj.setMeta(super.toDTO());

        return obj;

    }
    
    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    @Override
    public void setAttributes(Set<LuiExpenditureAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<LuiExpenditureAttributeEntity> getAttributes() {
        return attributes;
    }
}

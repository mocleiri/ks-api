package org.kuali.student.r2.core.classI.atp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.classI.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.classI.atp.dto.AtpInfo;
import org.kuali.student.r2.core.classI.atp.infc.Atp;
import org.kuali.student.r2.core.classI.atp.infc.AtpAtpRelation;

@Entity
@Table(name = "KSEN_ATPATP_RELTN")
public class AtpAtpRelationEntity extends MetaEntity implements AttributeOwner<AtpAtpRelationAttributeEntity>{
    @ManyToOne
    @JoinColumn(name="ATP_ID")
    private AtpEntity atpKey;
    
    @ManyToOne
    @JoinColumn(name="RELATED_ATP_ID")
    private AtpEntity relatedAtpKey;
    
    @ManyToOne
    @JoinColumn(name="ATP_RELTN_TYPE_ID")
    private AtpAtpRelationTypeEntity atpAtpRelationType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATP_STATE_ID")
    private AtpStateEntity atpState;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AtpAtpRelationAttributeEntity> attributes;

   public AtpAtpRelationEntity(){}
    
    public AtpAtpRelationEntity(AtpAtpRelation atpAtpRelation){
        this.setAtpKey(atpKey);
        this.setAtpAtpRelationType(atpAtpRelationType);
        this.setAtpState(atpState);
        this.setRelatedAtpKey(relatedAtpKey);
        this.setEffectiveDate(effectiveDate);
        this.setExpirationDate(expirationDate);
        
        this.setAttributes(new ArrayList<AtpAtpRelationAttributeEntity>());
        if (null != atpAtpRelation.getAttributes()) {
            for (Attribute att : atpAtpRelation.getAttributes()) {
                this.getAttributes().add(new AtpAtpRelationAttributeEntity(att));
            }
        }
    }
    
    public AtpEntity getAtpKey() {
        return atpKey;
    }

    public void setAtpKey(AtpEntity atpKey) {
        this.atpKey = atpKey;
    }

    public AtpEntity getRelatedAtpKey() {
        return relatedAtpKey;
    }

    public void setRelatedAtpKey(AtpEntity relatedAtpKey) {
        this.relatedAtpKey = relatedAtpKey;
    }

    public AtpAtpRelationTypeEntity getAtpAtpRelationType() {
        return atpAtpRelationType;
    }

    public void setAtpAtpRelationType(AtpAtpRelationTypeEntity atpAtpRelationType) {
        this.atpAtpRelationType = atpAtpRelationType;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    public AtpStateEntity getAtpState() {
        return atpState;
    }

    public void setAtpState(AtpStateEntity atpState) {
        this.atpState = atpState;
    }

    @Override
    public void setAttributes(List<AtpAtpRelationAttributeEntity> attributes) {
        this.attributes = attributes;       
    }

    @Override
    public List<AtpAtpRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public AtpAtpRelationInfo toDto() {
        AtpAtpRelationInfo.Builder builder = new AtpAtpRelationInfo.Builder();
        builder.setAtpKey(atpKey.getId());
        builder.setRelatedAtpKey(relatedAtpKey.getId());
        builder.setEffectiveDate(effectiveDate);
        builder.setExpirationDate(expirationDate);
        builder.setStateKey(atpState.getId());
        builder.setTypeKey(atpAtpRelationType.getId());
        builder.setMetaInfo(super.toDTO());
        
        List<Attribute> atts = new ArrayList<Attribute>();
        for (AtpAtpRelationAttributeEntity att : getAttributes()) {
            Attribute attInfo = att.toDto();
            atts.add(attInfo);
        }
        builder.setAttributes(atts);
        
        AtpAtpRelationInfo info = builder.build();
        return info;
    }
}

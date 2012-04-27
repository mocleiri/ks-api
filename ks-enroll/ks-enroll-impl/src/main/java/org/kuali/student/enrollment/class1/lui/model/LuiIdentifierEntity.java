package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_IDENT")
public class LuiIdentifierEntity extends MetaEntity {

    @Column(name = "LUI_CD")
    private String code;
    @Column(name = "SHRT_NAME")
    private String shortName;
    @Column(name = "LNG_NAME")
    private String longName;
    @Column(name = "DIVISION")
    private String division;
    @Column(name = "VARTN")
    private String variation;
    @Column(name = "SUFX_CD")
    private String suffixCode;
    @Column(name = "LUI_ID_TYPE")
    private String type;
    @Column(name = "LUI_ID_STATE")
    private String state;
    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiIdentifierAttributeEntity> attributes;

    public LuiIdentifierEntity() {
    }

    public LuiIdentifierEntity(LuiIdentifier luiIdentifier) {
        super(luiIdentifier);
        this.setId(luiIdentifier.getId());
        this.setState(luiIdentifier.getStateKey());
        this.setType(luiIdentifier.getTypeKey());
        fromDto(luiIdentifier);
    }

    public void fromDto(LuiIdentifier luiIdentifier) {
        this.setCode(luiIdentifier.getCode());
        this.setDivision(luiIdentifier.getDivision());
        this.setLongName(luiIdentifier.getLongName());
        this.setShortName(luiIdentifier.getShortName());
        this.setSuffixCode(luiIdentifier.getSuffixCode());
        this.setVariation(luiIdentifier.getVariation());
        this.setAttributes(new ArrayList<LuiIdentifierAttributeEntity>());
        for (Attribute att : luiIdentifier.getAttributes()) {
            LuiIdentifierAttributeEntity attEntity = new LuiIdentifierAttributeEntity(att);
            this.getAttributes().add(attEntity);
        }
    }

    public LuiIdentifierInfo toDto() {
        LuiIdentifierInfo info = new LuiIdentifierInfo();
        info.setId(getId());
        info.setCode(code);
        info.setDivision(division);
        info.setLongName(longName);
        info.setShortName(shortName);
        info.setStateKey(state);
        info.setSuffixCode(suffixCode);
        info.setTypeKey(type);
        info.setVariation(variation);
        info.setMeta(super.toDTO());

        for (LuiIdentifierAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }
        return info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getSuffixCode() {
        return suffixCode;
    }

    public void setSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAttributes(List<LuiIdentifierAttributeEntity> attributes) {
        this.attributes = attributes;

    }

    public List<LuiIdentifierAttributeEntity> getAttributes() {

        if(this.attributes!= null)
                 return attributes;

        return new ArrayList<LuiIdentifierAttributeEntity>() ;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }
}

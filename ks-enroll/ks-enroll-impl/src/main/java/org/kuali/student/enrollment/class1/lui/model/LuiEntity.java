package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.MeetingSchedule;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.infc.Fee;
import org.kuali.student.r2.lum.clu.infc.LuCode;
import org.kuali.student.r2.lum.clu.infc.Revenue;

@Entity
@Table(name = "KSEN_LUI")
public class LuiEntity extends MetaEntity implements AttributeOwner<LuiAttributeEntity> {
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "LUI_TYPE")
    private String luiType;

    @Column(name = "LUI_STATE")
    private String luiState;

    @Column(name = "CLU_ID")
    private String cluId;

    @Column(name = "ATP_ID")
    private String atpId;

    @Column(name = "REF_URL")
    private String referenceURL;

    @Column(name = "MAX_SEATS")
    private Integer maxSeats;

    @Column(name = "MIN_SEATS")
    private Integer minSeats;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXP_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuCodeEntity> luCodes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiRevenueEntity> luiRevenues;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiExpenditureEntity> luiExpenditures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiFeeEntity> luiFees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiIdentifierEntity> identifiers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<MeetingScheduleEntity> meetingSchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiCluCluRelationEntity> cluCluReltns;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsDeploymentEntity> unitsDeployments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiUnitsContentOwnerEntity> unitsContentOwners;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
    private List<LuiResultValuesGroupEntity> resultValuesGroupRelationEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiAttributeEntity> attributes;

    public LuiEntity() {}

    public LuiEntity(Lui lui) {
        super(lui);
        try {
            this.setId(lui.getId());
            this.setName(lui.getName());
            this.setAtpId(lui.getAtpId());
            this.setCluId(lui.getCluId());
            this.setMaxSeats(lui.getMaximumEnrollment());
            this.setMinSeats(lui.getMinimumEnrollment());
            this.setReferenceURL(lui.getReferenceURL());
            this.setLuiState(lui.getStateKey());
            this.setLuiType(lui.getTypeKey());
            if (lui.getEffectiveDate() != null)
                this.setEffectiveDate(lui.getEffectiveDate());
            if (lui.getExpirationDate() != null)
                this.setExpirationDate(lui.getExpirationDate());

            if (lui.getDescr() != null) {
                RichText rt = lui.getDescr();
                this.setDescrFormatted(rt.getFormatted());
                this.setDescrPlain(rt.getPlain());
            }

            // Lui Identifiers
            this.setIdentifiers(new ArrayList<LuiIdentifierEntity>());
            if (lui.getOfficialIdentifier() != null)
                this.getIdentifiers().add(new LuiIdentifierEntity(lui.getOfficialIdentifier()));
            if (lui.getAlternateIdentifiers() != null) {
                for (LuiIdentifier identifier : lui.getAlternateIdentifiers()) {
                    this.getIdentifiers().add(new LuiIdentifierEntity(identifier));
                }
            }

            // Lu Codes
            this.setLuCodes(new ArrayList<LuCodeEntity>());
            if (null != lui.getLuiCodes()) {
                for (LuCode luCode : lui.getLuiCodes()) {
                    this.getLuCodes().add(new LuCodeEntity(luCode));
                }
            }

            // Meeting Schedules
            this.setMeetingSchedules(new ArrayList<MeetingScheduleEntity>());
            if (null != lui.getMeetingSchedules()) {
                for (MeetingSchedule ms : lui.getMeetingSchedules()) {
                    this.getMeetingSchedules().add(new MeetingScheduleEntity(ms));
                }
            }

            // Lui Fees
            this.setLuiFees(new ArrayList<LuiFeeEntity>());
            if (null != lui.getFees()) {
                for (Fee fee : lui.getFees()) {
                    this.getLuiFees().add(new LuiFeeEntity(fee));
                }
            }

            // Lui Expenditures
            this.setLuiExpenditures(new ArrayList<LuiExpenditureEntity>());
            if (null != lui.getExpenditure()) {
                this.getLuiExpenditures().add(new LuiExpenditureEntity(lui.getExpenditure()));
            }

            // Lui Revenues
            this.setLuiRevenues(new ArrayList<LuiRevenueEntity>());
            if (null != lui.getFees()) {
                for (Revenue revenue : lui.getRevenues()) {
                    this.getLuiRevenues().add(new LuiRevenueEntity(revenue));
                }
            }

            this.setResultValuesGroupRelationEntities(new ArrayList<LuiResultValuesGroupEntity>());
            this.setUnitsDeployments(new ArrayList<LuiUnitsDeploymentEntity>());
            this.setUnitsContentOwners(new ArrayList<LuiUnitsContentOwnerEntity>());
            this.setCluCluReltns(new ArrayList<LuiCluCluRelationEntity>());

            // Lui Attributes
            this.setAttributes(new ArrayList<LuiAttributeEntity>());
            if (null != lui.getAttributes()) {
                for (Attribute att : lui.getAttributes()) {
                    this.getAttributes().add(new LuiAttributeEntity(att));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LuiInfo toDto() {
        LuiInfo obj = new LuiInfo();
        obj.setId(getId());
        obj.setName(name);
        obj.setAtpId(atpId);
        obj.setCluId(cluId);

        if (maxSeats != null)
            obj.setMaximumEnrollment(maxSeats);
        if (minSeats != null)
            obj.setMinimumEnrollment(minSeats);
        obj.setEffectiveDate(effectiveDate);
        obj.setExpirationDate(expirationDate);
        obj.setReferenceURL(referenceURL);
        
        if (luiType != null)
            obj.setTypeKey(luiType);
        if (luiState != null)
            obj.setStateKey(luiState);
        obj.setMeta(super.toDTO());
        if (getDescrPlain() != null) {
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getDescrPlain());
            rti.setFormatted(getDescrFormatted());
            obj.setDescr(rti);
        }

        // Identifiers
        for (LuiIdentifierEntity identifier : identifiers) {
            if (LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY.equals(identifier.getType())) {
                obj.setOfficialIdentifier(identifier.toDto());
            } else {
                obj.getAlternateIdentifiers().add(identifier.toDto());
            }
        }
        
        // Lu Codes
        for (LuCodeEntity luCode : this.getLuCodes()){
            obj.getLuiCodes().add(luCode.toDto());
        }

        // Meeting Schedules
        for (MeetingScheduleEntity ms : meetingSchedules) {
            obj.getMeetingSchedules().add(ms.toDto());
        }

        // Expenditures
        if (null != this.getLuiExpenditures()) {
            for (LuiExpenditureEntity luiExpenditure : this.getLuiExpenditures()) {
                obj.setExpenditure(luiExpenditure.toDto());
                break;
            }
        }

        // Fees
        if (null != this.getLuiFees()) {
            for (LuiFeeEntity luiFee : this.getLuiFees()) {
                obj.getFees().add(luiFee.toDto());
            }
        }
        
        // Revenues
        if (null != this.getLuiRevenues()) {
            for (LuiRevenueEntity luiRevenue : this.getLuiRevenues()) {
                obj.getRevenues().add(luiRevenue.toDto());
            }
        }

        // CluClu Relations
        if (null != this.getCluCluReltns()) {
            for (LuiCluCluRelationEntity luCluCluRelation : this.getCluCluReltns()) {
                obj.getCluCluRelationIds().add(luCluCluRelation.getClucluRelationId());
            }
        }

        // Units Deployments
        if (null != this.getUnitsDeployments()) {
            for (LuiUnitsDeploymentEntity unitDeployment : this.getUnitsDeployments()) {
                obj.getUnitsDeployment().add(unitDeployment.getOrgId());
            }
        }

        // Units Content Owners
        if (null != this.getUnitsContentOwners()) {
            for (LuiUnitsContentOwnerEntity unitContentOwner : this.getUnitsContentOwners()) {
                obj.getUnitsContentOwner().add(unitContentOwner.getOrgId());
            }
        }

        // Result Values Group Relations
        if (null != getResultValuesGroupRelationEntities()) {
            for (LuiResultValuesGroupEntity relationEntity : getResultValuesGroupRelationEntities()) {
                obj.getResultValuesGroupKeys().add(relationEntity.getResultValuesGroupKey());
            }
        }

        // Attributes
        for (LuiAttributeEntity att : getAttributes()) {
            obj.getAttributes().add(att.toDto());
        }
        
        return obj;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getMinSeats() {
        return minSeats;
    }

    public void setMinSeats(Integer minSeats) {
        this.minSeats = minSeats;
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

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
    }

    public String getLuiType() {
        return luiType;
    }

    public void setLuiType(String luiType) {
        this.luiType = luiType;
    }

    public String getLuiState() {
        return luiState;
    }

    public void setLuiState(String luiState) {
        this.luiState = luiState;
    }

    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    // public boolean isHasWaitlist() {
    // return hasWaitlist;
    // }
    //
    // public void setHasWaitlist(boolean hasWaitlist) {
    // this.hasWaitlist = hasWaitlist;
    // }
    //
    // public boolean isWaitlistCheckinRequired() {
    // return isWaitlistCheckinRequired;
    // }
    //
    // public void setWaitlistCheckinRequired(boolean isWaitlistCheckinRequired) {
    // this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
    // }
    //
    // public Integer getWaitlistMaximum() {
    // return waitlistMaximum;
    // }
    //
    // public void setWaitlistMaximum(Integer waitlistMaximum) {
    // this.waitlistMaximum = waitlistMaximum;
    // }

    @Override
    public void setAttributes(List<LuiAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<LuCodeEntity> getLuCodes() {
        return luCodes;
    }

    public void setLuCodes(List<LuCodeEntity> luCodes) {
        this.luCodes = luCodes;
    }

    public List<LuiIdentifierEntity> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<LuiIdentifierEntity> identifiers) {
        this.identifiers = identifiers;
    }

    public List<LuiUnitsContentOwnerEntity> getUnitsContentOwners() {
        return unitsContentOwners;
    }

    public void setUnitsContentOwners(List<LuiUnitsContentOwnerEntity> unitsContentOwners) {
        this.unitsContentOwners = unitsContentOwners;
    }

    @Override
    public List<LuiAttributeEntity> getAttributes() {
        return attributes;
    }

    public List<MeetingScheduleEntity> getMeetingSchedules() {
        return meetingSchedules;
    }

    public void setMeetingSchedules(List<MeetingScheduleEntity> meetingSchedules) {
        this.meetingSchedules = meetingSchedules;
    }

    public List<LuiCluCluRelationEntity> getCluCluReltns() {
        return cluCluReltns;
    }

    public void setCluCluReltns(List<LuiCluCluRelationEntity> cluCluReltns) {
        this.cluCluReltns = cluCluReltns;
    }

    public List<LuiUnitsDeploymentEntity> getUnitsDeployments() {
        return unitsDeployments;
    }

    public void setUnitsDeployments(List<LuiUnitsDeploymentEntity> unitsDeployments) {
        this.unitsDeployments = unitsDeployments;
    }

    public List<LuiResultValuesGroupEntity> getResultValuesGroupRelationEntities() {
        return resultValuesGroupRelationEntities;
    }

    public void setResultValuesGroupRelationEntities(List<LuiResultValuesGroupEntity> resultValuesGroupRelationEntities) {
        this.resultValuesGroupRelationEntities = resultValuesGroupRelationEntities;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public List<LuiRevenueEntity> getLuiRevenues() {
        return luiRevenues;
    }

    public void setLuiRevenues(List<LuiRevenueEntity> luiRevenues) {
        this.luiRevenues = luiRevenues;
    }

    public List<LuiExpenditureEntity> getLuiExpenditures() {
        return luiExpenditures;
    }

    public void setLuiExpenditures(List<LuiExpenditureEntity> luiExpenditures) {
        this.luiExpenditures = luiExpenditures;
    }

    public List<LuiFeeEntity> getLuiFees() {
        return luiFees;
    }

    public void setLuiFees(List<LuiFeeEntity> luiFees) {
        this.luiFees = luiFees;
    }

    /*
     * public List<LuiCluRelationEntity> getCluCluRelationIds() { return cluCluRelationIds; } public void
     * setCluCluRelationIds(List<LuiCluRelationEntity> cluCluRelationIds) { this.cluCluRelationIds = cluCluRelationIds; }
     */

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.kim.api.identity.CodedAttribute;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.address.EntityAddress;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliation;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliationType;
import org.kuali.rice.kim.api.identity.citizenship.EntityCitizenship;
import org.kuali.rice.kim.api.identity.email.EntityEmail;
import org.kuali.rice.kim.api.identity.employment.EntityEmployment;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.entity.EntityQueryResults;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifier;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifierType;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.personal.EntityBioDemographics;
import org.kuali.rice.kim.api.identity.personal.EntityEthnicity;
import org.kuali.rice.kim.api.identity.phone.EntityPhone;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.identity.privacy.EntityPrivacyPreferences;
import org.kuali.rice.kim.api.identity.residency.EntityResidency;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.kim.api.identity.visa.EntityVisa;

/**
 *
 * @author nwright
 */
public class IdentityServiceDecorator implements IdentityService {
    
    private IdentityService nextDecorator;

    public IdentityService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(IdentityService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public EntityVisa updateVisa(EntityVisa ev) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateVisa(ev);
    }

    @Override
    public EntityResidency updateResidency(EntityResidency er) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateResidency(er);
    }

    @Override
    public EntityPrivacyPreferences updatePrivacyPreferences(EntityPrivacyPreferences epp) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updatePrivacyPreferences(epp);
    }

    @Override
    public Principal updatePrincipal(Principal prncpl) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updatePrincipal(prncpl);
    }

    @Override
    public EntityPhone updatePhone(EntityPhone ep) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updatePhone(ep);
    }

    @Override
    public EntityName updateName(EntityName en) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateName(en);
    }

    @Override
    public EntityExternalIdentifier updateExternalIdentifier(EntityExternalIdentifier eei) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateExternalIdentifier(eei);
    }

    @Override
    public EntityEthnicity updateEthnicity(EntityEthnicity ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateEthnicity(ee);
    }

    @Override
    public EntityTypeContactInfo updateEntityTypeContactInfo(EntityTypeContactInfo etci) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateEntityTypeContactInfo(etci);
    }

    @Override
    public Entity updateEntity(Entity entity) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateEntity(entity);
    }

    @Override
    public EntityEmployment updateEmployment(EntityEmployment ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateEmployment(ee);
    }

    @Override
    public EntityEmail updateEmail(EntityEmail ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateEmail(ee);
    }

    @Override
    public EntityCitizenship updateCitizenship(EntityCitizenship ec) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateCitizenship(ec);
    }

    @Override
    public EntityBioDemographics updateBioDemographics(EntityBioDemographics ebd) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateBioDemographics(ebd);
    }

    @Override
    public EntityAffiliation updateAffiliation(EntityAffiliation ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateAffiliation(ea);
    }

    @Override
    public EntityAddress updateAddress(EntityAddress ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.updateAddress(ea);
    }

    @Override
    public Principal inactivatePrincipalByName(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivatePrincipalByName(string);
    }

    @Override
    public Principal inactivatePrincipal(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivatePrincipal(string);
    }

    @Override
    public EntityPhone inactivatePhone(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivatePhone(string);
    }

    @Override
    public EntityName inactivateName(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateName(string);
    }

    @Override
    public EntityTypeContactInfo inactivateEntityTypeContactInfo(String string, String string1) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateEntityTypeContactInfo(string, string1);
    }

    @Override
    public Entity inactivateEntity(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateEntity(string);
    }

    @Override
    public EntityEmployment inactivateEmployment(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateEmployment(string);
    }

    @Override
    public EntityEmail inactivateEmail(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateEmail(string);
    }

    @Override
    public EntityCitizenship inactivateCitizenship(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateCitizenship(string);
    }

    @Override
    public EntityAffiliation inactivateAffiliation(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateAffiliation(string);
    }

    @Override
    public EntityAddress inactivateAddress(String string) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.inactivateAddress(string);
    }

    @Override
    public Principal getPrincipalByPrincipalNameAndPassword(String string, String string1) throws RiceIllegalArgumentException {
        return nextDecorator.getPrincipalByPrincipalNameAndPassword(string, string1);
    }

    @Override
    public Principal getPrincipalByPrincipalName(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getPrincipalByPrincipalName(string);
    }

    @Override
    public Principal getPrincipal(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getPrincipal(string);
    }

    @Override
    public CodedAttribute getPhoneType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getPhoneType(string);
    }

    @Override
    public CodedAttribute getNameType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getNameType(string);
    }

    @Override
    public EntityExternalIdentifierType getExternalIdentifierType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getExternalIdentifierType(string);
    }

    @Override
    public CodedAttribute getEntityType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityType(string);
    }

    @Override
    public EntityPrivacyPreferences getEntityPrivacyPreferences(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityPrivacyPreferences(string);
    }

    @Override
    public EntityDefault getEntityDefaultByPrincipalName(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityDefaultByPrincipalName(string);
    }

    @Override
    public EntityDefault getEntityDefaultByPrincipalId(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityDefaultByPrincipalId(string);
    }

    @Override
    public EntityDefault getEntityDefault(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityDefault(string);
    }

    @Override
    public Entity getEntityByPrincipalName(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityByPrincipalName(string);
    }

    @Override
    public Entity getEntityByPrincipalId(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntityByPrincipalId(string);
    }

    @Override
    public Entity getEntity(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEntity(string);
    }

    @Override
    public CodedAttribute getEmploymentType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEmploymentType(string);
    }

    @Override
    public CodedAttribute getEmploymentStatus(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEmploymentStatus(string);
    }

    @Override
    public CodedAttribute getEmailType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getEmailType(string);
    }

    @Override
    public CodedAttribute getCitizenshipStatus(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getCitizenshipStatus(string);
    }

    @Override
    public EntityAffiliationType getAffiliationType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getAffiliationType(string);
    }

    @Override
    public CodedAttribute getAddressType(String string) throws RiceIllegalArgumentException {
        return nextDecorator.getAddressType(string);
    }

    @Override
    public EntityDefaultQueryResults findEntityDefaults(QueryByCriteria qbc) throws RiceIllegalArgumentException {
        return nextDecorator.findEntityDefaults(qbc);
    }

    @Override
    public EntityQueryResults findEntities(QueryByCriteria qbc) throws RiceIllegalArgumentException {
        return nextDecorator.findEntities(qbc);
    }

    @Override
    public Entity createEntity(Entity entity) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.createEntity(entity);
    }

    @Override
    public EntityVisa addVisaToEntity(EntityVisa ev) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addVisaToEntity(ev);
    }

    @Override
    public EntityResidency addResidencyToEntity(EntityResidency er) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addResidencyToEntity(er);
    }

    @Override
    public EntityPrivacyPreferences addPrivacyPreferencesToEntity(EntityPrivacyPreferences epp) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addPrivacyPreferencesToEntity(epp);
    }

    @Override
    public Principal addPrincipalToEntity(Principal prncpl) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addPrincipalToEntity(prncpl);
    }

    @Override
    public EntityPhone addPhoneToEntity(EntityPhone ep) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addPhoneToEntity(ep);
    }

    @Override
    public EntityName addNameToEntity(EntityName en) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addNameToEntity(en);
    }

    @Override
    public EntityExternalIdentifier addExternalIdentifierToEntity(EntityExternalIdentifier eei) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addExternalIdentifierToEntity(eei);
    }

    @Override
    public EntityEthnicity addEthnicityToEntity(EntityEthnicity ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addEthnicityToEntity(ee);
    }

    @Override
    public EntityTypeContactInfo addEntityTypeContactInfoToEntity(EntityTypeContactInfo etci) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addEntityTypeContactInfoToEntity(etci);
    }

    @Override
    public EntityEmployment addEmploymentToEntity(EntityEmployment ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addEmploymentToEntity(ee);
    }

    @Override
    public EntityEmail addEmailToEntity(EntityEmail ee) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addEmailToEntity(ee);
    }

    @Override
    public EntityCitizenship addCitizenshipToEntity(EntityCitizenship ec) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addCitizenshipToEntity(ec);
    }

    @Override
    public EntityBioDemographics addBioDemographicsToEntity(EntityBioDemographics ebd) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addBioDemographicsToEntity(ebd);
    }

    @Override
    public EntityAffiliation addAffiliationToEntity(EntityAffiliation ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addAffiliationToEntity(ea);
    }

    @Override
    public EntityAddress addAddressToEntity(EntityAddress ea) throws RiceIllegalArgumentException, RiceIllegalStateException {
        return nextDecorator.addAddressToEntity(ea);
    }
    
    
    
}

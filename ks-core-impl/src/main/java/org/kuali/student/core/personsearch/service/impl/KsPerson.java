package org.kuali.student.core.personsearch.service.impl;

import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.entity.KimEntityName;
import org.kuali.rice.kim.bo.entity.KimPrincipal;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 *
 * Real ugly hack to get around the problem with PersonImpl being in rice-impl and we
 * are not allowed to use the results from the IdentityService directly
 *
 *
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */

//FIXME Either find a cleaner fix or go back to using PersonSerice
public class KsPerson implements Person {

    private String principalId;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;

    public KsPerson(KimEntityDefaultInfo entity, KimPrincipal principal) {
        this.principalId = principal.getPrincipalId();
        populateNameInfo(entity, principal);
    }

    @Override
    public String getAddressCityName() {

        return null;
    }

    @Override
    public String getAddressCityNameUnmasked() {

        return null;
    }

    @Override
    public String getAddressCountryCode() {

        return null;
    }

    @Override
    public String getAddressCountryCodeUnmasked() {

        return null;
    }

    @Override
    public String getAddressLine1() {

        return null;
    }

    @Override
    public String getAddressLine1Unmasked() {

        return null;
    }

    @Override
    public String getAddressLine2() {

        return null;
    }

    @Override
    public String getAddressLine2Unmasked() {

        return null;
    }

    @Override
    public String getAddressLine3() {

        return null;
    }

    @Override
    public String getAddressLine3Unmasked() {

        return null;
    }

    @Override
    public String getAddressPostalCode() {

        return null;
    }

    @Override
    public String getAddressPostalCodeUnmasked() {

        return null;
    }

    @Override
    public String getAddressStateCode() {

        return null;
    }

    @Override
    public String getAddressStateCodeUnmasked() {

        return null;
    }

    @Override
    public KualiDecimal getBaseSalaryAmount() {

        return null;
    }

    @Override
    public String getCampusCode() {

        return null;
    }

    @Override
    public List<String> getCampusCodesForAffiliationOfType(String paramString) {

        return null;
    }

    @Override
    public String getEmailAddress() {

        return null;
    }

    @Override
    public String getEmailAddressUnmasked() {

        return null;
    }

    @Override
    public String getEmployeeId() {

        return null;
    }

    @Override
    public String getEmployeeStatusCode() {

        return null;
    }

    @Override
    public String getEmployeeTypeCode() {

        return null;
    }

    @Override
    public String getEntityId() {

        return null;
    }

    @Override
    public String getEntityTypeCode() {

        return null;
    }

    @Override
    public String getExternalId(String paramString) {

        return null;
    }

    @Override
    public Map<String, String> getExternalIdentifiers() {

        return null;
    }

    @Override
    public String getFirstName() {

        return null;
    }

    @Override
    public String getFirstNameUnmasked() {

        return null;
    }

    @Override
    public String getLastName() {

        return null;
    }

    @Override
    public String getLastNameUnmasked() {

        return null;
    }

    @Override
    public String getMiddleName() {

        return null;
    }

    @Override
    public String getMiddleNameUnmasked() {

        return null;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public String getNameUnmasked() {

        return null;
    }

    @Override
    public String getPhoneNumber() {

        return null;
    }

    @Override
    public String getPhoneNumberUnmasked() {

        return null;
    }

    @Override
    public String getPrimaryDepartmentCode() {

        return null;
    }

    @Override
    public String getPrincipalId() {

        return principalId;
    }

    @Override
    public String getPrincipalName() {

        return null;
    }

    @Override
    public boolean hasAffiliationOfType(String paramString) {

        return false;
    }

    @Override
    public boolean isActive() {

        return false;
    }

    @Override
    public void prepareForWorkflow() {

    }

    @Override
    public void refresh() {

    }

    protected void populateNameInfo(KimEntityDefaultInfo entity, KimPrincipal principal) {
        if (entity != null) {
            KimEntityName entityName = entity.getDefaultName();
            if (entityName != null) {
                firstName = unNullify(entityName.getFirstNameUnmasked());
                middleName = unNullify(entityName.getMiddleNameUnmasked());
                lastName = unNullify(entityName.getLastNameUnmasked());
                name = unNullify(entityName.getFormattedNameUnmasked());
            } else {
                firstName = "";
                middleName = "";
                name = "";
                lastName = "";
            }
        }
    }
    /** So users of this class don't need to program around nulls. */
    private String unNullify( String str ) {
        if ( str == null ) {
            return "";
        }
        return str;
    }


}

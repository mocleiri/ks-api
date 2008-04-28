package org.kuali.student.poc.personidentity.person.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.kuali.student.poc.common.util.EhCacheHelper;
import org.kuali.student.poc.personidentity.person.dao.Person;
import org.kuali.student.poc.personidentity.person.dao.PersonAttribute;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeSetType;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeType;
import org.kuali.student.poc.personidentity.person.dao.PersonCitizenship;
import org.kuali.student.poc.personidentity.person.dao.PersonDAO;
import org.kuali.student.poc.personidentity.person.dao.PersonName;
import org.kuali.student.poc.personidentity.person.dao.PersonReferenceId;
import org.kuali.student.poc.personidentity.person.dao.PersonType;
import org.kuali.student.poc.personidentity.person.dao.PersonalInformation;
import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.CircularReferenceException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonReferenceIdInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.ValidationError;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface="org.kuali.student.poc.wsdl.personidentity.person.PersonService",
		serviceName="PersonService",
		portName="PersonService",
		targetNamespace="http://student.kuali.org/poc/wsdl/personidentity/person")
@Transactional
public class PersonServiceImpl implements PersonService {

	@Override
    public List<PersonInfo> findPeopleByPersonIds(List<String> personIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<PersonDisplay> findPeopleDisplayByPersonIds(List<String> personIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private PersonDAO personDAO;
	private EhCacheHelper ehCacheHelper;
	private final String personInfoCacheName = "PersonInfo";

	public PersonServiceImpl() {
		ehCacheHelper = new EhCacheHelper();		
		ehCacheHelper.createCache(personInfoCacheName);
	}
	/**
	 * @return the personDAO
	 */
	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	/**
	 * @param personDAO
	 *            the personDAO to set
	 */
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	
	private boolean isNullOrBlank(String id) {
		return id==null||"".equals(id);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#assignPersonType(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean assignPersonType(String personId, String personTypeKey)
			throws AlreadyExistsException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		PersonType personType = personDAO.fetchPersonType(personTypeKey);
		Person person = personDAO.lookupPerson(personId);
		
		//personType.getPeople().add(person);
		person.getPersonTypes().add(personType);
		personDAO.updatePerson(person);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createAttributeDefinition(org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo)
	 */
	@Override
	public String createAttributeDefinition(
			PersonAttributeTypeInfo attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		PersonAttributeType perAttrTyp = toPersonAttributeType(attributeDefinition, false);
		return personDAO.createPersonAttributeType(perAttrTyp).getId();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPerson(org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo, java.util.List)
	 */
	@Override
	public String createPerson(PersonCreateInfo personCreateInfo,
			List<String> personTypeKeys) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		//create a JPA entity
		Person person = new Person();
		
		// First look up the personTypes
		Set<PersonType> personTypeSet = new HashSet<PersonType>();
		Set<PersonAttributeType> attributeTypeSet = new HashSet<PersonAttributeType>();

		//Find all the types we are adding to the person to be created
		for(String personTypeKey:personTypeKeys){	
		
			//Look up the person types
			PersonType personType =  personDAO.fetchPersonType(personTypeKey);
			
			//Add them to the list of person types
			personTypeSet.add(personType);
			
			//find all the attributes in the person type and add them to our set
			for(PersonAttributeSetType personAttributeSetType:personType.getPersonAttributeSetTypes()){
				for(PersonAttributeType personAttributeType:personAttributeSetType.getPersonAttributeTypes()){
					attributeTypeSet.add(personAttributeType);
				}
			}
		}
		//Add all the attribute types
		person.getPersonTypes().addAll(personTypeSet);
		
		//Add all of the attributes that were in the personTypes and exist on the person
		for(PersonAttributeType personAttributeType:attributeTypeSet){
		
			//Check that the person being passed in actually has this attribute
			if(personCreateInfo.getAttribute(personAttributeType.getName())!=null){
			
				//Create a new attribute
				PersonAttribute personAttribute = new PersonAttribute();
				personAttribute.setPerson(person);
				personAttribute.setPersonAttributeType(personAttributeType);
				personAttribute.setValue(personCreateInfo.getAttribute(personAttributeType.getName()));
				
				//Add the attribute to the person
				person.getAttributes().add(personAttribute);
			}
			
		}
		//Copy the standard person fields
		PersonalInformation personalInformation = new PersonalInformation();
		personalInformation.setGender(personCreateInfo.getGender());
		personalInformation.setDateOfBirth(personCreateInfo.getBirthDate());
		personalInformation.setPerson(person);
		person.setPersonalInformation(personalInformation);
		
		PersonCitizenshipInfo citizenshipInfo = personCreateInfo.getCitizenship();
		if(citizenshipInfo!=null){
			PersonCitizenship citizenship = new PersonCitizenship();
			citizenship.setCountryOfCitizenship(citizenshipInfo.getCountryOfCitizenshipCode());
			citizenship.setEffectiveStartDate(citizenshipInfo.getEffectiveStartDate());
			citizenship.setEffectiveEndDate(citizenshipInfo.getEffectiveEndDate());
			citizenship.setPerson(person);
			Set<PersonCitizenship> personCitizenship = new HashSet<PersonCitizenship>();
			personCitizenship.add(citizenship);
			person.setPersonCitizenships(personCitizenship);
		}	
		
		for(PersonNameInfo nameInfo : personCreateInfo.getName()){
			PersonName personName = new PersonName();
			personName.setEffectiveEndDate(nameInfo.getEffectiveEndDate());
			personName.setEffectiveStartDate(nameInfo.getEffectiveStartDate());
			personName.setGivenName(nameInfo.getGivenName());
			personName.setMiddleNames(nameInfo.getMiddleName());
			personName.setNameType(nameInfo.getNameType());
			personName.setSuffix(nameInfo.getSuffix());
			personName.setSurname(nameInfo.getSurname());
			personName.setTitle(nameInfo.getPersonTitle());
			personName.setPerson(person);
			person.getPersonNames().add(personName);
		}
		for(PersonReferenceIdInfo personReferenceIdInfo:personCreateInfo.getReferenceId()){
			PersonReferenceId personReferenceId = new PersonReferenceId();
			personReferenceId.setOrganizationReferenceId(personReferenceIdInfo.getOrganizationReferenceId());
			personReferenceId.setReferenceId(personReferenceIdInfo.getReferenceId());
			personReferenceId.setRestrictedAccess(personReferenceIdInfo.isRestrictedAccess());
			personReferenceId.setUpdateTime(personReferenceIdInfo.getUpdateTime());
			personReferenceId.setUpdateUserComment(personReferenceIdInfo.getUpdateUserComment());
			personReferenceId.setUpdateUserId(personReferenceIdInfo.getUpdateUserId());
			personReferenceId.setPerson(person);
			person.getPersonReferenceIds().add(personReferenceId);
		}
		//Create the person
		Person created = personDAO.createPerson(person);
		ehCacheHelper.saveOrUpdateCacheElement(personInfoCacheName, created.getId().toString(), toPersonInfo(created));
		return created.getId();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPersonAttributeSetType(org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo)
	 */
	@Override
	public String createPersonAttributeSetType(
			PersonAttributeSetTypeInfo attributeSetDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		PersonAttributeSetType personAttributeSetType = this.toPersonAttributeSetType(attributeSetDTO, false);
		return personDAO.createPersonAttributeSetType(personAttributeSetType).getId();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPersonTypeInfo(org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo)
	 */
	@Override
	public String createPersonTypeInfo(PersonTypeInfo personTypeInfo)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		PersonType personType = toPersonType(personTypeInfo,false);
		PersonType created = personDAO.createPersonType(personType);
		return created.getId();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#deletePerson(java.lang.String)
	 */
	@Override
	public boolean deletePerson(String personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		Person person = personDAO.lookupPerson(personId);
		ehCacheHelper.evictCacheElement(personInfoCacheName, personId.toString());
		return personDAO.deletePerson(person);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchFullPersonInfo(java.lang.String)
	 */
	@Override
	public PersonInfo fetchFullPersonInfo(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		PersonInfo cachedPersonInfo = (PersonInfo)ehCacheHelper.getCacheElementValue(personInfoCacheName, personId.toString());
		if(cachedPersonInfo != null) {
			return cachedPersonInfo;
		}
		Person person = personDAO.lookupPerson(personId);
		return toPersonInfo(person);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonAttributeSetType(java.lang.String)
	 */
	@Override
	public PersonAttributeSetTypeInfo fetchPersonAttributeSetType(
			String personAttributeSetTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		PersonAttributeSetType personAttributeSetType = personDAO.fetchPersonAttributeSetType(personAttributeSetTypeKey);
		return toPersonAttributeSetTypeInfo(personAttributeSetType);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonInfoByPersonAttributeSetTypes(java.lang.String, java.util.List)
	 */
	@Override
	public PersonInfo fetchPersonInfoByPersonAttributeSetTypes(String personId,
			List<String> personAttributeSetTypeKeyList)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Person person = personDAO.lookupPerson(personId);
		Set<PersonAttribute> filteredAttributes = personDAO.fetchAttributesByPersonAttributeSetType(personId, personAttributeSetTypeKeyList);
		person.setAttributes(filteredAttributes);
		return toPersonInfo(person);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonInfoByPersonType(java.lang.String, java.lang.String)
	 */
	@Override
	public PersonInfo fetchPersonInfoByPersonType(String personId,
			String personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		Person person = personDAO.lookupPerson(personId);
		Set<PersonAttribute> filteredAttributes = personDAO.fetchAttributesByPersonType(personId, personTypeKey);
		person.setAttributes(filteredAttributes);
		return toPersonInfo(person);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonType(java.lang.String)
	 */
	@Override
	public PersonTypeInfo fetchPersonType(String personTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return this.toPersonTypeInfo(personDAO.fetchPersonType(personTypeKey));
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findCreatablePersonTypes()
	 */
	@Override
	public List<PersonTypeDisplay> findCreatablePersonTypes()
			throws OperationFailedException {
		return findPersonTypes();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonAttributeSetTypes()
	 */
	@Override
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes()
			throws OperationFailedException {

        List<PersonAttributeSetTypeDisplay> personAttrSetTypeDispList = 
               new ArrayList<PersonAttributeSetTypeDisplay>();
        List<PersonAttributeSetType> personAttrSetTypeList = personDAO.findPersonAttributeSetTypes("%");
        for(PersonAttributeSetType personAttrSetType : personAttrSetTypeList) {
            personAttrSetTypeDispList.add(toPersonAttributeSetTypeDisplay(personAttrSetType));
        }
        
        return personAttrSetTypeDispList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonAttributeSetTypesForPerson(java.lang.String)
	 */
	@Override
	public List<String> findPersonAttributeSetTypesForPerson(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    Set<String> personAttrSetTypeIds = new HashSet<String>();
	    
        Person person = personDAO.lookupPerson(personId);
        
        Set<PersonType> personTypes = person.getPersonTypes();
        for (PersonType personType : personTypes){
            Set<PersonAttributeSetType>  personAttrSetTypes = personType.getPersonAttributeSetTypes();
            for(PersonAttributeSetType personAttrSetType : personAttrSetTypes) {
                personAttrSetTypeIds.add(personAttrSetType.getId());
            }
        }

        return (new ArrayList<String>(personAttrSetTypeIds));
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonAttributeSetTypesForPersonType(java.lang.String)
	 */
	@Override
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypesForPersonType(
			String personTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

	    PersonType personType = personDAO.fetchPersonType(personTypeKey);
	    Set<PersonAttributeSetType> personAttrSetTypeSet = personType.getPersonAttributeSetTypes();
	    
	    List<PersonAttributeSetTypeDisplay> personAttrSetTypeDispList = 
	        new ArrayList<PersonAttributeSetTypeDisplay>();
	    for (PersonAttributeSetType personAttrSetType : personAttrSetTypeSet){
	        personAttrSetTypeDispList.add(toPersonAttributeSetTypeDisplay(personAttrSetType)); 
	    }
	    
	    return personAttrSetTypeDispList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonIdsForPersonType(java.lang.String, org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<String> findPersonIdsForPersonType(String personTypeKey,
			PersonCriteria personFilter) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        List<String> personIds = new ArrayList<String>();
	    if(personFilter == null) {
	        PersonType personType = personDAO.fetchPersonType(personTypeKey);
	        
	        Set<Person> people = personType.getPeople();
	        for (Person person : people) {
	            personIds.add(person.getId());
	        }
	    } else {
            List<Person> people = personDAO.findPeopleWithPersonType(personTypeKey, personFilter);
            for (Person person : people) {
                personIds.add(person.getId());
            }
	    }
        return personIds;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonTypes()
	 */
	@Override
	public List<PersonTypeDisplay> findPersonTypes()
			throws OperationFailedException {
		List<PersonTypeDisplay> personTypeInfoList = new ArrayList<PersonTypeDisplay>();
		List<PersonType> personTypeList = personDAO.findPersonTypes("%");
		for(PersonType personType : personTypeList)	{
			personTypeInfoList.add(toPersonTypeDisplay(personType));
		}
		
		return personTypeInfoList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonTypesForPerson(java.lang.String)
	 */
	@Override
	public List<String> findPersonTypesForPerson(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    Person person = personDAO.lookupPerson(personId);
	    if(person==null){
	    	throw new DoesNotExistException("Person with id '" + personId + "' does not exist.");
	    }
	    Set<PersonType> personTypeSet = person.getPersonTypes();
	    List<String> personTypeIdList = new ArrayList<String>();
	    for (PersonType personType:personTypeSet){
	        personTypeIdList.add(personType.getId());
	    }
	        
	    return personTypeIdList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#isPersonType(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPersonType(String personId, String personTypeKey)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    Person person = personDAO.lookupPerson(personId);

	    return person.getPersonTypes().contains(personTypeKey);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeopleByPersonType(java.lang.String, org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<PersonInfo> searchForPeopleByPersonType(String personTypeKey,
			PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        List<PersonInfo> personInfoList = new ArrayList<PersonInfo>();
	    if(personCriteria == null) {
	        PersonType personType = personDAO.fetchPersonType(personTypeKey);
	        
	        Set<Person> people = personType.getPeople();
	        for (Person person : people) {
	            personInfoList.add(toPersonInfo(person));            
	        }
	    } else {
            List<Person> people = personDAO.findPeopleWithPersonType(personTypeKey, personCriteria);
            for (Person person : people) {
                personInfoList.add(toPersonInfo(person));
            }
	    }
        return personInfoList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#updatePerson(java.lang.String, org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo)
	 */
	@Override
	public boolean updatePerson(String personId, PersonUpdateInfo personUpdateInfo)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			ReadOnlyException, OperationFailedException,
			PermissionDeniedException {
        // Find the JPA entity
        Person personJPA = personDAO.lookupPerson(personId);     
       
        // First look up the personTypes
        Set<PersonType> personTypeSetJPA = personJPA.getPersonTypes();
        Set<PersonAttributeType> attributeTypeSetJPA = new HashSet<PersonAttributeType>();

        //Find all the types that can exist on the person to be updated
        for(PersonType personTypeJPA: personTypeSetJPA) {
            for(PersonAttributeSetType personAttributeSetType:personTypeJPA.getPersonAttributeSetTypes()){
                for(PersonAttributeType personAttributeType:personAttributeSetType.getPersonAttributeTypes()){
                    attributeTypeSetJPA.add(personAttributeType);
                }
            }
        }
        
        //Add all of the attributes that were in the personTypes and exist on the person
        for(PersonAttributeType personAttributeType:attributeTypeSetJPA){
        
            //Check that the person being passed in actually has this attribute
            if(personUpdateInfo.getAttributes().containsKey(personAttributeType.getName())){
                String attributeValue = personUpdateInfo.getAttribute(personAttributeType.getName());
                
                //I hate writing lookups
                //Find the actual entry in the attribute set
                PersonAttribute attribute = null;
                Set<PersonAttribute> attributes = personJPA.getAttributes();
                for (PersonAttribute personAttribute : attributes) {
                    if(personAttribute.getPersonAttributeType().getId().equals(personAttributeType.getId())) {
                        attribute = personAttribute;
                        break;
                    }
                }
                // if value is null, that means we're removing it. that's why we did containsKey up there instead of getAttribute()
                if(attributeValue == null) {
                    attributes.remove(attribute);
                    personDAO.deletePersonAttribute(attribute);
                } else {
                    //The problem here is that jpa allows for multi-valued attrs, whereas the dto has a map with 1:1
                    attribute.setValue(attributeValue);
                }
            }
            
        }
       
        //Copy the standard person fields
        PersonalInformation personalInformation = personJPA.getPersonalInformation();
        personalInformation.setGender(personUpdateInfo.getGender());
        personalInformation.setDateOfBirth(personUpdateInfo.getBirthDate());
        
        //TODO: Need to handle multiple citizenship in DTO 
        PersonCitizenshipInfo citizenshipInfo = personUpdateInfo.getCitizenship();
        if(citizenshipInfo!=null){
            PersonCitizenship citizenship = new PersonCitizenship();
            citizenship.setCountryOfCitizenship(citizenshipInfo.getCountryOfCitizenshipCode());
            citizenship.setEffectiveStartDate(citizenshipInfo.getEffectiveStartDate());
            citizenship.setEffectiveEndDate(citizenshipInfo.getEffectiveEndDate());
            citizenship.setPerson(personJPA);
            Set<PersonCitizenship> personCitizenship = new HashSet<PersonCitizenship>();
            personCitizenship.add(citizenship);
            personJPA.setPersonCitizenships(personCitizenship);
        }
        
        //Update person names
        for (PersonNameInfo personNameInfo : personUpdateInfo.getName()) {

            //Find existing name
            PersonName name = null;
            Set<PersonName> personNameSet = personJPA.getPersonNames(); 
            for (PersonName personName: personNameSet) {
                if(personName.getNameType().equals(personNameInfo.getNameType())) {
                    name = personName;
                    break;
                }
            }
  
            //create new name if it doesn't exist
            if (name == null){
                name = new PersonName();
                name.setPerson(personJPA);
                personNameSet.add(name);
            }
            
            if (personNameInfo.getGivenName() == null){
                personNameSet.remove(name);
            } else {            
                name.setEffectiveEndDate(personNameInfo.getEffectiveEndDate());
                name.setEffectiveStartDate(personNameInfo.getEffectiveStartDate());
                name.setGivenName(personNameInfo.getGivenName());
                name.setMiddleNames(personNameInfo.getMiddleName());
                name.setNameType(personNameInfo.getNameType());
                name.setSuffix(personNameInfo.getSuffix());
                name.setSurname(personNameInfo.getSurname());
                name.setTitle(personNameInfo.getPersonTitle());
            }            
        }
        
        //Update reference ids
        //TODO: Fix so it doesn't do simply delete existing and replace with new
        personJPA.setPersonReferenceIds(new HashSet<PersonReferenceId>());
        for(PersonReferenceIdInfo personReferenceIdInfo:personUpdateInfo.getReferenceId()){
            PersonReferenceId personReferenceId = new PersonReferenceId();
            personReferenceId.setOrganizationReferenceId(personReferenceIdInfo.getOrganizationReferenceId());
            personReferenceId.setReferenceId(personReferenceIdInfo.getReferenceId());
            personReferenceId.setRestrictedAccess(personReferenceIdInfo.isRestrictedAccess());
            personReferenceId.setUpdateTime(personReferenceIdInfo.getUpdateTime());
            personReferenceId.setUpdateUserComment(personReferenceIdInfo.getUpdateUserComment());
            personReferenceId.setUpdateUserId(personReferenceIdInfo.getUpdateUserId());
            personReferenceId.setPerson(personJPA);
            personJPA.getPersonReferenceIds().add(personReferenceId);
        }
        

        //Create the person        
        Person updatedPerson = personDAO.updatePerson(personJPA);
        ehCacheHelper.saveOrUpdateCacheElement(personInfoCacheName, updatedPerson.getId().toString(), toPersonInfo(updatedPerson));
        return true;    
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#validatePersonInfoForPersonType(java.lang.String, java.lang.String)
	 */
	@Override
	public ValidationError validatePersonInfoForPersonType(String personId,
			String personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchReplacementPersonId(java.lang.String)
	 */
	@Override
	public String fetchReplacementPersonId(String personId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#removePersonType(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removePersonType(String personId, String personTypeKey)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        PersonType personType = null;
        
        Person person = personDAO.lookupPerson(personId);
        if(person==null){
            throw new DoesNotExistException("Person with id '" + personId + "' does not exist.");
        }

        for(PersonType type : person.getPersonTypes()) {
            if(type.getId().equals(personTypeKey)) {
                personType = type;
                break;
            }
        }
        if(personType == null)
            throw new DoesNotExistException("Person with id '" + personId + "' does not exist have Person Type with id '" + personTypeKey + "'");
        person.getPersonTypes().remove(personType);
        personDAO.updatePerson(person);
        return true;

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeople(org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<PersonInfo> searchForPeople(PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if(personCriteria == null) {
            //Can't really do a non-criteria search
            personCriteria = new PersonCriteria();
            personCriteria.setFirstName("%");
            personCriteria.setLastName("%");
        }
        
	    List<PersonInfo> infos = new ArrayList<PersonInfo>();
	    List<Person> people = personDAO.findPeople(personCriteria);
	    for (Person person : people) {
            infos.add(toPersonInfo(person));
        }
	    return infos;
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeopleByPersonAttributeSetType(java.lang.String, org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<PersonInfo> searchForPeopleByPersonAttributeSetType(
			String personAttributeSetTypeKey, PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        List<PersonInfo> personInfoList = new ArrayList<PersonInfo>();
        if(personCriteria == null) {
            //I'll do a non-criteria search, but this is probably slower than a criteria search depending on how nested everything is
            PersonAttributeSetType personAttributeSetType = personDAO.fetchPersonAttributeSetType(personAttributeSetTypeKey);
            
            Set<PersonType> personTypes = personAttributeSetType.getPersonTypes();
            for (PersonType personType : personTypes) {
                Set<Person> people = personType.getPeople();
                for (Person person : people) {
                    personInfoList.add(toPersonInfo(person));            
                }
            }
        } else {
            List<Person> people = personDAO.findPeopleWithAttributeSetType(personAttributeSetTypeKey, personCriteria);
            for (Person person : people) {
                personInfoList.add(toPersonInfo(person));
            }
        }
        return personInfoList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPersonIds(org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<String> searchForPersonIds(PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        List<String> infos = new ArrayList<String>();
        List<Person> people = personDAO.findPeople(personCriteria);
        for (Person person : people) {
            infos.add(person.getId());
        }
        return infos;
	}
	
	
//------------- Bean Copy Mess -------------------------------------------------
	
	
	private PersonTypeInfo toPersonTypeInfo(PersonType personType){
		PersonTypeInfo personTypeInfo = new PersonTypeInfo();
		personTypeInfo.setId(personType.getId());
		personTypeInfo.setName(personType.getName());
		personTypeInfo.getAttributeSets().addAll(PersonAttributeSetTypeInfos(personType.getPersonAttributeSetTypes()));
		return personTypeInfo;
	}

	private Set<PersonAttributeSetTypeInfo> PersonAttributeSetTypeInfos(
			Set<PersonAttributeSetType> personAttributeSetTypes) {
		Set<PersonAttributeSetTypeInfo> personAttributeSetTypeInfos = new HashSet<PersonAttributeSetTypeInfo>(); 
		for(PersonAttributeSetType personAttributeSetType:personAttributeSetTypes){
			personAttributeSetTypeInfos.add(toPersonAttributeSetTypeInfo(personAttributeSetType));
		}
		return personAttributeSetTypeInfos;
	}

	private PersonAttributeSetTypeInfo toPersonAttributeSetTypeInfo(
			PersonAttributeSetType personAttributeSetType) {
		PersonAttributeSetTypeInfo personAttributeSetTypeInfo = new PersonAttributeSetTypeInfo();
		personAttributeSetTypeInfo.setId(personAttributeSetType.getId());
		personAttributeSetTypeInfo.setName(personAttributeSetType.getName());
		personAttributeSetTypeInfo.getAttributeTypes().addAll(toPersonAttributeTypeInfos(personAttributeSetType.getPersonAttributeTypes()));
		return personAttributeSetTypeInfo;
	}

	private Set<PersonAttributeTypeInfo> toPersonAttributeTypeInfos(
			Set<PersonAttributeType> personAttributeTypes) {
		Set<PersonAttributeTypeInfo> personAttributeTypeInfos = new HashSet<PersonAttributeTypeInfo>();
		for(PersonAttributeType personAttributeType:personAttributeTypes){
			personAttributeTypeInfos.add(toPersonAttributeTypeInfo(personAttributeType));
		}
		return personAttributeTypeInfos;
	}
	
	private PersonAttributeTypeInfo toPersonAttributeTypeInfo(
			PersonAttributeType personAttributeType) {
		PersonAttributeTypeInfo personAttributeTypeInfo = new PersonAttributeTypeInfo();
		personAttributeTypeInfo.setId(personAttributeType.getId());
		personAttributeTypeInfo.setLabel(personAttributeType.getDisplayLabel());
		personAttributeTypeInfo.setName(personAttributeType.getName());
		personAttributeTypeInfo.setType(personAttributeType.getType());
		return personAttributeTypeInfo;
	}
	
	private PersonAttributeType toPersonAttributeType(
			PersonAttributeTypeInfo attributeDefinition, boolean update) {
		PersonAttributeType personAttributeType = new PersonAttributeType();
		if(update||isNullOrBlank(attributeDefinition.getId())){
			if(isNullOrBlank(attributeDefinition.getId())){
				personAttributeType.setId(attributeDefinition.getId());
			}
			personAttributeType.setDisplayLabel(attributeDefinition.getLabel());
			personAttributeType.setName(attributeDefinition.getName());
			personAttributeType.setType(attributeDefinition.getType().toString());
			personAttributeType.setValidator(attributeDefinition.getValidators());
		}else{
			return personDAO.fetchPersonAttributeType(attributeDefinition.getId());
		}
		return personAttributeType;
	}

	private PersonAttributeSetType toPersonAttributeSetType(
			PersonAttributeSetTypeInfo attributeSetType, boolean update) {
		PersonAttributeSetType personAttributeSetType = new PersonAttributeSetType();
		if(update||isNullOrBlank(attributeSetType.getId())){
			if(!isNullOrBlank(attributeSetType.getId())){
				personAttributeSetType.setId(attributeSetType.getId());
			}
			personAttributeSetType.setName(attributeSetType.getName());
			personAttributeSetType.setPersonAttributeTypes(toPersonAttributeTypes(attributeSetType.getAttributeTypes(), false));
		}else{
			return personDAO.fetchPersonAttributeSetType(attributeSetType.getId());
		}
		return personAttributeSetType;
	}

	private Set<PersonAttributeType> toPersonAttributeTypes(
			List<PersonAttributeTypeInfo> attributeTypes, boolean update) {
		Set<PersonAttributeType> personAttributeTypes = new HashSet<PersonAttributeType>();
		for(PersonAttributeTypeInfo attributeDefinition:attributeTypes){
			personAttributeTypes.add(toPersonAttributeType(attributeDefinition, update));
		}
		return personAttributeTypes;
	}
	
	private Set<PersonAttributeSetType> toPersonAttributeSetTypes(
			List<PersonAttributeSetTypeInfo> attributeSets, boolean update) {
		Set<PersonAttributeSetType> personAttributeSetTypes = new HashSet<PersonAttributeSetType>();
		for(PersonAttributeSetTypeInfo attributeSetType:attributeSets){
			personAttributeSetTypes.add(toPersonAttributeSetType(attributeSetType, update));
		}
		return personAttributeSetTypes;
	}

	private PersonType toPersonType(PersonTypeInfo personTypeInfo, boolean update) {
		PersonType personType = new PersonType();
		if(update||isNullOrBlank(personTypeInfo.getId())){
			if(!isNullOrBlank(personTypeInfo.getId())){
				personType.setId(personTypeInfo.getId());
			}
			personType.setName(personTypeInfo.getName());
			personType.setPersonAttributeSetTypes(toPersonAttributeSetTypes(personTypeInfo.getAttributeSets(), false));
		}else{
			return this.personDAO.fetchPersonType(personTypeInfo.getId());
		}
		return personType;
	}

	private PersonInfo toPersonInfo(Person person) {
		PersonInfo personInfo = new PersonInfo();
		
		//Copy fields
		personInfo.setPersonId(person.getId());
		if(person.getPersonalInformation() != null){
			personInfo.setBirthDate(person.getPersonalInformation().getDateOfBirth());
			personInfo.setGender(person.getPersonalInformation().getGender());
		}

		personInfo.getName();
		for(PersonName personName : person.getPersonNames()){
			PersonNameInfo personNameInfo = new PersonNameInfo();
			personNameInfo.setEffectiveEndDate(personName.getEffectiveEndDate());
			personNameInfo.setEffectiveStartDate(personName.getEffectiveStartDate());
			personNameInfo.setGivenName(personName.getGivenName());
			personNameInfo.setMiddleName(personName.getMiddleNames());
			personNameInfo.setNameType(personName.getNameType());
			personNameInfo.setSuffix(personName.getSuffix());
			personNameInfo.setSurname(personName.getSurname());
			personNameInfo.setPersonTitle(personName.getTitle());
			personInfo.getName().add(personNameInfo);
		}
		
		for(PersonCitizenship personCitizenship:person.getPersonCitizenships()){
			PersonCitizenshipInfo personCitizenshipInfo = new PersonCitizenshipInfo();
			personCitizenshipInfo.setCountryOfCitizenshipCode(personCitizenship.getCountryOfCitizenship());
			personCitizenshipInfo.setEffectiveEndDate(personCitizenship.getEffectiveEndDate());
			personCitizenshipInfo.setEffectiveStartDate(personCitizenship.getEffectiveStartDate());
			//personCitizenshipInfo.setPersonId(personCitizenship.getPerson().getId());
			personCitizenshipInfo.setUpdateTime(personCitizenshipInfo.getUpdateTime());
			personCitizenshipInfo.setUpdateUserComment(personCitizenshipInfo.getUpdateUserComment());
			personCitizenshipInfo.setUpdateUserId(personCitizenshipInfo.getUpdateUserId());
			//TODO - personInfo has only one citizenship while person (DAO) has a list
			personInfo.setCitizenship(personCitizenshipInfo);
		}

		personInfo.getReferenceId();
		for(PersonReferenceId personReferenceId:person.getPersonReferenceIds()){
			PersonReferenceIdInfo personReferenceIdInfo = new PersonReferenceIdInfo();
			personReferenceIdInfo.setOrganizationReferenceId(personReferenceIdInfo.getOrganizationReferenceId());
			personReferenceIdInfo.setReferenceId(personReferenceIdInfo.getReferenceId());
			personReferenceIdInfo.setRestrictedAccess(personReferenceId.isRestrictedAccess());
			personReferenceIdInfo.setUpdateTime(personReferenceId.getUpdateTime());
			personReferenceIdInfo.setUpdateUserComment(personReferenceId.getUpdateUserComment());
			personReferenceIdInfo.setUpdateUserId(personReferenceId.getUpdateUserId());
			personInfo.getReferenceId().add(personReferenceIdInfo);
		}
		
		//Copy/convert attributes to map
		personInfo.getAttributes();
		for(PersonAttribute attribute : person.getAttributes()) {
			personInfo.setAttribute(attribute.getPersonAttributeType().getName(), attribute.getValue());
		}

		return personInfo;
	}

    private PersonAttributeSetTypeDisplay toPersonAttributeSetTypeDisplay(PersonAttributeSetType personAttrSetType) {
        PersonAttributeSetTypeDisplay personAttrSetTypeDisp = new PersonAttributeSetTypeDisplay();
        personAttrSetTypeDisp.setId(personAttrSetType.getId());
        personAttrSetTypeDisp.setName(personAttrSetType.getName());
        return personAttrSetTypeDisp;
    }
	
	private PersonTypeDisplay toPersonTypeDisplay(PersonType personType) {
		PersonTypeDisplay personTypeDisplay = new PersonTypeDisplay();
		personTypeDisplay.setId(personType.getId());
		personTypeDisplay.setName(personType.getName());
		return personTypeDisplay;
	}

//----------- Relationship Operations not implemented in POC -------------------	
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPersonRelation(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCreateInfo)
	 */
	@Override
	public String createPersonRelation(String personId, String relatedPersonId,
			String personRelationTypeKey,
			PersonRelationCreateInfo personRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#updatePersonRelation(java.lang.String, org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationUpdateInfo)
	 */
	@Override
	public boolean updatePersonRelation(String personRelationId,
			PersonRelationUpdateInfo personRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#deletePersonRelation(java.lang.String)
	 */
	@Override
	public boolean deletePersonRelation(String personRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonRelation(java.lang.String)
	 */
	@Override
	public PersonRelationInfo fetchPersonRelation(String personRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonRelationTypes()
	 */
	@Override
	public List<PersonRelationTypeDisplay> fetchPersonRelationTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonPersonRelationIds(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> findPersonPersonRelationIds(String personId,
			String personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonRelations(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PersonRelationDisplay> findPersonRelations(String personId,
			String personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPersonIdsByRelation(org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria)
	 */
	@Override
	public List<String> searchForPersonIdsByRelation(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPersonRelations(org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria)
	 */
	@Override
	public List<PersonRelationDisplay> searchForPersonRelations(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeopleByRelation(org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria)
	 */
	@Override
	public List<PersonDisplay> searchForPeopleByRelation(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	
}

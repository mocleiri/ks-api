package org.kuali.student.poc.personidentity.person.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.kuali.student.poc.personidentity.person.dao.Person;
import org.kuali.student.poc.personidentity.person.dao.PersonAttribute;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeSetType;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeType;
import org.kuali.student.poc.personidentity.person.dao.PersonDAO;
import org.kuali.student.poc.personidentity.person.dao.PersonType;
import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.AttributeDataTypeDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.AttributeDefinitionDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.AttributeSetDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonIdDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfoDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfoDTO;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface="org.kuali.student.poc.wsdl.personidentity.person.PersonService",
		serviceName="PersonService",
		portName="PersonService",
		targetNamespace="http://student.kuali.org/poc/wsdl/personidentity/person")
@Transactional
public class PersonServiceImpl implements PersonService {

	private PersonDAO personDAO;

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

	
	@Override
	public List<PersonTypeDTO> findPersonTypes()
			throws OperationFailedException {
		
		List<PersonTypeDTO> personTypeDTOList = new ArrayList<PersonTypeDTO>();
		List<PersonType> personTypeList = personDAO.findPersonTypes("%");
		for(PersonType personType : personTypeList)	{
			personTypeDTOList.add(toPersonTypeDTO(personType));
		}
		
		return personTypeDTOList;
	}
	
	@Override
	public List<PersonTypeDTO> findCreatablePersonTypes()
			throws OperationFailedException {
		
		return findPersonTypes();
	}
	
	@Override
	public PersonDTO fetchFullPersonInfo(long personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		Person person = personDAO.lookupPerson(personId);
		
		if(person == null) {
			throw new DoesNotExistException();
		}
		
		return toPersonDTO(person);
	}
	
	@Override
	public PersonDTO fetchPersonInfoByPersonType(Long personId,
			PersonTypeInfoDTO personType) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long createPerson(PersonInfoDTO person, List<PersonTypeInfoDTO> personTypes)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		//create a JPA entity
		Person personJPA = new Person();
		
		// First look up the personTypes
		Set<PersonType> personTypeSetJPA = new HashSet<PersonType>();
		Set<PersonAttributeType> attributeTypeSetJPA = new HashSet<PersonAttributeType>();

		//Find all the types we are adding to the person to be created
		for(PersonTypeInfoDTO personTypeInfo:personTypes){	
		
			//Look up the person types
			PersonType personTypeJPA =  personDAO.fetchPersonType(personTypeInfo.getId());
			
			//Add them to the list of person types
			personTypeSetJPA.add(personTypeJPA);
			
			//find all the attributes in the person type and add them to our set
			for(PersonAttributeSetType personAttributeSetType:personTypeJPA.getPersonAttributeSetTypes()){
				for(PersonAttributeType personAttributeType:personAttributeSetType.getPersonAttributeTypes()){
					attributeTypeSetJPA.add(personAttributeType);
				}
			}
		}
		
		//Add all the attribute types
		personJPA.getPersonTypes().addAll(personTypeSetJPA);
		
		//Add all of the attributes that were in the personTypes and exist on the person
		for(PersonAttributeType personAttributeType:attributeTypeSetJPA){
		
			//Check that the person being passed in actually has this attribute
			if(person.getAttribute(personAttributeType.getName())!=null){
			
				//Create a new attribute
				PersonAttribute personAttribute = new PersonAttribute();
				personAttribute.setPerson(personJPA);
				personAttribute.setPersonAttributeType(personAttributeType);
				personAttribute.setValue(person.getAttribute(personAttributeType.getName()));
				
				//Add the attribute to the person
				personJPA.getAttributes().add(personAttribute);
			}
			
		}

		//Copy the standard person fields
		personJPA.setDateOfBirth(person.getDob());
		personJPA.setConfidential(person.isConfidential());
		personJPA.setFirstName(person.getFirstName());
		personJPA.setLastName(person.getLastName());
		personJPA.setGender(person.getGender());
		
		//Set the DB specific fields
		personJPA.setUpdateDate(new Date());
		personJPA.setUpdatePerson(null);
		
		//Create the person
		personDAO.createPerson(personJPA);
		return personJPA.getId();
	}

	@Override
	public boolean deletePerson(PersonIdDTO personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    Person person = personDAO.lookupPerson(personId.getId());
	    if(person != null) {
	        personDAO.deletePerson(person);
	        return true;
	    }
		return false;
	}

	@Override
	public boolean updatePerson(PersonDTO person) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
        // Find the JPA entity
        Person personJPA = personDAO.lookupPerson(person.getId());
        
        // First look up the personTypes
        Set<PersonType> personTypeSetJPA = personJPA.getPersonTypes();
        Set<PersonAttributeType> attributeTypeSetJPA = new HashSet<PersonAttributeType>();

        //Find all the types we are adding to the person to be created
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
            if(person.getAttributes().containsKey(personAttributeType.getName())){
                String attributeValue = person.getAttribute(personAttributeType.getName());
                
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
        personJPA.setDateOfBirth(person.getDob());
        personJPA.setConfidential(person.isConfidential());
        personJPA.setFirstName(person.getFirstName());
        personJPA.setLastName(person.getLastName());
        personJPA.setGender(person.getGender());
        
        //Set the DB specific fields
        personJPA.setUpdateDate(new Date());
        personJPA.setUpdatePerson(null);
        
        //Create the person
        personDAO.updatePerson(personJPA);
		return true;
	}

	@Override
	public long createAttributeDefinition(
			AttributeDefinitionDTO attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//TODO exceptions
		PersonAttributeType personAttributeType = toPersonAttributeType(attributeDefinition,false);
		PersonAttributeType created = personDAO.createPersonAttributeType(personAttributeType);
		return created.getId();
	}



	@Override
	public long createPersonTypeInfo(PersonTypeInfoDTO personType)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		PersonType personTypeJPA = new PersonType();
		personTypeJPA.setName(personType.getName());

		//FIXME Exceptions are not working because Transactions cause commits to occur outside of the dao
		//and jpa exceptions will not be thrown until they are commited.
		
		try {
			personTypeJPA = personDAO.createPersonType(personTypeJPA);
		}
		catch(javax.persistence.EntityExistsException ex) {
			throw new AlreadyExistsException();
		}
		
		return personTypeJPA.getId();
	}
	
	@Override
	public long createPersonAttributeSetType(AttributeSetDTO attributeSetDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//TODO exceptions
		PersonAttributeSetType personAttributeSetType = toPersonAttributeSetType(attributeSetDTO,false);
		PersonAttributeSetType created = personDAO.createPersonAttributeSetType(personAttributeSetType);
		return created.getId();
	}
	
	@Override
	public long createPersonType(PersonTypeDTO personTypeDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		//Create a person type and all of the attribute sets and attribute definitions
		//for every new attribute set and definition
		PersonType personType = toPersonType(personTypeDTO,false);
		PersonType created = personDAO.createPersonType(personType);
		return created.getId();
	}

	private PersonTypeDTO toPersonTypeDTO(PersonType personType){
		PersonTypeDTO personTypeDTO = new PersonTypeDTO();
		personTypeDTO.setId(personType.getId());
		personTypeDTO.setName(personType.getName());
		personTypeDTO.getAttributeSets().addAll(toAttributeSetDTO(personType.getPersonAttributeSetTypes()));
		return personTypeDTO;
	}

	private Set<AttributeSetDTO> toAttributeSetDTO(
			Set<PersonAttributeSetType> personAttributeSetTypes) {
		Set<AttributeSetDTO> attributeSetDTO = new HashSet<AttributeSetDTO>(); 
		for(PersonAttributeSetType personAttributeSetType:personAttributeSetTypes){
			attributeSetDTO.add(toAttributeSetDTO(personAttributeSetType));
		}
		return attributeSetDTO;
	}

	private AttributeSetDTO toAttributeSetDTO(
			PersonAttributeSetType personAttributeSetType) {
		AttributeSetDTO attributeSetDTO = new AttributeSetDTO();
		attributeSetDTO.setId(personAttributeSetType.getId());
		attributeSetDTO.setName(personAttributeSetType.getName());
		attributeSetDTO.getAttributeDefinitions().addAll(toAttributeDefinition(personAttributeSetType.getPersonAttributeTypes()));
		return attributeSetDTO;
	}

	private Set<AttributeDefinitionDTO> toAttributeDefinition(
			Set<PersonAttributeType> personAttributeTypes) {
		Set<AttributeDefinitionDTO> attributeDefinitionDTO = new HashSet<AttributeDefinitionDTO>();
		for(PersonAttributeType personAttributeType:personAttributeTypes){
			attributeDefinitionDTO.add(toAttributeDefinition(personAttributeType));
		}
		return attributeDefinitionDTO;
	}

	private AttributeDefinitionDTO toAttributeDefinition(
			PersonAttributeType personAttributeType) {
		AttributeDefinitionDTO attributeDefinitionDTO = new AttributeDefinitionDTO();
		attributeDefinitionDTO.setId(personAttributeType.getId());
		attributeDefinitionDTO.setLabel(personAttributeType.getDisplayLabel());
		attributeDefinitionDTO.setName(personAttributeType.getName());
		attributeDefinitionDTO.setType(AttributeDataTypeDTO.valueOf(personAttributeType.getType()));
		return attributeDefinitionDTO;
	}
	
	private PersonAttributeType toPersonAttributeType(
			AttributeDefinitionDTO attributeDefinition, boolean update) {
		PersonAttributeType personAttributeType = new PersonAttributeType();
		if(update||isNullOrZero(attributeDefinition.getId())){
			if(isNullOrZero(attributeDefinition.getId())){
				personAttributeType.setId(attributeDefinition.getId());
			}
			personAttributeType.setDisplayLabel(attributeDefinition.getLabel());
			personAttributeType.setName(attributeDefinition.getName());
			personAttributeType.setType(attributeDefinition.getType().toString());
		}else{
			return personDAO.fetchPersonAttributeType(attributeDefinition.getId());
		}
		return personAttributeType;
	}
	
	private PersonAttributeSetType toPersonAttributeSetType(
			AttributeSetDTO attributeSetDTO, boolean update) {
		PersonAttributeSetType personAttributeSetType = new PersonAttributeSetType();
		if(update||isNullOrZero(attributeSetDTO.getId())){
			if(!isNullOrZero(attributeSetDTO.getId())){
				personAttributeSetType.setId(attributeSetDTO.getId());
			}
			personAttributeSetType.setName(attributeSetDTO.getName());
			personAttributeSetType.setPersonAttributeTypes(toPersonAttributeTypes(attributeSetDTO.getAttributeDefinitions(), false));
		}else{
			return personDAO.fetchPersonAttributeSetType(attributeSetDTO.getId());
		}
		return personAttributeSetType;
	}

	private Set<PersonAttributeType> toPersonAttributeTypes(
			List<AttributeDefinitionDTO> attributeDefinitions, boolean update) {
		Set<PersonAttributeType> personAttributeTypes = new HashSet<PersonAttributeType>();
		for(AttributeDefinitionDTO attributeDefinition:attributeDefinitions){
			personAttributeTypes.add(toPersonAttributeType(attributeDefinition, update));
		}
		return personAttributeTypes;
	}

	private Set<PersonAttributeSetType> toPersonAttributeSetTypes(
			List<AttributeSetDTO> attributeSets, boolean update) {
		Set<PersonAttributeSetType> personAttributeSetTypes = new HashSet<PersonAttributeSetType>();
		for(AttributeSetDTO attributeSetDTO:attributeSets){
			personAttributeSetTypes.add(toPersonAttributeSetType(attributeSetDTO, update));
		}
		return personAttributeSetTypes;
	}
	
	private PersonType toPersonType(PersonTypeDTO personTypeDTO, boolean update) {
		PersonType personType = new PersonType();
		if(update||isNullOrZero(personTypeDTO.getId())){
			if(!isNullOrZero(personTypeDTO.getId())){
				personType.setId(personTypeDTO.getId());
			}
			personType.setName(personTypeDTO.getName());
			personType.setPersonAttributeSetTypes(toPersonAttributeSetTypes(personTypeDTO.getAttributeSets(), false));
		}else{
			return this.personDAO.fetchPersonType(personTypeDTO.getId());
		}
		return personType;
	}

	private PersonDTO toPersonDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();
		
		personDTO.setId(person.getId());
		personDTO.setFirstName(person.getFirstName());
		personDTO.setLastName(person.getLastName());
		personDTO.setConfidential(person.isConfidential());
		personDTO.setDob(person.getDateOfBirth());
		personDTO.setGender(person.getGender());
		
		// call getAttributes just in case there are none to set.  it shouldn't be a problem
		// but jaxb will throw null pointer exception if it's not done here
		personDTO.getAttributes();
		for(PersonAttribute attribute : person.getAttributes()) {
			personDTO.setAttribute(attribute.getPersonAttributeType().getName(), attribute.getValue());
		}

		List<PersonTypeInfoDTO> personTypeInfoList = personDTO.getPersonTypes();
		for(PersonType personType : person.getPersonTypes()) {
			personTypeInfoList.add(toPersonTypeInfoDTO(personType));
		}
		
		return personDTO;
	}
	
	private PersonTypeInfoDTO toPersonTypeInfoDTO(PersonType personType) {
		PersonTypeInfoDTO personTypeInfoDTO = new PersonTypeInfoDTO();
		personTypeInfoDTO.setId(personType.getId());
		personTypeInfoDTO.setName(personType.getName());
		
		return personTypeInfoDTO;
	}
	
	private boolean isNullOrZero(Long id) {
		return id==null||Long.valueOf(0).equals(id);
	}

}

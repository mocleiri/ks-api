package org.kuali.student.poc.personidentity.person.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Person_T")
@TableGenerator(name = "idGen")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;

	// these will move to their own entity
	private String firstName;
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;

	private char Gender;
	private boolean confidential;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "update_person_id")
	private Person updatePerson;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<PersonAttribute> attributes;

	@ManyToMany
	@JoinTable(name = "Person_PersonType_J", joinColumns = @JoinColumn(name = "Person_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PersonType_ID", referencedColumnName = "ID"))
	protected Set<PersonType> personTypes;

	public Person() {
		id = null;
		attributes = null;
		personTypes = null;
	}

	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<PersonAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new HashSet<PersonAttribute>();
		}
		return attributes;
	}

	public void setAttributes(Set<PersonAttribute> attributes) {
		this.attributes = attributes;
	}

	public Set<PersonType> getPersonTypes() {
		if (personTypes == null) {
			personTypes = new HashSet<PersonType>();
		}
		return personTypes;
	}

	public void setPersonTypes(Set<PersonType> personTypes) {
		this.personTypes = personTypes;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public char getGender() {
		return Gender;
	}

	public void setGender(char gender) {
		Gender = gender;
	}

	public boolean isConfidential() {
		return confidential;
	}

	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Person getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(Person updatePerson) {
		this.updatePerson = updatePerson;
	}

}

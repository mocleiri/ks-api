package org.kuali.student.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class TypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 

	@XmlElement
	private Date effectiveDate; 
	@XmlElement
	private Date expirationDate; 
	@XmlElement
	private List<AttributeInfo> attributes; 
	@XmlAttribute
	private String key; 
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public Date getEffectiveDate(){
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate){
		this.effectiveDate = effectiveDate;
	}
	public Date getExpirationDate(){
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate){
		this.expirationDate = expirationDate;
	}
	public List<AttributeInfo> getAttributes(){
		if(attributes == null){
			attributes = new ArrayList<AttributeInfo>();
		}
		return attributes;
	}
	public void setAttributes(List<AttributeInfo> attributes){
		this.attributes = attributes;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}
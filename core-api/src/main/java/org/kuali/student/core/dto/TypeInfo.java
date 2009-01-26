package org.kuali.student.core.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

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
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
	private Map<String, String> attributes;
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
	public Map<String, String> getAttributes(){
		if(attributes == null){
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes){
		this.attributes = attributes;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}
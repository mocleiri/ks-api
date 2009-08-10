package org.kuali.student.core.document.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.validation.dto.ValidationResultContainer;

/**
 * This class was generated by Apache CXF 2.2 Tue Jun 16 14:57:19 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "validateDocumentResponse", namespace = "http://student.kuali.org/wsdl/document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateDocumentResponse", namespace = "http://student.kuali.org/wsdl/document")
public class ValidateDocumentResponse {

	@XmlElement(name = "return")
	private java.util.List<ValidationResultContainer> _return;

	public java.util.List<ValidationResultContainer> getReturn() {
		return this._return;
	}

	public void setReturn(
			java.util.List<ValidationResultContainer> new_return) {
		this._return = new_return;
	}

}

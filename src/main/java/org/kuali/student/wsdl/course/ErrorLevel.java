
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for errorLevel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="errorLevel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="WARN"/>
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "errorLevel", namespace = "http://student.kuali.org/wsdl/course")
@XmlEnum
public enum ErrorLevel {

    OK,
    WARN,
    ERROR;

    public String value() {
        return name();
    }

    public static ErrorLevel fromValue(String v) {
        return valueOf(v);
    }

}

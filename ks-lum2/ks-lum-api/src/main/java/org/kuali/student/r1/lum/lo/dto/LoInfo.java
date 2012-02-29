/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r1.lum.lo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.r1.common.dto.HasAttributes;
import org.kuali.student.r1.common.dto.HasTypeState;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r1.common.dto.MetaInfo;
import org.kuali.student.r1.common.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.w3c.dom.Element;

/**
 * Detailed information about a learning objective
 *
 * @Author KSContractMojo
 * @Author jimt
 * @Since Tue Dec 08 10:01:30 PST 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/loInfo+Structure+v1.0-rc2">LoInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LoInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo desc;

    @XmlElement
    private String loRepositoryKey;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * Friendly name of the learning objective
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Narrative description of the learning objective
     */
    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    /**
     * Unique identifier for a learning objective repository. This value is immutable once set during creation.
     */
    public String getLoRepositoryKey() {
        return loRepositoryKey;
    }

    public void setLoRepositoryKey(String loRepositoryKey) {
        this.loRepositoryKey = loRepositoryKey;
    }

    /**
     * Date and time that this learning objective became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this learning objective expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    /**
     * Unique identifier for a learning objective type.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the learning objective. The values for this field are constrained to those in the loState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a learning objective record. This is optional, due to the identifier being set at the time of creation. Once the learning objective has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
    	return "LoInfo[id=" + id + "]";
    }

	public LoInfo convertR2toR1(org.kuali.student.r2.lum.lo.dto.LoInfo loToDelete) {
	  
	org.kuali.student.r2.lum.lo.dto.LoInfo r2Object = loToDelete;
    org.kuali.student.r1.lum.lo.dto.LoInfo r1Object = new   org.kuali.student.r1.lum.lo.dto.LoInfo();
	
    r1Object.setLoRepositoryKey(r2Object.getLoRepositoryKey());
    r1Object.setEffectiveDate(r2Object.getEffectiveDate()); 
    r1Object.setExpirationDate(r2Object.getExpirationDate());
	  
       
		return r1Object;
	}
}
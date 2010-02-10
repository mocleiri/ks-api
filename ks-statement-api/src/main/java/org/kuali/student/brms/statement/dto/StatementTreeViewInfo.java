package org.kuali.student.brms.statement.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatementTreeViewInfo implements Serializable, Idable, HasTypeState {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String name;
    @XmlElement
    private RichTextInfo desc;
    @XmlElement
    private StatementOperatorTypeKey operator;
    @XmlElement
    private List<StatementTreeViewInfo> statements;
    @XmlElement
    private List<ReqComponentInfo> reqComponents;
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
    @XmlAttribute
    private String parentId;
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    public StatementOperatorTypeKey getOperator() {
        return operator;
    }

    public void setOperator(StatementOperatorTypeKey operator) {
        this.operator = operator;
    }

    public List<StatementTreeViewInfo> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementTreeViewInfo> statements) {
        this.statements = statements;
    }

    public List<ReqComponentInfo> getReqComponents() {
        return reqComponents;
    }

    public void setReqComponents(List<ReqComponentInfo> reqComponents) {
        this.reqComponents = reqComponents;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	@Override
	public String toString() {
		return "StatementTreeViewInfo[id=" + id + "]";
	}
    
}

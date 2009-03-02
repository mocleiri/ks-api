package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

@Entity
@Table(name = "KSLU_STMT")
@NamedQueries( {
        @NamedQuery(name = "LuStatement.getLuStatementsForLuStatementType", query = "SELECT ls FROM LuStatement ls WHERE luStatementType = :luStatementTypeKey") })
public class LuStatement extends MetaEntity implements AttributeOwner<LuStatementAttribute>{
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCR")
    private String desc;
    
    @Column(name="ST")
    private String state;

    @Column(name="OPERATOR")
    private StatementOperatorTypeKey operator;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "PARENT_LU_STMT_ID")
    private LuStatement parent;

    @OneToMany(mappedBy = "parent")
    private List<LuStatement> children;

    @ManyToMany
    @JoinTable(name = "KSLU_STMT_JN_REQ_COM", joinColumns = @JoinColumn(name = "REQ_COM_ID"), inverseJoinColumns = @JoinColumn(name = "LU_STMT_ID"))
    private List<ReqComponent> requiredComponents;

    @ManyToOne
    @JoinColumn(name = "LU_STMT_TYPE_ID")
    private LuStatementType luStatementType;

    @ManyToMany
    @JoinTable(name = "KS_CLU_LU_STMT_T", joinColumns = @JoinColumn(name = "CLU_ID"), inverseJoinColumns = @JoinColumn(name = "LU_STMT_ID"))
    private List<Clu> clus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER")
    private List<LuStatementAttribute> attributes;
    
    /**
     * AutoGenerate the Id
     */
    @Override
    public void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LuStatement getParent() {
        return parent;
    }

    public void setParent(LuStatement parent) {
        this.parent = parent;
    }

    public List<LuStatement> getChildren() {
        return children;
    }

    public void setChildren(List<LuStatement> children) {
        this.children = children;
    }

    public List<ReqComponent> getRequiredComponents() {
        return requiredComponents;
    }

    public void setRequiredComponents(List<ReqComponent> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public LuStatementType getLuStatementType() {
        return luStatementType;
    }

    public void setLuStatementType(LuStatementType luStatementType) {
        this.luStatementType = luStatementType;
    }

    public List<Clu> getClus() {
        return clus;
    }

    public void setClus(List<Clu> clus) {
        this.clus = clus;
    }

    public StatementOperatorTypeKey getOperator() {
        return operator;
    }

    public void setOperator(StatementOperatorTypeKey operator) {
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public List<LuStatementAttribute> getAttributes() {
        if(attributes==null){
            attributes = new ArrayList<LuStatementAttribute>();
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<LuStatementAttribute> attributes) {
        this.attributes=attributes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package org.kuali.student.core.comment.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;
@Entity
@Table(name = "KSCO_COMMENT_TYPE")
public class CommentType extends Type<CommentTypeAttribute> {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CommentTypeAttribute> attributes;

    @Override
    public List<CommentTypeAttribute> getAttributes() {
        if(attributes==null){
            attributes = new ArrayList<CommentTypeAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<CommentTypeAttribute> attributes) {
        this.attributes=attributes;
    }

}

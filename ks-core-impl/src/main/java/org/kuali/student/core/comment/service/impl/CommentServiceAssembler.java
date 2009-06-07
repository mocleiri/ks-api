/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.comment.service.impl;

import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.RichTextInfo;
import org.kuali.student.core.comment.entity.Comment;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CommentServiceAssembler extends BaseAssembler {

    public static CommentInfo toCommentInfo(Comment entity) {
        CommentInfo dto = new CommentInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "commentText", "attributes", "type" });

        dto.setCommentText(toRichTextInfo(entity.getCommentText()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());

        return dto;
    }


    public static RichTextInfo toRichTextInfo(RichText entity) {
        if(entity==null){
            return null;
        }

        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id" });

        return dto;
    }
}

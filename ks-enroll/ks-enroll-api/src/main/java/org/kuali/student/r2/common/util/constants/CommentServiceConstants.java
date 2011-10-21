package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;

/**
 * This class holds the constants used by the Comment service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class CommentServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "comment";
    public static final String REF_OBJECT_URI_ATP = NAMESPACE + "/" + CommentInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_MILESTONE = NAMESPACE + "/" + TagInfo.class.getSimpleName();
}

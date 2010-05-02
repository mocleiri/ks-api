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

package org.kuali.student.brms.devgui.server.convert;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.gwt.user.client.rpc.SerializableException;

public class GwtException extends SerializableException {

    public static SerializableException toGWT(Exception ex) {
        return new SerializableException(GwtException.stringException(ex));
    }

    private static String stringException(Exception e) {
        e.printStackTrace();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String str = sw.toString();
        return str;
    }
}

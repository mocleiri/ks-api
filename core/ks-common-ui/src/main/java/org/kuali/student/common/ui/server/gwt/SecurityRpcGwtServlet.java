/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.server.gwt;

import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.server.gwt.rpc.BaseRemoteAbstractServiceServlet;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

/**
 * This is a description of what this class does - Will Gomes don't forget to
 * fill this in.
 * 
 * @author Kuali Student Team
 * 
 */
@SuppressWarnings("serial")
public class SecurityRpcGwtServlet extends BaseRemoteAbstractServiceServlet implements SecurityRpcService {

	public String getPrincipalUsername() {
		String username = "unknown";

		try {
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (obj instanceof UserDetails) {
				username = ((UserDetails) obj).getUsername();
			} else {
				username = obj.toString();
			}
		} catch (Exception e) {
			// TODO: How do we handle this?
		}

		return username;
	}

}

package org.kuali.student.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Tracks information about an HttpServletRequest.
 * 
 * @author Jeff Caddel
 */
public class RecordedRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	int sequence;

	/**
	 * URL for this request
	 */
	String path;

	/**
	 * Time of this request
	 */
	Date startTime;
	Date finishTime;

	/**
	 * Request parameters (if any)
	 */
	List<NameValuesBean> parameters;
	List<NameValuesBean> headers;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public List<NameValuesBean> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuesBean> parameters) {
		this.parameters = parameters;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public List<NameValuesBean> getHeaders() {
		return headers;
	}

	public void setHeaders(List<NameValuesBean> headers) {
		this.headers = headers;
	}

}

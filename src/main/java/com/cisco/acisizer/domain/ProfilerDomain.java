/**
 * 
 */
package com.cisco.acisizer.domain;

import javax.persistence.Transient;

import com.google.gson.Gson;

/**
 * @author Mahesh
 *
 */
public class ProfilerDomain {

	@Transient
	protected Gson gson=new Gson();
}

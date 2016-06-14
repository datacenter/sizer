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
public class AciDomain {

	@Transient
	protected Gson gson=new Gson();
}

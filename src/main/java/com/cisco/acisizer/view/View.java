/**
 * 
 */
package com.cisco.acisizer.view;


/**
 * @author Mahesh
 *
 */
public class View {
	public interface Project extends Users{}
	public interface Users{}
	public interface FilterEntry extends Filter{}
	public interface Filter extends Subject {}
	public interface Subject extends Contract {}
	public interface Contract {}
	public interface Plugin{}
	public interface AuditInfo{}
	public interface Model{}
	public interface Device{}{}
	public interface Room{}
	public interface Rows extends Room {}

}
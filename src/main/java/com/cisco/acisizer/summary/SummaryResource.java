package com.cisco.acisizer.summary;

public class SummaryResource {
	private String name;
	private int count;
	private int pct_usage;
	private int max;
	
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPct_usage() {
		return pct_usage;
	}
	public void setPct_usage(int pct_usage) {
		this.pct_usage = pct_usage;
	}
}

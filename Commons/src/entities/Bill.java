package entities;

import java.io.Serializable;

public class Bill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type1;
	private String numberOfVisitor;
	private boolean isInstructor;
	private boolean invited;
	private boolean payed;
	private	String requestedBill;
	
	public Bill(String type1,String numberOfVisitor,boolean invisted,boolean payed) {
		this.numberOfVisitor=numberOfVisitor;
		this.type1=type1;
		this.invited=invisted;
		this.payed=payed;
	}
	
	public Bill(String id) {
		this.id=id;
	}

	 public String getNumberOfVisitor() {
		return numberOfVisitor;
	}
	 
	 public String getType() {
		return type1;
	}
	 
	 public String getId() {
		return id;
	}
	 
	 public boolean getinvited() {
		return invited;
	}
	 
	 public boolean getPayed() {
		return payed;
	}
	 public boolean getIsInstructor() {
		return isInstructor;
	}
	public void setNumberOfVisitor(String numberOfVisitor) {
		this.numberOfVisitor = numberOfVisitor;
	}
	
	public void setRequestedBill(String requestedBill) {
		this.requestedBill = requestedBill;
	} 


}
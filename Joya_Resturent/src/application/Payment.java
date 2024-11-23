package application;

public class Payment {
	 private int id;
	 private double amount;
	 private String method;
	 private String Date;
	public Payment() {
		super();
	}
	public Payment(int id, double amount, String method, String date) {
		super();
		this.id = id;
		this.amount = amount;
		this.method = method;
		Date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	 
	 
	 
}

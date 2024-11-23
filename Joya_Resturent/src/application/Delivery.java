package application;

public class Delivery {
	private int id;
	 private double price;
	 private String address;
	 private String Date;
	 private String time;
	 
	 
	public Delivery() {
		super();
	}
	
	
	public Delivery(int id, double price, String address, String date, String time) {
		super();
		this.id = id;
		this.price = price;
		this.address = address;
		Date = date;
		this.time = time;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDate() {
		return Date;
	}


	public void setDate(String date) {
		Date = date;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	
	
	 
}
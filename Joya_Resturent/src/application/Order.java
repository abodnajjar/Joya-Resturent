package application;

public class Order {
	private int id;
	private String date;
	private String time;
	private int customer_id;
	private int payment_id;
	private int employee_id;
	private double price ;

	public Order() {
		super();
	}
	public Order( String date, String time,int customer_id, int employee_id,int payment_id,double pric  ) {
		super();
		this.date = date;
		this.time = time;
		this.price=pric;
		this.customer_id = customer_id;
		this.payment_id = payment_id;
		this.employee_id = employee_id;
	}
	public Order( String date, String time, int employee_id ) {
		super();
		this.date = date;
		this.time = time;
		this.employee_id = employee_id;
	}

	public Order(int id, String date, String time, int customer_id, int payment_id,int employee_id,double price) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.price=price;
		this.customer_id = customer_id;
		this.payment_id = payment_id;
		this.employee_id = employee_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}




	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}







}

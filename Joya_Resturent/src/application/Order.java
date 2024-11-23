package application;

public class Order {
	 private int id;
	 private String date;
	 private double tolal_price;
	 private String time;
	 private String itemes;
	 private int customer_id;
	 private int payment_id;
	 private int menu_id;
	 private int employee_id;
	 
	public Order() {
		super();
	}

	public Order(int id, String date, double tolal_price, String time, String itemes, int customer_id, int payment_id,
		int menu_id, int employee_id) {
		super();
		this.id = id;
		this.date = date;
		this.tolal_price = tolal_price;
		this.time = time;
		this.itemes = itemes;
		this.customer_id = customer_id;
		this.payment_id = payment_id;
		this.menu_id = menu_id;
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

	public double getTolal_price() {
		return tolal_price;
	}

	public void setTolal_price(double tolal_price) {
		this.tolal_price = tolal_price;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getItemes() {
		return itemes;
	}

	public void setItemes(String itemes) {
		this.itemes = itemes;
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

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}



	 
	 
	 
	 
}

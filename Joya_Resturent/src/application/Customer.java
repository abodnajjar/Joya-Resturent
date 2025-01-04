package application;

public class Customer {
	private int id;
	private String name;
	private double point;


	public Customer() {
		super();
	}

	public Customer(int id, String name, double pointer) {
		super();
		this.id = id;
		this.name = name;
		this.point = pointer;
	}

	public Customer(String customerName, Double customerPoints) {
		this.name= customerName;
		this.point = customerPoints;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPointer() {
		return point;
	}

	public void setPointer(double pointer) {
		this.point = pointer;
	}

	@Override
	public String toString() {
		return name;
	}



}


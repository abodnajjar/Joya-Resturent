package application;

public class Customer {
	 private int id;
	 private String name;
	 private double pointer;
	 
	 
		public Customer() {
			super();
		}
	 
	public Customer(int id, String name, double pointer) {
		super();
		this.id = id;
		this.name = name;
		this.pointer = pointer;
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
		return pointer;
	}

	public void setPointer(double pointer) {
		this.pointer = pointer;
	}
	 
	 
	 
}


package application;

public class Employee {
	 private int id;
	 private double salary;
	 private String name;
	 private String ContactInfo;
	 private String postion;
	 private String password;
	 private String address;
	 
	 
	public Employee() {
		super();
	}
	


	public Employee(int id, double salary, String name, String contactInfo, String postion, String password,
			String address) {
		super();
		this.id = id;
		this.salary = salary;
		this.name = name;
		ContactInfo = contactInfo;
		this.postion = postion;
		this.password = password;
		this.address = address;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostion() {
		return postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



	public String getContactInfo() {
		return ContactInfo;
	}



	public void setContactInfo(String contactInfo) {
		ContactInfo = contactInfo;
	}
	
	 
	 
	 
}
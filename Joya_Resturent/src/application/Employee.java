package application;

public class Employee {
	 private int EmployeeID;
	 private double salary;
	 private String name;
	 private String ContactInfo;
	 private String postion;
	 private String password;
	 private String address;
	 
	 
	public Employee() {
		super();
	}
	


	public Employee( double salary, String name, String contactInfo, String postion, String password,
			String address) {
		super();
		this.salary = salary;
		this.name = name;
		ContactInfo = contactInfo;
		this.postion = postion;
		this.password = password;
		this.address = address;
	}

	public Employee(int id, double salary, String name, String contactInfo, String postion, String password,
			String address) {
		super();
		this.EmployeeID=id;
		this.salary = salary;
		this.name = name;
		ContactInfo = contactInfo;
		this.postion = postion;
		this.password = password;
		this.address = address;
	}

	public int getId() {
		return EmployeeID;
	}

	public void setId(int id) {
		this.EmployeeID = id;
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


	@Override
	public String toString() {
		return "Employee [EmployeeID=" + EmployeeID + ", salary=" + salary + ", name=" + name + ", ContactInfo="
				+ ContactInfo + ", postion=" + postion + ", password=" + password + ", address=" + address + "]";
	}
	
	 
	 
	 
}
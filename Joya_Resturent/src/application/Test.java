package application;

public class Test {
	private int id;
	private String name; 
	private double price;
	private int Quatity;
	private double Total_Price; 


	public Test(int id, String name, double price, int quatity, double total_Price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		Quatity = quatity;
		Total_Price = total_Price;
		//GetPric() ;
	}

	public void GetPric() {
		double x=0;
		x=Quatity*Total_Price;
		this.Total_Price=x;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuatity() {
		return Quatity;
	}

	public void setQuatity(int quatity) {
		Quatity = quatity;
	}

	public double getTotal_Price() {
		return Total_Price;
	}

	public void setTotal_Price(double total_Price) {
		Total_Price = total_Price;
	}

	public static void main(String[] args) {



	}

}

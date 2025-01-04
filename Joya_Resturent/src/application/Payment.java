package application;

public class Payment {
	 private int id;
	 private String method;
	public Payment() {
		super();
	}
	public Payment( String method) {
		super();
		this.method = method;

	}
	public Payment(int id,String method) {
		super();
		this.id = id;
		this.method = method;
	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return method;
	}

	  
}

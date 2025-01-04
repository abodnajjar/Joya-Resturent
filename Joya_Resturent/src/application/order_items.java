package application;

public class order_items {
	
	int orderId;
	int menuId;
	int quantity;
	Double orderPrice;
	
	
	
	
	public order_items(int orderId, int menuId) {
		super();
		this.orderId = orderId;
		this.menuId = menuId;
	}
	public order_items(int orderId, int menuId, int quantity, Double orderPrice) {
		super();
		this.orderId = orderId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.orderPrice = orderPrice;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	

}

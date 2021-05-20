package ecommerce;

import java.util.ArrayList;

public class sessionCart {
	public ArrayList<int[]> cart;
	private String user;
	
	public sessionCart(String username) {
		this.cart = new ArrayList();
		this.user= username;
	}
	
	public ArrayList getCart() {
		return this.cart;
	}
	
	public void addToCart(int pid, int q) {
		int[] item = {pid,q};
		cart.add(item);
	}
	
	public void removeFromCart(int pid) {
		for ( int i = 0; i < cart.size(); i++ ) {
			if ( cart.get(i)[0] == pid ) {
				cart.remove(i);
			}
		}
	}
	
	public void clearCart() {
		cart.clear();
	}
}

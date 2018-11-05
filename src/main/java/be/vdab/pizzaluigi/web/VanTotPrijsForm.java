package be.vdab.pizzaluigi.web;

import java.math.BigDecimal;

class VanTotPrijsForm {
	private BigDecimal van;
	private BigDecimal tot;
	
	public BigDecimal getVan() {
		return van;
	}
	public void setVan(BigDecimal van) {
		this.van = van;
	}
	public BigDecimal getTot() {
		return tot;
	}
	public void setTot(BigDecimal tot) {
		this.tot = tot;
	}
	
}

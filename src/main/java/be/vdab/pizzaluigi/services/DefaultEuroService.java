package be.vdab.pizzaluigi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import be.vdab.pizzaluigi.restclients.KoersClient;

@Service
class DefaultEuroService implements EuroService {
	private final KoersClient koersClient;
	
	//constructor
	DefaultEuroService(KoersClient koersClient) {
		this.koersClient = koersClient;
	}
	
	public BigDecimal naarDollar(BigDecimal euro) {
		return euro.multiply(koersClient.getDollarKoers())
				   .setScale(2, RoundingMode.HALF_UP);
	}

}

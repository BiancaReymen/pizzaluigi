package be.vdab.pizzaluigi.restclients;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.vdab.pizzaluigi.exceptions.KoersClientException;

@Component
@Order(1)
class FixerKoersClient implements KoersClient {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(FixerKoersClient.class);
	
	private final URL url;
	
	FixerKoersClient(){
		try {
			url = new URL ("http://data.fixer.io/api/latest?access_key=c73bfc31bb6ca323b61c93a65fc1a86c&symbols=USD");
		} catch (MalformedURLException ex) {
			String fout = "Fixer URL is verkeerd";
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
	@Override
	public BigDecimal getDollarKoers() {
		try (Scanner scanner = new Scanner(url.openStream())) {
			String lijn = scanner.nextLine();
			int beginPositieKoers = lijn.indexOf("USD") + 5;
			int accoladePositie = lijn.indexOf('}', beginPositieKoers);
			LOGGER.info("koers gelezen via Fixer");
			return new BigDecimal(lijn.substring(beginPositieKoers, accoladePositie));
		} catch (IOException | NumberFormatException ex) {
		    String fout = "kan koers niet lezen via Fixer";
			LOGGER.error(fout , ex);
			throw new KoersClientException(fout);
		}	
		
	}
	
}

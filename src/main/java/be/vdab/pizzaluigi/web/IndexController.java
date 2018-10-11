package be.vdab.pizzaluigi.web;

import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

class IndexController {

	@GetMapping
	String index() {
		int uur = LocalTime.now().getHour();
		if (uur < 12 ) {
			return "Goede morgen";
			
		}
		if (uur < 18) {
			return "Goede middag";
		}
		return "Goede avond";
	}
}

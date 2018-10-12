package be.vdab.pizzaluigi.web;

import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.valueobjects.Adres;
import be.vdab.pizzaluigi.valueobjects.Persoon;

@Controller
@RequestMapping("/")

class IndexController {

	@GetMapping
	ModelAndView index() {
		String boodschap;
		int uur = LocalTime.now().getHour();
		if (uur < 12) {
			boodschap = "Goede morgen";
		} else if (uur < 18) {
			boodschap = "Goede middag";
		} else {
			boodschap = "Goede avond";
		}
		return new ModelAndView("index", "boodschap", boodschap)
				.addObject("zaakvoerder", new Persoon("Luigi", "Peperone", 7, true,
 						new Adres("Grote Markt", "3", 9700, "Oudenaarde")));
				
			
	}
		
}

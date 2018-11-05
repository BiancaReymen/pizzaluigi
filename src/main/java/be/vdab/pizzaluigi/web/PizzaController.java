package be.vdab.pizzaluigi.web;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.EuroService;
import be.vdab.pizzaluigi.services.PizzaService;

@Controller
@RequestMapping("pizzas")

public class PizzaController {
	private static final String PIZZA_VIEW = "pizza";
	private static final String PIZZAS_VIEW = "pizzas";
	private static final String PRIJZEN_VIEW = "prijzen";
	private final EuroService euroService;
	private final PizzaService pizzaService;
	
	
//	private final List<Pizza> pizzas = Arrays.asList(
//			new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
//			new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
//			new Pizza(3, "Calzone", BigDecimal.valueOf(4), false));
	private final Map<Long, Pizza> pizzas = new LinkedHashMap<>();
	PizzaController(EuroService euroService, PizzaService pizzaService){
		pizzas.put(1L, new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true));
		pizzas.put(2L, new Pizza(2, "Margherita", BigDecimal.valueOf(5), false));
		pizzas.put(3L, new Pizza(3, "Calzone", BigDecimal.valueOf(4), false));
		pizzas.put(23L, new Pizza(23, "Fungi & Olive", BigDecimal.valueOf(5), false));
		this.euroService = euroService;
		this.pizzaService = pizzaService;
	}
			
	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzaService.findAll());
	}
	@GetMapping ("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(PIZZA_VIEW);
		pizzaService.read(id).ifPresent(pizza -> {
			modelAndView.addObject(pizza);
			modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
		});
		
		return modelAndView; 
	}
	@GetMapping ("prijzen")
	ModelAndView prijzen() {
		return new ModelAndView(PRIJZEN_VIEW, "prijzen", 
				pizzaService.findUniekePrijzen());
	}	
				
	
	@GetMapping (params = "prijs")
	ModelAndView pizzasVanPrijs(BigDecimal prijs) {
		return new ModelAndView(PRIJZEN_VIEW, "pizzas", 
				pizzaService.findByPrijs(prijs))
				.addObject("prijs", prijs)
				.addObject("prijzen", pizzaService.findUniekePrijzen());
	}
	
	private static final String VAN_TOT_PRIJS_VIEW = "vantotprijs";
	@GetMapping("vantotprijs")
	ModelAndView findVanTotPrijs() {
		VanTotPrijsForm form = new VanTotPrijsForm();
		form.setVan(BigDecimal.ZERO);
		form.setTot(BigDecimal.ZERO);
		return new ModelAndView(VAN_TOT_PRIJS_VIEW).addObject(form);
	}
	
	@GetMapping(params = {"van", "tot"})
	ModelAndView findVanTotPrijs(@Valid VanTotPrijsForm form, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView(VAN_TOT_PRIJS_VIEW);
		if (bindingResult.hasErrors()) {
			return modelAndView;
		}
		List<Pizza> pizzas = pizzaService.findByPrijsBetween(form.getVan(), form.getTot());
		if (pizzas.isEmpty()) {
			bindingResult.reject("geenPizzas");
		} else {
			modelAndView.addObject("pizzas", pizzas);
		}
		return modelAndView;
	}

}

package be.vdab.pizzaluigi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.EuroService;

@RunWith(MockitoJUnitRunner.class)
public class PizzaControllerTest {
	
	private PizzaController controller;
	private PizzaController pizzaController;
	
	@Mock
	private EuroService dummyEuroService;
	
	@Before
	public void before() {
		pizzaController = new PizzaController(dummyEuroService);
	}
	@Test
	public void pizzaWerktSamenMetDeJspPizza() {
		ModelAndView modelAndView = controller.pizza(1);
		assertEquals("pizza", modelAndView.getViewName());
	}
	@Test
	public void pizzaGeeftPizzaDoor() {
		ModelAndView modelAndView = controller.pizza(1);
		assertTrue(modelAndView.getModel().get("pizza") instanceof Pizza);
	}
	@Test
	public void onbestaandePizza() {
		ModelAndView modelAndView = controller.pizza(-1);
		assertFalse(modelAndView.getModel().containsKey("pizza"));
	}
	@Test
	public void pizzaWerktSamenMetDeJspPizzas() {
		ModelAndView modelAndView = controller.pizzas();
		assertEquals("pizzas", modelAndView.getViewName());
	}
	@Test
	public void pizzasGeeftPizzaDoor() {
		ModelAndView modelAndView = controller.pizzas();
		assertTrue(modelAndView.getModel().get("pizzas") instanceof Map);
	}
	

}

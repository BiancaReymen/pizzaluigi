package be.vdab.pizzaluigi.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.exceptions.PizzaNietGevondenException;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JdbcPizzaRepository.class)
@Sql("/insertPizza.sql")
public class JdbcPizzaRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	private static final String PIZZAS = "pizzas";
	@Autowired
	private JdbcPizzaRepository repository;

	@Test
	public void findAantal() {
		assertEquals(super.countRowsInTable(PIZZAS), repository.findAantalPizzas());
	}

	@Test
	public void findAll() {
		List<Pizza> pizzas = repository.findAll();
		assertEquals(super.countRowsInTable(PIZZAS), pizzas.size());
		// zijn ze wel oplopend gesorteerd :
		long vorigeId = 0;
		for (Pizza pizza : pizzas) {
			assertTrue(pizza.getId() > vorigeId);
			vorigeId = pizza.getId();
		}
	}

	@Test
	public void create() {
		int aantalPizzas = super.countRowsInTable(PIZZAS);
		Pizza pizza = new Pizza("test2", BigDecimal.TEN, false);
		repository.create(pizza);
		assertNotEquals(0, pizza.getId());
		assertEquals(aantalPizzas + 1, this.countRowsInTable(PIZZAS));
		assertEquals(1, super.countRowsInTableWhere(PIZZAS, "id=" + pizza.getId()));
	}

	private long idVanTestPizza() {
		return super.jdbcTemplate.queryForObject("select id from pizzas where naam='test'", Long.class);
	}

	@Test
	public void delete() {
		long id = idVanTestPizza();
		int aantalPizzas = super.countRowsInTable(PIZZAS);
		repository.delete(id);
		assertEquals(aantalPizzas - 1, super.countRowsInTable(PIZZAS));
		assertEquals(0, super.countRowsInTableWhere(PIZZAS, "id=" + id));
	}

	@Test
	public void read() {
		assertEquals("test", repository.read(idVanTestPizza()).get().getNaam());
	}

	@Test
	public void readOnbestaandePizza() {
		assertFalse(repository.read(-1).isPresent());
	}

	@Test
	public void update() {
		long id = idVanTestPizza();
		Pizza pizza = new Pizza(id, "test", BigDecimal.ONE, false);
		repository.update(pizza);
		assertEquals(0, BigDecimal.ONE.compareTo(
				super.jdbcTemplate.queryForObject("select prijs from pizzas where id=?", BigDecimal.class, id)));
	}

	@Test(expected = PizzaNietGevondenException.class)
	public void updateOnbestaandePizza() {
		repository.update(new Pizza(-1, "test", BigDecimal.ONE, false));
	}

	@Test
	public void findByPrijsBetween() {
		List<Pizza> pizzas = repository.findByPrijsBetween(BigDecimal.ONE, BigDecimal.TEN);
		BigDecimal vorigePrijs = BigDecimal.ZERO;
		for (Pizza pizza : pizzas) {
			assertTrue(pizza.getPrijs().compareTo(BigDecimal.ONE) >= 0);
			assertTrue(pizza.getPrijs().compareTo(BigDecimal.TEN) <= 0);
			assertTrue(vorigePrijs.compareTo(pizza.getPrijs()) <= 0);
			vorigePrijs = pizza.getPrijs();
		}
		assertEquals(super.countRowsInTableWhere(PIZZAS, "prijs between 1 and 10"), pizzas.size());
	}

	@Test
	public void findUniekePrijzenGeeftPrijzenOplopend() {
		List<BigDecimal> prijzen = repository.findUniekePrijzen();
		long aantalPrijzen = super.jdbcTemplate.queryForObject("select count(distinct prijs) from pizzas", Long.class);
		assertEquals(aantalPrijzen, prijzen.size());
		BigDecimal vorigePrijs = BigDecimal.valueOf(-1);
		for (BigDecimal prijs : prijzen) {
			assertTrue(prijs.compareTo(vorigePrijs) > 0);
			vorigePrijs = prijs;
		}
	}

	@Test
	public void findByPrijs() {
		List<Pizza> pizzas = repository.findByPrijs(BigDecimal.TEN);
		String vorigeNaam = "";
		for (Pizza pizza : pizzas) {
			assertEquals(0, BigDecimal.TEN.compareTo(pizza.getPrijs()));
			assertTrue(vorigeNaam.compareTo(pizza.getNaam()) <= 0);
			vorigeNaam = pizza.getNaam();
		}
		assertEquals(super.countRowsInTableWhere(PIZZAS, "prijs=10"), pizzas.size());
	}
}

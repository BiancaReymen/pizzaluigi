package be.vdab.pizzaluigi.repositories;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.exceptions.PizzaNietGevondenException;

@Repository
class JdbcPizzaRepository implements PizzaRepository {

	private final JdbcTemplate template;
	private final SimpleJdbcInsert insert;
	private final RowMapper<Pizza> pizzaRowMapper = (resultSet, rowNum) ->
		new Pizza(resultSet.getLong("id"), 
				  resultSet.getString("naam"),
				  resultSet.getBigDecimal("prijs"),
			      resultSet.getBoolean("pikant"));
	private final RowMapper<BigDecimal> prijsRowMapper = (resultSet, rowNum) ->
	resultSet.getBigDecimal("prijs");

	JdbcPizzaRepository(JdbcTemplate template) {
		this.template = template;
		this.insert = new SimpleJdbcInsert(template);
		insert.withTableName("pizzas");
		insert.usingGeneratedKeyColumns("id");
	}
	private static final String SELECT_AANTAL_PIZZAS = "select count(*) from pizzas";
	@Override
	public long findAantalPizzas() {
		return template.queryForObject(SELECT_AANTAL_PIZZAS, Long.class);
	}
	private static final String DELETE_PIZZA = "delete from pizzas where id=?";
	@Override
	public void delete(long id) {
		template.update(DELETE_PIZZA, id);
	}
	private static final String UPDATE_PIZZA = "update pizzas set naam=?, prijs=?, pikant=? where id=?";
	@Override
	public void update(Pizza pizza) {
		if (template.update(UPDATE_PIZZA, pizza.getNaam(), pizza.getPrijs(), pizza.isPikant(),
			                pizza.getId()) == 0) {
			throw new PizzaNietGevondenException();                	
	    }
	}
	@Override
	public void create(Pizza pizza) {
		Map<String, Object> kolomWaarden = new HashMap<>();
		kolomWaarden.put("naam", pizza.getNaam());
		kolomWaarden.put("prijs", pizza.getPrijs());
		kolomWaarden.put("pikant", pizza.isPikant());
		Number id = insert.executeAndReturnKey(kolomWaarden);
		pizza.setId(id.longValue());
	}
	private static final String SELECT_ALL = "select id, naam, prijs, pikant from pizzas order by id";
	@Override
	public List<Pizza> findAll(){
		return template.query(SELECT_ALL,  pizzaRowMapper);
	}
	private static final String SELECT_BY_PRIJS_BETWEEN = 
			"select id, naam, prijs, pikant from pizzas where prijs between ? and ? order by prijs";
	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot){
		return template.query(SELECT_BY_PRIJS_BETWEEN,  pizzaRowMapper, van, tot);
	}
	private static final String READ =  "select id, naam, prijs, pikant from pizzas where id=?";
	@Override
	public Optional<Pizza> read(long id){
		try {
			return Optional.of(template.queryForObject(READ, pizzaRowMapper, id));
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}
	
	private static final String SELECT_UNIEKE_PRIJZEN = "select distinct prijs from pizzas order by prijs";
	@Override
	public List<BigDecimal> findUniekePrijzen(){
		return template.query(SELECT_UNIEKE_PRIJZEN, prijsRowMapper);
	}
	private static final String SELECT_BY_PRIJS = 
			"select id, naam, prijs, pikant from pizzas where prijs=? order by naam";
	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs){
		return template.query(SELECT_BY_PRIJS, pizzaRowMapper, prijs);
	}
}



 
 
 




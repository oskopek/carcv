package sk.carcv.jsf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity(name="TConverter")
public class TemperatureConvertor {
	
	@Id
	@Column(name = "ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "CELSIUS", nullable = false,length = 4)
	private double celsius;
	
	@Column(name = "FAHRENHEIT", nullable = false,length = 4)
	private double fahrenheit;
	
	@Column(name = "INITIAL", nullable = false)
	private boolean initial = true;

	public double getCelsius() {
		return celsius;
	}

	public void setCelsius(double celsius) {
		this.celsius = celsius;
	}

	public double getFahrenheit() {
		return fahrenheit;
	}

	public boolean getInitial() {
		return initial;
	}

	public String reset() {
		initial = true;
		fahrenheit = 0;
		celsius = 0;
		return "reset";
	}

	public String celsiusToFahrenheit() {
		initial = false;
		fahrenheit = (celsius * 9 / 5) + 32;
		return "calculated";
	}

}

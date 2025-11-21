package ar.edu.unlam.pb2.Gestion;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unlam.pb2.Criaturas.Criatura;
import ar.edu.unlam.pb2.Exception.FaltaDeMaestriaException;

public class Maestro {

	private String nombre;

	private Integer nivelMaestria;
	private Map<String, Criatura> criaturas;

	public Maestro(String nombre, Integer nivelMaestria) {
		this.nombre = nombre;
		this.nivelMaestria = nivelMaestria;
		this.criaturas = new HashMap<>();
	}

	public void agregarCriatura(Criatura c) {
		criaturas.put(c.getNombre(), c);
	}

	public void entrenarCriatura(String nombreCriatura) throws FaltaDeMaestriaException {
		Criatura c = criaturas.get(nombreCriatura);
		if (c == null)
			return;

		if (this.nivelMaestria < 5) { 
			throw new FaltaDeMaestriaException("El maestro " + this.nombre + " no tiene suficiente nivel.");
		}

		c.entrenar();
	}

	public Integer getNivelMaestria() {
		return nivelMaestria;
	}

	public Map<String, Criatura> getCriaturas() {
		return criaturas;
	}

}

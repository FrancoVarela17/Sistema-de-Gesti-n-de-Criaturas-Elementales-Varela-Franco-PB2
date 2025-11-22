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

	public void agregarCriatura(Criatura criatura) {
		criaturas.put(criatura.getNombre(), criatura);
	}

	public void entrenarCriatura(String nombreCriatura) throws FaltaDeMaestriaException {
		Criatura criatura = criaturas.get(nombreCriatura);
		if (criatura == null)
			return;

		if (this.nivelMaestria < 10) { 
			throw new FaltaDeMaestriaException("Maestría insuficiente (" + this.nivelMaestria + "). Se requiere 10.");
		}

		criatura.entrenar();
	}

	public Integer getNivelMaestria() {
		return nivelMaestria;
	}

	public Map<String, Criatura> getCriaturas() {
		return criaturas;
	}

}

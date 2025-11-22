package ar.edu.unlam.pb2.Criaturas;

import ar.edu.unlam.pb2.core.Afinidad;
import ar.edu.unlam.pb2.core.EstadoEmocional;

public abstract class Criatura {
	protected String nombre;
	protected Integer energia;
	protected Afinidad afinidad;
	protected EstadoEmocional estado;

	public Criatura(String nombre, Integer energia, Afinidad afinidad) {
		this.nombre = nombre;
		this.energia = energia;
		this.afinidad = afinidad;
		this.estado = EstadoEmocional.TRANQUILA;
	}

	public abstract void entrenar();

	public void modificarEnergia(Integer cantidad) {
		this.energia += cantidad;
		if (this.energia < 0)
			this.energia = 0;

		if (this.energia > 200)
			this.energia = 200;
	}

	public void interactuar(Criatura otra) {
		if (this.esAncestral() || otra.esAncestral()) {

			Criatura ancestral = this.esAncestral() ? this : otra;
			Criatura otraCriatura = this.esAncestral() ? otra : this;

			ancestral.modificarEnergia(20);
			otraCriatura.modificarEnergia(-15);
		} else if (this.getAfinidad() == otra.getAfinidad()) {

			this.modificarEnergia(10);
			otra.modificarEnergia(10);
		} else if (esOpuesta(otra.getAfinidad())) {

			this.desestabilizar();
			otra.desestabilizar();
		}
	}

	private Boolean esOpuesta(Afinidad otra) {

		return (this.afinidad == Afinidad.AGUA && otra == Afinidad.FUEGO)
				|| (this.afinidad == Afinidad.FUEGO && otra == Afinidad.AGUA)
				|| (this.afinidad == Afinidad.AIRE && otra == Afinidad.TIERRA)
				|| (this.afinidad == Afinidad.TIERRA && otra == Afinidad.AIRE);

	}
	
	
	public String getNombre() {
		return nombre;
	}

	public Integer getEnergia() {
		return energia;
	}

	public Afinidad getAfinidad() {
		return afinidad;
	}

	
	public void pacificar() {
		this.estado = EstadoEmocional.TRANQUILA;
	}

	public void desestabilizar() {
		this.estado = EstadoEmocional.INESTABLE;
	}

	public Boolean esAncestral() {
		return false;
	}

	public EstadoEmocional getEstado() {
		return estado;
	}

	public void setEstado(EstadoEmocional estado) {
		this.estado = estado;
	}



}

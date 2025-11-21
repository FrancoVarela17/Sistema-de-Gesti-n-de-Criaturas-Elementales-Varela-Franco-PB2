package ar.edu.unlam.pb2.Criaturas;

import ar.edu.unlam.pb2.core.Afinidad;

public class CriaturaAncestral extends Criatura {

	public CriaturaAncestral(String nombre, Integer energia, Afinidad afinidad) {
		super(nombre, energia, afinidad);

	}

	@Override
	public void entrenar() {

		this.modificarEnergia(20);

	}

	@Override
	public void modificarEnergia(Integer cantidad) {
		super.modificarEnergia(cantidad);
		if (this.energia < 100)
			this.energia = 100;

	}
	
	@Override
	public Boolean esAncestral () {
		return true;
	}

}

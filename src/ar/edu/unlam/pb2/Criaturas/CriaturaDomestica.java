package ar.edu.unlam.pb2.Criaturas;

import ar.edu.unlam.pb2.core.Afinidad;

public class CriaturaDomestica extends Criatura {

	public CriaturaDomestica(String nombre, Integer energia, Afinidad afinidad) {
		super(nombre, energia, afinidad);
		
	}

	@Override
	public void entrenar() {
		this.modificarEnergia(10);

	}
	
	@Override
	public void desestabilizar () {
		
	}

}

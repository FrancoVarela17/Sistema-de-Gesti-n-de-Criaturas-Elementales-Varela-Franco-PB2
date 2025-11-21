package ar.edu.unlam.pb2.Criaturas;

import ar.edu.unlam.pb2.Exception.EnergiaDesbordadaException;
import ar.edu.unlam.pb2.core.Afinidad;

public class CriaturaSalvaje extends Criatura {

	public CriaturaSalvaje(String nombre, Integer energia, Afinidad afinidad) {
		super(nombre, energia, afinidad);

	}

	@Override
	public void entrenar() {
		
		Integer aumento =  (int) (Math.random() * 50);
		if(this.energia + aumento > 200) {
			throw new EnergiaDesbordadaException("¡La criatura salvaje ha explotado en poder!");
		}
		this.modificarEnergia(aumento);

	}

}

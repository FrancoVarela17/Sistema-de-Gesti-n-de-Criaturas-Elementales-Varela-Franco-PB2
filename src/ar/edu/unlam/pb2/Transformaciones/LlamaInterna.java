package ar.edu.unlam.pb2.Transformaciones;

import ar.edu.unlam.pb2.Criaturas.Criatura;
import ar.edu.unlam.pb2.Exception.FaltaDeMaestriaException;
import ar.edu.unlam.pb2.core.Afinidad;

public class LlamaInterna extends Transformacion {
	
	public LlamaInterna(Criatura criaturaDecorada) {
		super(criaturaDecorada);
	}
	
	@Override
	public void entrenar() {
		
		if(this.criaturaDecorada.getAfinidad() == Afinidad.FUEGO) {
			this.criaturaDecorada.modificarEnergia(30);
		} else {
			this.criaturaDecorada.desestabilizar();
		}
		
	}

}

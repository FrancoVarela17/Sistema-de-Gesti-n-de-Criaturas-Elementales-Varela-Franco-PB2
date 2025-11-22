package ar.edu.unlam.pb2.Transformaciones;

import ar.edu.unlam.pb2.Criaturas.Criatura;

public class BendicionDelRio extends Transformacion {
	
	public BendicionDelRio (Criatura criatura) {
		super(criatura);
	}
	
	@Override
	public Integer getEnergia() {
		Integer energiaBase = criaturaDecorada.getEnergia();
		
		return Math.min(energiaBase * 2, 180);
	}
}

package ar.edu.unlam.pb2.Transformaciones;

import ar.edu.unlam.pb2.Criaturas.Criatura;

public class VinculoTerrestre extends Transformacion {
	
	public VinculoTerrestre(Criatura criaturaDecorada) {
		super(criaturaDecorada);
	}
	
	@Override
	public void modificarEnergia(Integer cantidad) {
		this.criaturaDecorada.modificarEnergia(cantidad);
		
		if(this.criaturaDecorada.getEnergia().intValue()<50) {
			this.criaturaDecorada.setEnergia(50);
		}
	}
}

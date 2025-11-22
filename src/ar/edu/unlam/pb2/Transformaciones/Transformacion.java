package ar.edu.unlam.pb2.Transformaciones;

import ar.edu.unlam.pb2.Criaturas.Criatura;
import ar.edu.unlam.pb2.core.Afinidad;

public abstract class Transformacion extends Criatura {

	protected Criatura criaturaDecorada;
	private Integer transformacionesAplicadas = 1;
	
	public Transformacion (Criatura criatura) {
		super(criatura.getNombre(), criatura.getEnergia(), criatura.getAfinidad());
		this.criaturaDecorada = criatura;
		
		if(criatura instanceof Transformacion) {
			this.transformacionesAplicadas += ((Transformacion) criatura).getTransformacionesAplicadas();
		}
	}

	@Override
	public void entrenar() {
		this.criaturaDecorada.entrenar();

	}
	
	@Override
	public Afinidad getAfinidad() {
		return this.criaturaDecorada.getAfinidad();
	}
	
	public Integer getTransformacionesAplicadas() {
		return this.transformacionesAplicadas;
	}
	
	@Override
	public Integer getEnergia() { 
	    return this.criaturaDecorada.getEnergia();
	}

}

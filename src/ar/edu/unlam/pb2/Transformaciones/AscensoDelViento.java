package ar.edu.unlam.pb2.Transformaciones;

import ar.edu.unlam.pb2.Criaturas.Criatura;
import ar.edu.unlam.pb2.core.Afinidad;

public class AscensoDelViento extends Transformacion {
	
	public AscensoDelViento (Criatura criatura) {
		super(criatura);
	}
	
	@Override
	public Afinidad getAfinidad () {
		return Afinidad.AIRE;
	}
}

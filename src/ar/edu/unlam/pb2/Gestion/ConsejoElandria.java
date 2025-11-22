package ar.edu.unlam.pb2.Gestion;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ar.edu.unlam.pb2.Criaturas.Criatura;
import ar.edu.unlam.pb2.Transformaciones.Transformacion;
import ar.edu.unlam.pb2.core.Afinidad;

public class ConsejoElandria {

	public Criatura obtenerCriaturaMasPoderosa(List<Maestro> maestros) {
		return maestros.stream().flatMap(maestro -> maestro.getCriaturas().values().stream())
				.max(Comparator.comparingInt(Criatura::getEnergia)).orElse(null);
	}

	public Maestro maestroConMasTransformaciones(List<Maestro> maestros) {
		return maestros.stream().max(Comparator.comparingLong(maestro -> maestro.getCriaturas().values().stream()
				.filter(criatura -> criatura instanceof Transformacion).count())).orElse(null);
	}
	
	public Map<Afinidad, Long> cantidadPorAfinidad(List<Maestro> maestros) {
		return maestros.stream()
				.flatMap(maestro -> maestro.getCriaturas().values().stream())
				.collect(Collectors.groupingBy(Criatura::getAfinidad,Collectors.counting()));
	}
	
	public Criatura obtenerTransformacionMasCompleja(List<Maestro> maestros) {
	    return maestros.stream()
	        .flatMap(maestro -> maestro.getCriaturas().values().stream())
	        .filter(criatura -> criatura instanceof Transformacion)
	        .max(Comparator.comparingInt(criatura -> ((Transformacion) criatura).getTransformacionesAplicadas().intValue()))
	        .orElse(null);
	}
}

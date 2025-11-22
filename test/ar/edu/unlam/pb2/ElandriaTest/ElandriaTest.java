package ar.edu.unlam.pb2.ElandriaTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ar.edu.unlam.pb2.Exception.EnergiaDesbordadaException;
import ar.edu.unlam.pb2.Exception.FaltaDeMaestriaException;
import ar.edu.unlam.pb2.Gestion.ConsejoElandria;
import ar.edu.unlam.pb2.Gestion.Maestro;
import ar.edu.unlam.pb2.Transformaciones.AscensoDelViento;
import ar.edu.unlam.pb2.Transformaciones.BendicionDelRio;
import ar.edu.unlam.pb2.Transformaciones.LlamaInterna;
import ar.edu.unlam.pb2.Transformaciones.Transformacion;
import ar.edu.unlam.pb2.Transformaciones.VinculoTerrestre;
import ar.edu.unlam.pb2.core.Afinidad;
import ar.edu.unlam.pb2.core.EstadoEmocional;
import ar.edu.unlam.pb2.Criaturas.Criatura;
import ar.edu.unlam.pb2.Criaturas.CriaturaAncestral;
import ar.edu.unlam.pb2.Criaturas.CriaturaDomestica;
import ar.edu.unlam.pb2.Criaturas.CriaturaSalvaje;

public class ElandriaTest {

	@Test(expected = FaltaDeMaestriaException.class)
	public void queAlTenerMaestriaInsuficienteDebeLanzarFaltaDeMaestriaException() throws FaltaDeMaestriaException {

		Maestro novato = new Maestro("Novato", 5);
		Criatura dragon = new CriaturaSalvaje("Furia", 100, Afinidad.FUEGO);
		novato.agregarCriatura(dragon);

		novato.entrenarCriatura("Furia");
	}

	@Test
	public void queAltenerMaestriaSuficienteEntrenaExitosamente() {

		Maestro experto = new Maestro("Experto", 15);
		Criatura oveja = new CriaturaDomestica("Ovejita", 50, Afinidad.TIERRA);
		experto.agregarCriatura(oveja);

		try {
			experto.entrenarCriatura("Ovejita");
		} catch (FaltaDeMaestriaException e) {
			fail("No debería lanzar excepción, el maestro tiene el nivel requerido.");
		}

		assertEquals(Integer.valueOf(60), oveja.getEnergia());
	}

	@Test
	public void queAlAplicarBendicionDelRioDuplicaEnergiaRespetandoElTope() {

		Criatura pez = new CriaturaDomestica("Pez", 50, Afinidad.AGUA);
		Criatura pezBendito = new BendicionDelRio(pez);

		assertEquals(Integer.valueOf(100), pezBendito.getEnergia());

		Criatura tiburon = new CriaturaSalvaje("Tiburon", 150, Afinidad.AGUA);
		Criatura tiburonBendito = new BendicionDelRio(tiburon);

		assertEquals(Integer.valueOf(180), tiburonBendito.getEnergia());
	}

	@Test
	public void queAlAplicarTransformacionSeMantieneElEstadoBase() {

		Criatura aguila = new CriaturaSalvaje("Aguila", 100, Afinidad.AIRE);
		Criatura aguilaAscenso = new AscensoDelViento(aguila);

		try {
			Maestro experto = new Maestro("Experto", 15);
			experto.agregarCriatura(aguilaAscenso);
			experto.entrenarCriatura("Aguila");

		} catch (Exception e) {
			fail("No debería haber excepciones en este test.");
		}

		assertTrue(aguila.getEnergia() > 100);
	}

	@Test
	public void queAlInteractuarConAfinidadCompartidaSeIncrementaLaEnergia() {

		Criatura agua1 = new CriaturaDomestica("Río", 100, Afinidad.AGUA);
		Criatura agua2 = new CriaturaSalvaje("Mar", 80, Afinidad.AGUA);

		agua1.interactuar(agua2);

		assertEquals(Integer.valueOf(110), agua1.getEnergia());
		assertEquals(Integer.valueOf(90), agua2.getEnergia());
	}

	@Test
	public void queAlInteractuarConAfinidadOpuestaSeProduceDesestabilizacion() {

		Criatura fuego = new CriaturaSalvaje("Lava", 100, Afinidad.FUEGO);
		Criatura agua = new CriaturaSalvaje("Vapor", 80, Afinidad.AGUA);

		fuego.interactuar(agua);
		
		assertEquals(EstadoEmocional.INESTABLE, fuego.getEstado());
		assertEquals(EstadoEmocional.INESTABLE, agua.getEstado());

		assertEquals(Integer.valueOf(100), fuego.getEnergia());

	}
	
	@Test
	public void queAlInteractuarConAfinidadNeutralNoOcurreNada() {
		
		Criatura agua = new CriaturaDomestica("Arroyo", 100, Afinidad.AGUA);
		Criatura tierra = new CriaturaDomestica("Barro", 100, Afinidad.TIERRA);
		
		agua.interactuar(tierra);
		
		assertEquals(Integer.valueOf(100), agua.getEnergia());
		assertEquals(EstadoEmocional.TRANQUILA, agua.getEstado());
		assertEquals(Integer.valueOf(100), tierra.getEnergia());
		assertEquals(EstadoEmocional.TRANQUILA, tierra.getEstado());
	}

	@Test
	public void queAlRealizarInteraccionAncestralSeGanaEnergiaRespetandoElMinimo() {

		Criatura ancestral = new CriaturaAncestral("Titan", 120, Afinidad.TIERRA);
		Criatura domestica = new CriaturaDomestica("Peque", 10, Afinidad.AIRE);

		ancestral.interactuar(domestica);

		assertEquals(Integer.valueOf(140), ancestral.getEnergia());

		assertEquals(Integer.valueOf(0), domestica.getEnergia());

	}

	@Test(expected = EnergiaDesbordadaException.class)
	public void queAlSuperarElLimiteDeEnergiaLaCriaturaSalvajeLanzaException() throws FaltaDeMaestriaException {
		Maestro experto = new Maestro("Mago", 50);
		Criatura salvaje = new CriaturaSalvaje("Volcan", 190, Afinidad.FUEGO);

		experto.agregarCriatura(salvaje);

		for (int i = 0; i < 10; i++) {
			experto.entrenarCriatura("Volcan");
		}
		fail("La criatura debería haber explotado en poder.");
	}

	@Test
	public void queAlSerCriaturaAncestralSeMantieneLaEnergiaMinima() {

		Criatura ancestral = new CriaturaAncestral("Gaia", 110, Afinidad.TIERRA);

		ancestral.modificarEnergia(-50);

		assertEquals(Integer.valueOf(100), ancestral.getEnergia());
	}

	@Test
	public void queAlSerCriaturaDomesticaNoSeDestabiliza() {

		Criatura domestica = new CriaturaDomestica("Perro", 100, Afinidad.AGUA);

		domestica.desestabilizar();

		assertEquals(EstadoEmocional.TRANQUILA, domestica.getEstado());
	}

	@Test
	public void queAlGenerarReporteSeObtieneCantidadPorAfinidad() {

		Maestro maestro1 = new Maestro("Juan", 20);
		maestro1.agregarCriatura(new CriaturaDomestica("Pez", 50, Afinidad.AGUA));
		maestro1.agregarCriatura(new CriaturaSalvaje("Dragon", 150, Afinidad.FUEGO));

		Maestro maestro2 = new Maestro("Ana", 30);
		maestro2.agregarCriatura(new CriaturaDomestica("Vaca", 80, Afinidad.TIERRA));
		maestro2.agregarCriatura(new CriaturaAncestral("Leviatan", 190, Afinidad.AGUA));

		ConsejoElandria consejo = new ConsejoElandria();

		Map<Afinidad, Long> conteo = consejo.cantidadPorAfinidad(Arrays.asList(maestro1, maestro2));

		assertEquals(Long.valueOf(2), conteo.get(Afinidad.AGUA));
		assertEquals(Long.valueOf(1), conteo.get(Afinidad.FUEGO));
		assertEquals(Long.valueOf(1), conteo.get(Afinidad.TIERRA));
		assertFalse(conteo.containsKey(Afinidad.AIRE));

	}

	@Test
	public void queAlGenerarReporteConListaVaciaRetornaNull() {
		ConsejoElandria consejo = new ConsejoElandria();
		List<Maestro> listaVacia = new ArrayList<>();

		Criatura masPoderosa = consejo.obtenerCriaturaMasPoderosa(listaVacia);
		assertNull(masPoderosa);

		Maestro masTransformaciones = consejo.maestroConMasTransformaciones(listaVacia);
		assertNull(masTransformaciones);
	}

	@Test
	public void queAlAplicarTransformacionSeDelegaCorrectamenteElGetterAfinidad() {

		Criatura base = new CriaturaDomestica("Roca", 100, Afinidad.TIERRA);
		Criatura transformada = new BendicionDelRio(base);

		Afinidad afinidadResultante = transformada.getAfinidad();

		assertEquals(Afinidad.TIERRA, afinidadResultante);
	}

	@Test
	public void queAlAnidadTransformacionesSeSumanCorrectamenteLasCapas() {

		Criatura base = new CriaturaDomestica("Base", 50, Afinidad.AGUA);

		Criatura capa1 = new BendicionDelRio(base);
		assertEquals(Integer.valueOf(1), ((Transformacion) capa1).getTransformacionesAplicadas());

		Criatura capa2 = new AscensoDelViento(capa1);
		assertEquals(Integer.valueOf(2), ((Transformacion) capa2).getTransformacionesAplicadas());

		Criatura capa3 = new BendicionDelRio(capa2);
		assertEquals(Integer.valueOf(3), ((Transformacion) capa3).getTransformacionesAplicadas());
	}

	@Test
	public void queAlModificarEnergiaNoSuperaElTopeDe200() {

		Criatura criatura = new CriaturaDomestica("Super", 195, Afinidad.FUEGO);

		criatura.modificarEnergia(10);

		assertEquals(Integer.valueOf(200), criatura.getEnergia());
	}

	@Test
	public void queAlIdentificarLaCriaturaSeSeleccionaLaTransformacionMasCompleja() {

		Criatura dragonEscama = new BendicionDelRio(new CriaturaDomestica("DragonEscama", 50, Afinidad.AGUA));

		Criatura golemMistico = new BendicionDelRio(
				new AscensoDelViento(new BendicionDelRio(new CriaturaSalvaje("GolemMistico", 100, Afinidad.FUEGO))));

		Criatura titanPuro = new CriaturaAncestral("TitanPuro", 150, Afinidad.TIERRA);

		Maestro maestroEntrenador = new Maestro("MaestroEntrenador", 20);
		maestroEntrenador.agregarCriatura(dragonEscama);
		maestroEntrenador.agregarCriatura(titanPuro);

		Maestro maestroSuperior = new Maestro("MaestroSuperior", 30);
		maestroSuperior.agregarCriatura(golemMistico);

		ConsejoElandria consejo = new ConsejoElandria();

		Criatura criaturaMasDecorada = consejo
				.obtenerTransformacionMasCompleja(Arrays.asList(maestroEntrenador, maestroSuperior));

		assertEquals("GolemMistico", criaturaMasDecorada.getNombre());
		assertEquals(Integer.valueOf(3), ((Transformacion) criaturaMasDecorada).getTransformacionesAplicadas());
	}
	
	@Test
	public void queLlamaInternaDaEnergiaSiLaAfinidadEsFuego() {
		
		Criatura dragon = new CriaturaSalvaje("Dragoncito", 100, Afinidad.FUEGO);
		Criatura conLlama = new LlamaInterna(dragon);
		
		conLlama.entrenar();
		
		assertEquals(EstadoEmocional.TRANQUILA, conLlama.getEstado());
		assertEquals(Integer.valueOf(130), conLlama.getEnergia());
	}
	
	@Test
	public void queLlamaInternaDesestabilizaSiLaAfinidadNoEsFuego() {
		
		Criatura sirena = new CriaturaDomestica("Sirenita", 80, Afinidad.AGUA);
		Criatura conLlama = new LlamaInterna(sirena);
		
		conLlama.entrenar();
		conLlama.desestabilizar();
		assertEquals(EstadoEmocional.INESTABLE, conLlama.getEstado());
		assertEquals(Integer.valueOf(80), conLlama.getEnergia());
	}
	
	@Test
	public void queVinculoTerrestreMantengaLaEnergiaMinimaDe50() {
		
		Criatura enano = new CriaturaDomestica("Rocker", 70, Afinidad.TIERRA);
		Criatura conVinculo = new VinculoTerrestre(enano);
		
		conVinculo.modificarEnergia(-10);
		assertEquals(Integer.valueOf(60), conVinculo.getEnergia());
		
		conVinculo.modificarEnergia(-50);
		
		assertEquals(Integer.valueOf(50), conVinculo.getEnergia());
	}
	
	@Test
	public void queVinculoTerrestreNoAjustaEnergiaSiEsIgualOSuperiorA50() {
		
		Criatura dragonLimite = new CriaturaDomestica("DragonLimite", 50, Afinidad.TIERRA);
		Criatura conVinculoLimite = new VinculoTerrestre(dragonLimite);
		
		conVinculoLimite.modificarEnergia(0);
		assertEquals(Integer.valueOf(50), conVinculoLimite.getEnergia());
		
		Criatura dragonSuperior = new CriaturaDomestica("DragonSuperior", 70, Afinidad.TIERRA);
		Criatura conVinculoSuperior = new VinculoTerrestre(dragonSuperior);
		
		conVinculoSuperior.modificarEnergia(-5);
		assertEquals(Integer.valueOf(65), conVinculoSuperior.getEnergia());
	}
	
}


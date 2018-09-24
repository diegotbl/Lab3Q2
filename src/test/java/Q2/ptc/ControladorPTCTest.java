package Q2.ptc;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorPTCTest {
	// Item a
	@Test
	public void ConstructorTest() {
		Sensor sensorMock = mock(Sensor.class);
		Datacenter datacenterMock = mock(Datacenter.class);
		PainelCondutor painelCondutorMock = mock(PainelCondutor.class);
		
		ControladorPTC controlador = Mockito.spy(new ControladorPTC(sensorMock, datacenterMock, painelCondutorMock));
		
		assertEquals(sensorMock, controlador.getSensor());
		assertEquals(datacenterMock, controlador.getDatacenter());
		assertEquals(painelCondutorMock, controlador.getPainelCondutor());
	}
	
	// Item b
	@Test
	public void IsNotCruzamentoTest() {
		Sensor sensorMock = mock(Sensor.class);
		Datacenter datacenterMock = mock(Datacenter.class);
		PainelCondutor painelCondutorMock = mock(PainelCondutor.class);
		
		when(sensorMock.isCruzamento()).thenReturn(false);
		when(sensorMock.getVelocidade()).thenReturn(50.0);
		
		ControladorPTC controlador = new ControladorPTC(sensorMock, datacenterMock, painelCondutorMock);
		controlador.run();
		
		verify(painelCondutorMock).imprimirAviso("50.0", 1);
	}
	
	// Item c
	@Test
	public void IsCruzamentoHighVelocityTest() {
		Sensor sensorMock = mock(Sensor.class);
		Datacenter datacenterMock = mock(Datacenter.class);
		PainelCondutor painelCondutorMock = mock(PainelCondutor.class);
		
		when(sensorMock.isCruzamento()).thenReturn(true);
		when(sensorMock.getVelocidade()).thenReturn(101.0);
		
		ControladorPTC controlador = new ControladorPTC(sensorMock, datacenterMock, painelCondutorMock);
		when(painelCondutorMock.imprimirAviso("Velocidade alta", 1)).thenReturn(true);
		controlador.run();
		
		verify(painelCondutorMock, never()).diminuiVelocidadeTrem(20);
	}
	
	// Item d
	@Test
	public void IsCruzamentoLowVelocityTest() {
		Sensor sensorMock = mock(Sensor.class);
		Datacenter datacenterMock = mock(Datacenter.class);
		PainelCondutor painelCondutorMock = mock(PainelCondutor.class);
		
		when(sensorMock.isCruzamento()).thenReturn(true);
		when(sensorMock.getVelocidade()).thenReturn(19.0);
		
		ControladorPTC controlador = new ControladorPTC(sensorMock, datacenterMock, painelCondutorMock);
		when(painelCondutorMock.imprimirAviso("Velocidade Baixa", 1)).thenReturn(false);
		controlador.run();
		
		verify(painelCondutorMock).aceleraVelocidadeTrem(20);
	}

}

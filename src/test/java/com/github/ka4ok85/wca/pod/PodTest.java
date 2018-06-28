package com.github.ka4ok85.wca.pod;

import static org.junit.Assert.*;

import org.junit.Test;

public class PodTest {

	@Test
	public void testGetOAuthEndpoint() {
		String oAuthEndpoint = Pod.getOAuthEndpoint(2);
		assertEquals(oAuthEndpoint, "https://api2.silverpop.com/oauth/token");
	}

	@Test(expected = RuntimeException.class)
	public void testGetOAuthEndpointThrowsException() {
		String oAuthEndpoint = Pod.getOAuthEndpoint(234);
		assertEquals(oAuthEndpoint, "https://api2.silverpop.com/oauth/token");
	}

	@Test
	public void testXMLAPIEndpoint() {
		String xMLAPIEndpoint = Pod.getXMLAPIEndpoint(4);
		assertEquals(xMLAPIEndpoint, "https://api4.silverpop.com/XMLAPI");
	}

	@Test(expected = RuntimeException.class)
	public void testXMLAPIEndpointThrowsException() {
		String xMLAPIEndpoint = Pod.getXMLAPIEndpoint(456);
		assertEquals(xMLAPIEndpoint, "https://api4.silverpop.com/XMLAPI");
	}

	@Test
	public void testSFTPHostName() {
		String sFTPHostName = Pod.getSFTPHostName(3);
		assertEquals(sFTPHostName, "transfer3.silverpop.com");
	}

	@Test(expected = RuntimeException.class)
	public void testSFTPHostNameThrowsException() {
		String sFTPHostName = Pod.getSFTPHostName(345);
		assertEquals(sFTPHostName, "transfer3.silverpop.com");
	}

}

package com.github.ka4ok85.wca.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ka4ok85.wca.Engage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
@TestPropertySource(properties = { "podNumber=1", "clientId=test client id", "clientSecret=test client secret",
		"refreshToken=test token", })
public class SpringConfigTest {
	@Autowired
	ApplicationContext context;

	@Test
	public void testEngage() {
		Engage testEngage = context.getBean(Engage.class);
		assertEquals(testEngage.getClass(), Engage.class);
	}
}
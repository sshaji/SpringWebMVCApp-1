package com.shaji.javaee.repository.tests;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/shaji/javaee/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OfferRepositoryTest extends TestCase {

	

}

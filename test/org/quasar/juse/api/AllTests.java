package org.quasar.juse.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author fba
 * Test suite
 */
@RunWith(Suite.class)
@SuiteClasses({ JUSE_BasicFacadeTest.class, JUSE_ProgramingFacadeTest.class, JUSE_ProgramingByContractFacadeTest.class })
public class AllTests
{
	
}

package pg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BMSTest1.class, CarSensorTest.class, ProcessingUnitTest.class,
		UseCases.class })
public class AllTests {

}

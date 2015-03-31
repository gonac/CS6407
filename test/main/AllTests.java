package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BMSTest1.class, CarSensorTest.class,ProcessingUnitTest.class})
public class AllTests {

}

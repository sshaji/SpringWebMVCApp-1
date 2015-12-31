import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.shaji.javaee.repository.tests.OfferRepositoryTest;
import com.shaji.javaee.repository.tests.UserRepositoryTest;

@RunWith(Suite.class)
@SuiteClasses({ UserRepositoryTest.class, OfferRepositoryTest.class })
public class AllTests {

}

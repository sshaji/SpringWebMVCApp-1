import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.shaji.javaee.offers.model.dao.tests.OffersDAOTest;
import com.shaji.javaee.offers.model.dao.tests.UsersDAOTest;

@RunWith(Suite.class)
@SuiteClasses({ UsersDAOTest.class, OffersDAOTest.class })
public class AllTests {

}

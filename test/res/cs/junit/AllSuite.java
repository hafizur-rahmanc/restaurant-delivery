package res.cs.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UserDAOTest.class, ItemDAOTest.class, ReviewDAOTest.class, StoreDAOTest.class, OrderDAOTest.class,PaymentDAOTest.class})
public class AllSuite {

}

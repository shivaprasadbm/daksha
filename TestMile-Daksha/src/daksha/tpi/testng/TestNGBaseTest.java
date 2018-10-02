package daksha.tpi.testng;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import daksha.Daksha;
import daksha.core.batteries.config.Configuration;
import daksha.core.batteries.config.ContextConfiguration;
import daksha.tpi.TestContext;

public class TestNGBaseTest {
	private ThreadLocal<String> testContextName = new ThreadLocal<String>();
	
	@BeforeSuite
	public void initDaksha() throws Exception {
		String rootDir = this.getRootDir();
		if (rootDir == null) {
			Daksha.init();
		} else {
			Daksha.init(rootDir);
		}
		this.setCentralOptions();
		Daksha.freezeCentralConfig();
	}
	
	protected void setCentralOptions() throws Exception {
		// Proceed with defaults if not overriden.
	}
	
	protected String getRootDir() throws Exception {
		// Proceed with default if null;
		return null;
	}
	
	@BeforeTest
	public void initContext(ITestContext testContext) throws Exception {
		if (testContext.getName().toLowerCase().equals("default test")) {
			this.testContextName.set("default");
		} else {
			this.testContextName.set(testContext.getName());
			Daksha.registerContext(new TestNGContext(testContext));
		}
	}
	
	private String getTestContextName() {
		return this.testContextName.get();
	}
	
	protected TestContext getContext() throws Exception {
		return Daksha.getTestContext(this.getTestContextName());
	}

}

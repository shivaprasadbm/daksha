package daksha.core.batteries.config;

import java.util.Map;

public class ContextConfiguration extends BaseConfiguration{
	
	 public ContextConfiguration(Configuration centralConf, Map<String, String> contextOptions) throws Exception {
		 super(centralConf);
		 this.add(contextOptions);
	 }
	 
	 public ContextConfiguration(Configuration centralConf) throws Exception {
		 super(centralConf);
	 }

}

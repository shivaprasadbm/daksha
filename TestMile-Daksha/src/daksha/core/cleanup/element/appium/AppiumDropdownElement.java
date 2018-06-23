/*******************************************************************************
 * Copyright 2015-18 Test Mile Software Testing Pvt Ltd
 * 
 * Website: www.TestMile.com
 * Email: support [at] testmile.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package daksha.core.cleanup.element.appium;

import daksha.core.cleanup.automator.ConcreteGuiAutomator;
import daksha.core.cleanup.element.proxy.GuiElementProxy;
import daksha.core.cleanup.element.selenium.BaseSeleniumDropdownElement;
import daksha.tpi.cleanup.gui.Gui;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppiumDropdownElement extends BaseSeleniumDropdownElement<AppiumDriver<MobileElement>,MobileElement>{
	
	public AppiumDropdownElement(Gui page, ConcreteGuiAutomator<AppiumDriver<MobileElement>,MobileElement> automator, GuiElementProxy eProxy) throws Exception{
		super(page,automator, eProxy);
	}
	
	public AppiumDropdownElement(ConcreteGuiAutomator<AppiumDriver<MobileElement>,MobileElement> automator, GuiElementProxy eProxy) throws Exception{
		this(null, automator, eProxy);
	}
}
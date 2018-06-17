package daksha.tpi.leaping.generator.appium;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;

import daksha.Daksha;
import daksha.core.batteries.config.Batteries;
import daksha.core.batteries.exceptions.Problem;
import daksha.core.leaping.automator.appium.AppiumNativeUiDriver;
import daksha.core.leaping.automator.appium.AppiumWebUiDriver;
import daksha.core.leaping.enums.AppiumMobilePlatformType;
import daksha.core.leaping.enums.UiAutomatorPropertyType;
import daksha.tpi.leaping.automator.GuiAutomator;
import daksha.tpi.leaping.enums.UiAutomationContext;
import daksha.tpi.leaping.interfaces.AppiumBuilder;
import daksha.tpi.leaping.interfaces.AppimWrapper;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumBuilder {
	private DesiredCapabilities defaultCaps = new DesiredCapabilities();
	private DesiredCapabilities otherCaps = new DesiredCapabilities();
	private UiAutomationContext context = UiAutomationContext.MOBILE_WEB;
	private int waitTime = 0;
	private AppiumMobilePlatformType platformType = null;
	private String appPath = null;
	
	public AppiumBuilder() throws Exception{
		switch(context){
		case MOBILE_WEB: waitTime = Batteries.value(UiAutomatorPropertyType.BROWSER_MOBILE_MAXWAIT).asInt(); break;
		case MOBILE_NATIVE: waitTime = Batteries.value(UiAutomatorPropertyType.APP_MOBILE_MAXWAIT).asInt(); break;
		default: waitTime = Batteries.value(UiAutomatorPropertyType.APP_MOBILE_MAXWAIT).asInt();
		}
		String platform = Batteries.value(UiAutomatorPropertyType.MOBILE_PLATFORM_NAME).asString();
		if (!Daksha.isAllowedAppiumPlatform(platform)){
			throwUnsupportedPlatformException("constructor", platform);
		}
		platformType = AppiumMobilePlatformType.valueOf(platform.toUpperCase());
		this.appPath = Batteries.value(UiAutomatorPropertyType.APP_MOBILE_PATH).asString();
	}
	
	private String getAppPath(){
		return this.appPath;
	}
	
	
	public void context(UiAutomationContext context){
		this.context = context;
	}
	
	
	public void appPath(String path){
		this.context(UiAutomationContext.MOBILE_NATIVE);
		otherCaps.setCapability(MobileCapabilityType.APP, path);
	}
	
	
	public void platformName(String name){
		otherCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, name);
	}
	
	
	public void platformVersion(String version){
		otherCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
	}
	
	
	public void deviceName(String name){
		otherCaps.setCapability(MobileCapabilityType.DEVICE_NAME, name);
	}
	
	
	public void udid(String id){
		otherCaps.setCapability(MobileCapabilityType.UDID, id);
	}
	
	
	public void capabilities(DesiredCapabilities caps){
		otherCaps.merge(caps);
	}
	
	
	public GuiAutomator build() throws Exception{
		populateDefaultCapabilities(platformType, defaultCaps);
		AppimWrapper appium = null;
		switch (this.context){
		case MOBILE_NATIVE:
			appium = new AppiumNativeUiDriver();
			break;
		case MOBILE_WEB:
			appium = new AppiumWebUiDriver();
			break;
		default:
			throwUnsupportedAutomationContextException(context);
		}
		appium.init();
		appium.setWaitTime(this.waitTime);
		appium.setPlatformType(this.platformType);
		defaultCaps.merge(otherCaps);
		appium.setCapabilities(defaultCaps.asMap());
		appium.load();
		return appium;
	}
	
	public GuiAutomator throwUnsupportedAutomationContextException(UiAutomationContext context) throws Exception{
		throw new Problem(
				Batteries.getComponentName("UI_AUTOMATOR"),
				"Appium Builder",
				"build",
				Daksha.problem.FACTORY_AUTOMATOR_UNSUPPORTED_CONTEXT,
				Batteries.getProblemText(
						Daksha.problem.FACTORY_AUTOMATOR_UNSUPPORTED_CONTEXT,
						Daksha.getAutomationContextName(context))
			);		
	}
	
	private void setHttpProxy(Proxy proxy, String proxyString) {
		proxy.setHttpProxy(proxyString);
	}

	private void setSslProxy(Proxy proxy, String proxyString) {
		proxy.setSslProxy(proxyString);
	}
	
	private void populateDefaultCapabilities(AppiumMobilePlatformType platform, DesiredCapabilities capabilities) throws Exception {
		if (Batteries.value(UiAutomatorPropertyType.BROWSER_MOBILE_PROXY_ON).asBoolean()){
			Proxy proxy = new Proxy();
			String p = Batteries.value(UiAutomatorPropertyType.BROWSER_MOBILE_PROXY_HOST).asString() + ":" + Batteries.value(UiAutomatorPropertyType.BROWSER_MOBILE_PROXY_PORT).asString();
			setHttpProxy(proxy, p);
			setSslProxy(proxy, p);
			capabilities.setCapability("proxy", proxy);
		}
		switch(context){
		case MOBILE_WEB: setMobileWebCapabilities(platform, capabilities) ; break;
		case MOBILE_NATIVE: setMobileNativeCapabilities(platform, capabilities); break;
		default: throw new Exception("Unsupported automation context for Appium. Allowed: MOBILE_WEB/MOBILE_NATIVE");
		}
	}
	
	private void setMobileNativeCapabilities(AppiumMobilePlatformType platform, DesiredCapabilities capabilities) throws Exception {		
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Daksha.getAppiumPlatformString(platform));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, Batteries.value(UiAutomatorPropertyType.MOBILE_PLATFORM_VERSION).asString());
		capabilities.setCapability(MobileCapabilityType.APP, this.getAppPath());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Batteries.value(UiAutomatorPropertyType.MOBILE_DEVICE_NAME).asString());
		if (!Batteries.value(UiAutomatorPropertyType.MOBILE_DEVICE_UDID).isNull()){
			capabilities.setCapability(MobileCapabilityType.UDID, Batteries.value(UiAutomatorPropertyType.MOBILE_DEVICE_UDID).asString());
		}
	}

	private void setMobileWebCapabilities(AppiumMobilePlatformType platform, DesiredCapabilities capabilities) throws Exception {
		String browser = Batteries.value(UiAutomatorPropertyType.BROWSER_MOBILE_DEFAULT).asString();
		if (!Daksha.isAllowedAppiumBrowser(platform, browser)){
			throwUnsupportedBrowserException("setMobileCapabilities", platform, browser);
		}
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Daksha.getAppiumPlatformString(platform));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,  Batteries.value(UiAutomatorPropertyType.MOBILE_PLATFORM_VERSION).asString());
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, Daksha.getAppiumBrowserString(browser));
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Batteries.value(UiAutomatorPropertyType.MOBILE_DEVICE_NAME).asString());
		if (!Batteries.value(UiAutomatorPropertyType.MOBILE_DEVICE_UDID).isNull()){
			capabilities.setCapability(MobileCapabilityType.UDID, Batteries.value(UiAutomatorPropertyType.MOBILE_DEVICE_UDID).asString());
		}
	}
	
	/**********************************************************************************
	**					EXCEPTIONS											
	**********************************************************************************/
	
	protected void throwAppiumAutomatorException(String action, String code, String message) throws Exception {
		throw new Problem(
				Batteries.getConfiguredName("COMPONENT_NAMES", "APPIUM_AUTOMATOR"),
				this.getClass().getSimpleName(),
				action,
				code,
				message
				);		
	}
	
	protected void throwUnsupportedPlatformException(String methodName, String platform) throws Exception {
		throwAppiumAutomatorException(
				methodName,
				Daksha.problem.APPIUM_UNSUPPORTED_PLATFORM,
				Batteries.getProblemText(
						Daksha.problem.APPIUM_UNSUPPORTED_PLATFORM,
						platform
						)
				);
	}

	protected void throwUnsupportedBrowserException(String methodName, AppiumMobilePlatformType platform, String browser) throws Exception {
		throwAppiumAutomatorException(
				methodName,
				Daksha.problem.APPIUM_UNSUPPORTED_BROWSER,
				Batteries.getProblemText(
						Daksha.problem.APPIUM_UNSUPPORTED_BROWSER,
						browser,
						Daksha.getAppiumPlatformString(platform)
						)
				);
	}

}
package daksha.tpi.enums;

public enum DakshaOption {
	// Daksha Directories
	ROOT_DIR,
	LOG_DIR,
	TOOLS_DIR,
	APPS_DIR,
	GUIAUTO_NAMESPACE_DIR,
	SCREENSHOTS_DIR,

	// Test Run
	TESTRUN_ENVIRONMENT,
	TESTRUN_TARGET_PLATFORM,
	TESTRUN_TARGET_PLATFORM_VERSION,
	
	// Browser (Common)
	BROWSER_NAME,
	BROWSER_VERSION,
	BROWSER_MAXIMIZE,
	BROWSER_DIM_WIDTH,
	BROWSER_DIM_HEIGHT,
	BROWSER_BIN_PATH,
	BROWSER_MOBILE_PROXY_HOST,
	BROWSER_MOBILE_PROXY_ON,
	BROWSER_MOBILE_PROXY_PORT,
	BROWSER_PC_PROXY_HOST,
	BROWSER_PC_PROXY_ON,
	BROWSER_PC_PROXY_PORT,
	
	// Browser Specific
	FIREFOX_WINDOWNAME,
	CHROME_WINDOWNAME,
	SAFARI_WINDOWNAME,
	
	// Application
	APP_URL,
	
	// General Gui Automation
	GUIAUTO_CONTEXT,
	GUIAUTO_SCROLL_PIXELS,
	GUIAUTO_SWIPE_TOP,
	GUIAUTO_SWIPE_BOTTOM,
	GUIAUTO_SWIPE_MAX_WAIT,
	GUIAUTO_MAX_WAIT,
	
	// Mobile automation
	MOBILE_DEVICE_NAME,
	MOBILE_DEVICE_UDID,
	MOBILE_APP_FILE_PATH,

	// Selenium
	SELENIUM_DRIVERS_DIR, // Place to find all drivers
	SELENIUM_DRIVER_PATH, // Overrides general look-up. If provided this absolute path is used.

	// Appium
	APPIUM_HUB_URL,
	APPIUM_AUTO_LAUNCH,
	
	// Image comparison
	IMAGE_COMPARISON_MIN_SCORE,
}

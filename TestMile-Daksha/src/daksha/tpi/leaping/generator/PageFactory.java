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
package daksha.tpi.leaping.generator;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import daksha.ErrorType;
import daksha.core.batteries.exceptions.Problem;
import daksha.tpi.enums.DakshaOption;
import daksha.tpi.leaping.automator.GuiAutomator;
import daksha.tpi.leaping.pageobject.App;
import daksha.tpi.leaping.pageobject.BaseApp;
import daksha.tpi.leaping.pageobject.BasePage;
import daksha.tpi.leaping.pageobject.Page;
import daksha.tpi.sysauto.utils.FileSystemUtils;

public class PageFactory {
	
	public static App getSimpleApp(String name, GuiAutomator<?,?> automator, String appMapsRootDir) throws Exception{
		String consideredPath = appMapsRootDir;
		if (!FileSystemUtils.isDir(consideredPath)){
			consideredPath = FileSystemUtils.getCanonicalPath(automator.getTestContext().getConfig().value(DakshaOption.DIRECTORY_PROJECT_UI_MAPS).asString() + "/" + consideredPath);
			if (!FileSystemUtils.isDir(consideredPath)){
				throw new Problem(
						"UI Automator", 
						"Page Mapper", 
						"getFileMapper", 
						ErrorType.APP_MAP_DIR_NOT_A_DIR, 
						String.format(ErrorType.APP_MAP_DIR_NOT_A_DIR, consideredPath)
					);				
			} 
		}
		App app = new BaseApp(name, automator, appMapsRootDir + File.separator + "app.ini");
		File d = new File(consideredPath + File.separator + "pages");
		if (!FileSystemUtils.isDir(consideredPath)){
			for (File path: d.listFiles()){
				app.registerPage(FilenameUtils.getBaseName(path.getAbsolutePath()), path.getAbsolutePath());
			}
		}
		return app;
	}

	public static Page getPage(GuiAutomator<?,?> automator, String mapPath) throws Exception {
		return new BasePage(FileSystemUtils.getFileName(mapPath), automator, mapPath);
	}
}

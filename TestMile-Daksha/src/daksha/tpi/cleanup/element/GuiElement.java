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
package daksha.tpi.cleanup.element;

import daksha.core.cleanup.actions.element.AttributesInquirer;
import daksha.core.cleanup.actions.element.BasicActionHandler;
import daksha.core.cleanup.actions.element.ChainActionHandler;
import daksha.core.cleanup.actions.element.CheckBoxActionHandler;
import daksha.core.cleanup.actions.element.DropdownActionsHandler;
import daksha.core.cleanup.actions.element.ElementIdentification;
import daksha.core.cleanup.actions.element.ImageBasedActionHandler;
import daksha.core.cleanup.actions.element.State;
import daksha.core.cleanup.actions.element.WebActionHandler;
import daksha.core.cleanup.element.ManagedSingleElement;

public interface GuiElement extends 		ManagedSingleElement,
											ImageBasedActionHandler,
											BasicActionHandler,
											AttributesInquirer,
											ChainActionHandler,
											CheckBoxActionHandler,
											DropdownActionsHandler,
											WebActionHandler,
											ElementIdentification,
											State{

	Object throwUnsupportedActionException(String action) throws Exception;

	Object throwUnsupportedSelectActionException(String action)
			throws Exception;

}

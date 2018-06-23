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
package daksha.core.cleanup.element;

import java.util.List;

import daksha.core.cleanup.automator.ConcreteGuiAutomator;
import daksha.core.cleanup.element.proxy.GuiElementProxy;
import daksha.core.cleanup.picker.GuiElementPicker;
import daksha.core.cleanup.picker.GuiElementMetaData;
import daksha.tpi.cleanup.element.GuiElement;
import daksha.tpi.cleanup.element.MultiGuiElement;
import daksha.tpi.cleanup.enums.GuiElementType;
import daksha.tpi.cleanup.gui.Gui;

public abstract class BaseConcreteSingleGuiElement<D,E> extends BaseManagedConcreteGuiElement<D,E,GuiElementProxy> implements ConcreteGuiElement<D,E>{
	private E toolElement = null;
	private GuiElementMetaData emd = null;
	private GuiElementType elementType = null;
	private GuiElementPicker<D,E> picker = null;
	private int waitTime = 10;
	
	public BaseConcreteSingleGuiElement(ConcreteGuiAutomator<D,E> automator, GuiElementProxy proxy) {
		super(automator, proxy);
		populateObjectVars(automator, proxy);
	}
	
	public BaseConcreteSingleGuiElement(Gui page, ConcreteGuiAutomator<D,E> automator, GuiElementProxy proxy) {
		super(page, automator, proxy);
		populateObjectVars(automator, proxy);

	}
	
	private void populateObjectVars(ConcreteGuiAutomator<D,E> automator, GuiElementProxy proxy) {
		this.emd = proxy.getMetaData();
		this.picker = automator.getPicker();		
		this.waitTime = automator.getWaitTime();
	}
	
	@Override
	public int getWaitTime() throws Exception {
		return this.waitTime;
	}
	
	public void identify() throws Exception{
		this.setToolElement(this.getAutomator().getPicker().find(this.emd));
	}
	
	public void identifyIfNull() throws Exception {
		if (this.getToolElement() == null) {
			this.identify();
		}
	}

	public GuiElementProxy element(String name) throws Exception{
		
		GuiElementMetaData metaData = this.getPage().getPageDef().getMetaData(name);
		E element = this.picker.find(this, metaData);
		return this.picker.convertToolElementToProxy(this.getPage(), metaData, element);
	}

	protected GuiElementPicker<D,E> getPicker() {
		return this.picker;
	}
	
	public MultiGuiElement elements(String name) throws Exception{
		GuiElementMetaData metaData = this.getPage().getPageDef().getMetaData(name);
		List<E> elements = this.picker.findAll(this, metaData);
		return this.picker.convertMultiToolElementToProxy(this.getPage(), metaData, elements);
	}
	
	public void setToolElement(E element) {
		this.toolElement = element;
	}
	
	public E getToolElement() {
		return this.toolElement;
	}

	protected E getToolElementWithRetry() throws Exception {
		E element = this.getToolElement();
		if (element == null){
			identify();
			return this.getToolElement();
		} else {
			return element;
		}
	}
	
	public boolean isIdentified() throws Exception{
		return this.getToolElement() != null;
	}
	
	public GuiElementMetaData getMetaData() {
		return emd;
	}

	public int getGuiElementCount() throws Exception {
		if (this.isIdentified()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public GuiElementType getElementType() {
		return this.elementType;
	}

	public void setElementType(GuiElementType elementType) {
		this.elementType = elementType;
	}
	
	@Override
	public boolean isPresent() throws Exception {
		return this.getPicker().isPresent(this.getGuiElementProxy());
	}
	
	@Override
	public void waitUntilPresent() throws Exception {
		if (!isPresent()) {
			throw new Exception("Not present despite waiting.");
		}
	}
	
	@Override
	public boolean isAbsent() throws Exception {
		return !isPresent();
	}
	
	@Override
	public void waitUntilAbsent() throws Exception {
		if (!isAbsent()) {
			throw new Exception("Not absent despite waiting.");
		}
	}
		
	@Override
	public boolean isVisible() throws Exception {
		return this.getPicker().isVisible(this.getGuiElementProxy());
	}

	@Override
	public void waitUntilVisible() throws Exception {
		if (!isVisible()) {
			throw new Exception("Not visible despite waiting.");
		}
	}

	@Override
	public boolean isInvisible() throws Exception {
		return !isVisible();
	}
	
	@Override
	public void waitUntilInvisible() throws Exception {
		if (!isInvisible()) {
			throw new Exception("Not invisible despite waiting.");
		}
	}
	
	@Override
	public boolean isClickable() throws Exception {
		return this.getPicker().isClickable(this.getGuiElementProxy());
	}
	
	@Override
	public void waitUntilClickable() throws Exception {
		if (!isClickable()) {
			throw new Exception("Not clickable despite waiting.");
		}
	}

	@Override
	public String getText() throws Exception {
		return (String) this.getGuiElementProxy().throwUnsupportedActionException("getText");
	}

	@Override
	public String getValue() throws Exception {
		return (String) this.getGuiElementProxy().throwUnsupportedActionException("getValue");
	}

	@Override
	public String getAttribute(String attr) throws Exception {
		return (String) this.getGuiElementProxy().throwUnsupportedActionException("getAttribute");
	}

	@Override
	public void enterText(String text) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("enterText");
	}

	@Override
	public void setText(String text) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("setText");
	}

	@Override
	public void clearText() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("clearText");
	}

	@Override
	public void focus() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("focus");
	}
	
	@Override
	public void submit() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("submit");
	}

	@Override
	public void click() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("click");
	}

	@Override
	public void hover() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("hover");
	}

	@Override
	public void hoverAndClick() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("hoverAndClick");
	}

	@Override
	public void rightClick() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("rightClick");
	}

	@Override
	public void check() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("check");
	}

	@Override
	public void uncheck() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("uncheck");
	}

	@Override
	public void toggle() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("toggle");
	}

	@Override
	public String getImagePath() throws Exception {
		return (String) this.getGuiElementProxy().throwUnsupportedActionException("getImagePath");
	}

	@Override
	public void setImagePath(String imagePath) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("setImagePath");
	}

	@Override
	public void select(String text) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("select");
	}

	@Override
	public void selectText(String text) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("selectLabel");
	}

	@Override
	public void selectValue(String value) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("selectValue");
	}

	@Override
	public void selectAtIndex(int index) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("selectIndex");
	}

	@Override
	public boolean hasSelectedText(String text) throws Exception {
		return (boolean) this.getGuiElementProxy().throwUnsupportedActionException("hasSelectedLabel");
	}

	@Override
	public boolean hasSelectedValue(String value) throws Exception {
		return (boolean) this.getGuiElementProxy().throwUnsupportedActionException("hasSelectedValue");
	}

	@Override
	public boolean hasSelectedIndex(int index) throws Exception {
		return (boolean) this.getGuiElementProxy().throwUnsupportedActionException("hasSelectedIndex");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllOptions() throws Exception {
		return (List<String>) this.getGuiElementProxy().throwUnsupportedActionException("getAllLabels");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllValues() throws Exception {
		return (List<String>) this.getGuiElementProxy().throwUnsupportedActionException("getAllValues");
	}

	@Override
	public int getOptionCount() throws Exception {
		return (int) this.getGuiElementProxy().throwUnsupportedActionException("getOptionCount");
	}

	@Override
	public void switchToFrame() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("switchToFrame");
	}

	public void hoverAndClickElement(GuiElement uiElement) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("hoverAndClickElement");
	}

	public void rightClickAndClickElement(GuiElement uiElement) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("rightClickAndClickElement");
	}
	
	@Override
	public boolean isChecked() throws Exception {
		return (boolean) this.getGuiElementProxy().throwUnsupportedActionException("isChecked");
	}

	@Override
	public boolean isSelected() throws Exception {
		return (boolean) this.getGuiElementProxy().throwUnsupportedActionException("isSelected");
	}
	
	@Override
	public void doubleClick() throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("doubleClick");
	}

	@Override
	public void dragAndDrop(String name) throws Exception {
		this.getGuiElementProxy().throwUnsupportedActionException("dragAndDrop");
	}
}

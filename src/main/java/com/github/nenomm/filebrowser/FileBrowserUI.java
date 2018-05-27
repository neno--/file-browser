package com.github.nenomm.filebrowser;

import com.github.nenomm.filebrowser.layout.BrowserLayout;
import com.vaadin.annotations.Push;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Push
@SpringUI
public class FileBrowserUI extends UI implements RefreshPathCallback {

	private Logger logger = LoggerFactory.getLogger(FileBrowserUI.class);
	private VerticalLayout root;
	private TextField searchField;

	@Autowired
	private BrowserLayout browserLayout;

	@Override
	protected void init(VaadinRequest request) {
		setupLayout();
		addHeader();
		initBody();
	}

	private void setupLayout() {
		root = new VerticalLayout();
		root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		root.setHeight("100%");

		setContent(root);
	}

	private void addHeader() {
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		searchLayout.setWidth("50%");

		Label title = new Label("Enter Path");
		searchLayout.addComponent(title);

		TextField tf1 = new TextField();
		searchLayout.addComponentsAndExpand(tf1);
		this.searchField = tf1;

		Button bttn1 = new Button();
		bttn1.addStyleName(ValoTheme.BUTTON_PRIMARY);
		bttn1.setIcon(VaadinIcons.ANGLE_RIGHT);
		bttn1.addClickListener(click -> {
			browserLayout.setVisible(false);
			browserLayout.browseFiles(tf1.getValue());
		});
		bttn1.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		searchLayout.addComponent(bttn1);

		root.addComponent(searchLayout);
	}

	private void initBody() {
		root.addComponentsAndExpand(browserLayout);
		browserLayout.registerCallback(this);
	}

	@Override
	public void refreshPath(String path) {
		this.searchField.setValue(path);
	}
}

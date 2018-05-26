package com.github.nenomm.filebrowser;

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

@SpringUI
public class FileBrowserUI extends UI {

	private VerticalLayout root;

	@Override
	protected void init(VaadinRequest request) {
		setupLayout();
		addHeader();
	}

	private void setupLayout() {
		root = new VerticalLayout();

		root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		setContent(root);
	}

	private void addHeader() {
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		searchLayout.setWidth("50%");

		Label title = new Label("Enter Path");
		searchLayout.addComponent(title);

		TextField tf1 = new TextField();
		tf1.setWidth("100%");
		searchLayout.addComponent(tf1);
		searchLayout.setExpandRatio(tf1, 1.0f);

		Button bttn1 = new Button();
		bttn1.addStyleName(ValoTheme.BUTTON_PRIMARY);
		bttn1.setIcon(VaadinIcons.SEARCH);
		searchLayout.addComponent(bttn1);

		root.addComponent(searchLayout);
	}

}

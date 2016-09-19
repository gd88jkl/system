package com.zz.auth.view;

import java.util.ArrayList;
import java.util.List;

public class VMenu {

	private String id;
	private String text;
	private String url;
	private String spriteCssClass;
	private List<VMenu> items = new ArrayList<VMenu>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSpriteCssClass() {
		return spriteCssClass;
	}

	public void setSpriteCssClass(String spriteCssClass) {
		this.spriteCssClass = spriteCssClass;
	}

	public List<VMenu> getItems() {
		return items;
	}

	public void setItems(List<VMenu> items) {
		this.items = items;
	}

}

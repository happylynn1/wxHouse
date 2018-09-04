package com.shenyingchengxun.action.bean;

/** 
 * 复杂按钮（父按钮） 
 * @author niufeihu
 * @date 2018-04-09  
 */  
public class ViewButton extends Button {
	private String type;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

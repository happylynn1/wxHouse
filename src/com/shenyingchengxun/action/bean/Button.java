package com.shenyingchengxun.action.bean;

/** 
 * 按钮的基类 
 * @author niufeihu
 * @date 2018-04-09 
 */  
public class Button {
	
    private String name;  
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

	public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
}

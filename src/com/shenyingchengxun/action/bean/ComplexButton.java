package com.shenyingchengxun.action.bean;

/** 
 * 复杂按钮（父按钮） 
 * @author niufeihu
 * @date 2018-04-09  
 */  
public class ComplexButton extends Button {  
    private Button[] sub_button;  
  
    public Button[] getSub_button() {  
        return sub_button;  
    }  
  
    public void setSub_button(Button[] sub_button) {  
        this.sub_button = sub_button;  
    }  
}

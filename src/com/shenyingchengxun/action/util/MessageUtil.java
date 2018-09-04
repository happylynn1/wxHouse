package com.shenyingchengxun.action.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.message.resp.Article;
import com.shenyingchengxun.action.message.resp.MusicMessage;
import com.shenyingchengxun.action.message.resp.NewsMessage;
import com.shenyingchengxun.action.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	/** 
     * 返回消息类型：文本 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  

    /** 
     * 返回消息类型：图文 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /** 
     * 请求消息类型：推送 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
    
    /** 
     * 事件类型：SCAN(扫描) 
     */  
    public static final String EVENT_TYPE_SCAN = "SCAN";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
    
    /** 
     * 事件类型：VIEW(VIEW菜单点击事件) 
     */  
    public static final String EVENT_TYPE_VIEW = "VIEW";
  
    /** 
     * 解析微信发来的请求（XML） 
     * 
     * @param request 
     * @return 
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
      
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
        @SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();  
      
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
      
        // 释放资源  
       /* inputStream.close();
        inputStream = null;*/
      
        return map;  
    }  
  
    /** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */  
    public static String textMessageToXml(TextMessage textMessage) {  
        xstream.alias("xml", textMessage.getClass());  
        return xstream.toXML(textMessage);  
    }  
      
    /** 
     * 音乐消息对象转换成xml 
     *  
     * @param musicMessage 音乐消息对象 
     * @return xml 
     */  
    public static String musicMessageToXml(MusicMessage musicMessage) {  
        xstream.alias("xml", musicMessage.getClass());  
        return xstream.toXML(musicMessage);  
    }  
      
    /** 
     * 图文消息对象转换成xml 
     *  
     * @param newsMessage 图文消息对象 
     * @return xml 
     */  
    public static String newsMessageToXml(NewsMessage newsMessage) {  
        xstream.alias("xml", newsMessage.getClass());  
        xstream.alias("item", new Article().getClass());  
        return xstream.toXML(newsMessage);  
    } 
    /** 
     * 扩展xstream，使其支持CDATA块 
     *  
     * @date 2017-03-06 
     */  
    private static XStream xstream = new XStream(new XppDriver() {  
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;
                public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {  
                    super.startNode(name, clazz);
                }
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text); 
                    }
                }
            };
        }
    });
    /**
     * 判断是否是QQ表情 
     * 
     * @param content 
     * @return 
     */  
    public static boolean isQqFace(String content) {  
        boolean result = false;  
        // 判断QQ表情的正则表达式  
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";  
        Pattern p = Pattern.compile(qqfaceRegex);  
        Matcher m = p.matcher(content);  
        if (m.matches()) {  
            result = true;  
        }  
        return result;  
    } 
    /**
     * 链接工具
     * 
     */
    public static String writeHref(String url,String name){
    	String href = "<a href=\""+url+"\">"+name+"</a>";
    	return href;
    }
    /**
     * @param title (标题)
     * @param description 描述(多图文消息时，为空)
     * @param picUrl (为空时，表示不插入图片)
     * @param url (文章原始链接)
     * @return Article
     */
    public static Article setArticle(String title,String description,String picUrl,String url){
    	Article article = new Article();
    	article.setTitle(title);
    	article.setDescription(description);  
    	article.setPicUrl(picUrl);  
    	if(url!=null&&url.length()>1)
    	article.setUrl(url);
    	return article;
    }
    public static TextMessage getTextMessage(Map<String, String> requestMap){
		TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(requestMap.get("FromUserName"));
        textMessage.setFromUserName(requestMap.get("ToUserName"));  
        textMessage.setCreateTime(new Date().getTime());  
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
        textMessage.setFuncFlag(0);
		return textMessage;
	}
	public static NewsMessage getNewMessage(Map<String, String> requestMap){
		NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName(requestMap.get("FromUserName"));  
        newsMessage.setFromUserName(requestMap.get("ToUserName"));  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0);
		return newsMessage;
	}
	public static String getHelloNews(StoreInfo si,Map<String,String> requestMap){
		System.out.println(si);
		NewsMessage newsMessage = getNewMessage(requestMap);
		List<Article> articleList = new ArrayList<Article>();
		String title = "",introdu = "",imagePath = Config.Base+"res/images/defaultStore.jpg";
		if(si.getStoreName()!=null&&!"".equals(si.getStoreName()))
			title = si.getStoreName();
		if(si.getIntrodu()!=null&&!"".equals(si.getIntrodu()))
			introdu = si.getIntrodu();
		if(si.getImagePath()!=null&&!"".equals(si.getImagePath().trim()))
			imagePath = Config.WebFileBase+si.getImagePath();
		Article article1 = MessageUtil.setArticle(title, introdu, imagePath,"");
		articleList.add(article1);
		newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
		return MessageUtil.newsMessageToXml(newsMessage);
	}
}  
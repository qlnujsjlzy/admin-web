package com.angular.admin.common.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by chenww3 on 2015/6/14.
 */
public class JsonUtil {

    private static Logger logger = LogManager.getLogger(JsonUtil.class.getName());


    public static final ObjectMapper OM = new ObjectMapper();
    static{
    	// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性  
    	OM.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);  
    	OM.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        OM.setSerializationInclusion(Inclusion.NON_NULL); 
    }
    

    public static JavaType assignList(Class<? extends Collection> collection, Class<? extends Object> object) {
        return JsonUtil.OM.getTypeFactory().constructParametricType(collection, object);
    }


    public static <T> ArrayList<T> readValuesAsArrayList(String key, Class<T> object) {
        ArrayList<T> list = null;
        try {
            list = OM.readValue(key, assignList(ArrayList.class, object));
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }


    public static String toJson(Object obj){
    	if(obj == null){
    		return "";
    	}
        try {
            return OM.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("toJson error -->", e);
        }
        return null;
    }

    /**
     * 
    * @Description: 重载个性化时间
    * @author yuzj7@lenovo.com  
    * @date 2015年8月9日 下午4:19:18
    * @param sdf
    * @param json
    * @param clazz
    * @return
     */
    public static <T> T fromJson(SimpleDateFormat sdf,String json, Class<T> clazz){
        try {
        	OM.setDateFormat(sdf);
            T t =  OM.readValue(json, clazz);
            OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return t;
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return OM.readValue(json, clazz);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        try {
            return OM.readValue(json, typeReference);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    
    public static String toJson(Object obj, String callback){
        if(obj instanceof String){
            return (String)obj;
        }
        String rs = null;
        try {
            JsonUtil.OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            rs = JsonUtil.OM.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("toJson error -->", e);
        }
        if(rs == null){
            rs = "{\"rc\":-1}";//解析JSON异常/IO异常
        }
        if(StringUtils.isNotEmpty(callback)){
        	rs = String.format("%s(%s)", callback, rs);
        }	
        return rs;
    }

    public static void main(String[] args) {
		
    	String json = "{\"rc\":0,\"msg\":\"success\",\"data\":{\"num\":1,\"vendors\":[{\"id\":\"7ef1d628-5bd3-4651-9530-793678cc02af\",\"name\":null,\"type\":null,\"sorted\":[{\"itemType\":0,\"item\":{\"item\":\"Sku\",\"itemId\":null,\"id\":\"50564\",\"num\":1,\"check\":1,\"createTime\":null,\"updateTme\":\"2016-03-15 14:23:49\",\"name\":\"Y40-80-IFI\",\"gdesc\":\"智能英特尔酷睿 Broadwell 双核处理器i5-5200U<br>Windows 8.1 中文版<br>8G内存/ 1T硬盘<br>AMD Radeon R9 M375 显卡<br>14英寸高清LED显示器\",\"cid\":\"01140315\",\"imgUrl\":\"http://p2.lefile.cn/g1/M00/02/80/CmPJilXIb5WAbw_LAAAY6QU2VS0517.jpg\",\"gspec\":\"null\",\"isNeedSN\":0,\"isPhysical\":1,\"isService\":0,\"price\":\"0.00\",\"discount\":\"0.00\",\"rePrice\":null,\"stock\":1,\"isDelivery\":0,\"shopId\":0,\"productGroupId\":\"48\",\"salesType\":0,\"unit\":null,\"deatLike\":\"48\",\"thinkRemark\":null,\"mediaPrice\":null,\"internalMedia\":null,\"costPrice\":null,\"marketPrice\":null,\"internalUpLine\":null,\"returnUpLine\":null,\"terminal\":3,\"productId\":null,\"zcid\":null,\"supportType\":null,\"kcode\":null,\"thinkCustomInfo\":null,\"shareCode\":null,\"activityType\":null,\"extraType\":0,\"activityId\":null,\"marketable\":false,\"gifts\":[],\"canSelectPromotions\":[],\"services\":[],\"discountCollect\":{},\"skuExtend\":{\"cvItemList\":null},\"faname\":\"联想 ( 上海 ) 电子科技有限公司\",\"materialCode\":\"80FA001LCD\",\"faid\":\"7ef1d628-5bd3-4651-9530-793678cc02af\",\"bu\":0,\"vendorid\":\"7ef1d628-5bd3-4651-9530-793678cc02af\",\"type\":0}}],\"total\":null,\"totalDiscount\":null,\"afterDiscount\":null,\"mbg\":false,\"zy\":false}],\"totalPrice\":\"0.00\",\"totalDiscount\":\"0.00\",\"afterDiscount\":\"0.00\",\"version\":null,\"empty\":false}}";
    	
//    	JsonUtil.fromJson(json, );
    	Calendar cal = Calendar.getInstance();
    	//Calendar gc = GregorianCalendar.getInstance();
    	
    	SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    	System.out.println("foo:"+foo.format(new Date()));




    	Calendar gc = GregorianCalendar.getInstance();


    	System.out.println("gc.getTime():"+foo.format(gc.getTime()));
    	
    	
    	Calendar calendar1 = Calendar.getInstance();


    	TimeZone tztz = TimeZone.getTimeZone("GMT-8");       


    	calendar1.setTimeZone(tztz);


    	System.out.println(foo.format(calendar1.getTime()));
    	
    	
	}
}

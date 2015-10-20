package cn.guolf.cordova.plugins.soap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author：guolf on 15/10/20 10:17
 * Email ：guo@guolingfa.cn
 */
public class SoapHelper extends CordovaPlugin{

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException{
        try {
            if (action.equals("caller")) {
                JSONObject r = new JSONObject();
                r.put("result"," this is a test from java code");
                callbackContext.success(r);
            }
        }catch (Exception ex){
            JSONObject r = new JSONObject();
            r.put("error",ex);
            callbackContext.error(r);
        }
        return true;
    }
}

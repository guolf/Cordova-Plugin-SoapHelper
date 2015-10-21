package cn.guolf.cordova.plugins.soap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;

/**
 * Author：guolf on 15/10/20 10:17
 * Email ：guo@guolingfa.cn
 */
public class SoapHelper extends CordovaPlugin{

    private final String url = "http://192.168.1.100/service.asmx";
    private final String namespace = "http://tempuri.org/";
    private final int timeout = 10000;
    private ProgressDialog pDialog;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("caller")) {
                if (!args.getString(0).equalsIgnoreCase("")) {
                    String[] par = args.getString(1).split("--");
                    String[] obj = args.getString(2).split("--");
                    callWebServer(args.getString(0), par, obj, callbackContext);
                } else {
                    JSONObject r = new JSONObject();
                    r.put("code", 0);
                    r.put("result", "参数不正确");
                    callbackContext.error(r);
                }
            }
        } catch (Exception ex) {
            JSONObject r = new JSONObject();
            r.put("result", ex);
            r.put("code", 0);
            callbackContext.error(r);
        }
        return true;
    }

    private void callWebServer(final String methodName, final String[] parm, final String[] value, final CallbackContext callbackContext) {

        AsyncTask task = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                JSONObject json = new JSONObject();
                try {
                    String soapAction = namespace + methodName;
                    SoapObject request = new SoapObject(namespace, methodName);
                    for (int i = 0; i < parm.length; i++) {
                        request.addProperty(parm[i], value[i]);
                    }
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(url, timeout);
                    androidHttpTransport.call(soapAction, envelope);
                    if (envelope.getResponse() != null) {
                        try {
                            json.put("code", 1);
                            json.put("result", envelope.getResponse().toString());
                        } catch (JSONException j) {

                        }
                    }
                } catch (ConnectException ex) {
                    try {
                        json.put("code", 0);
                        json.put("result", "网络异常");
                    } catch (JSONException j) {

                    }
                } catch (XmlPullParserException ex) {
                    try {
                        json.put("code", 0);
                        json.put("result", "xml解析异常");
                    } catch (JSONException j) {

                    }
                } catch (IOException ex) {
                    try {
                        json.put("code", 0);
                        json.put("result", "IO异常");
                    } catch (JSONException j) {

                    }
                } catch (Exception ex) {
                    try {
                        json.put("code", 0);
                        json.put("result", ex.getMessage());
                    } catch (JSONException j) {

                    }
                }
                return json;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = ProgressDialog.show(cordova.getActivity(), null, "loading..");
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                JSONObject json = (JSONObject) o;
                callbackContext.success(json);
                if (pDialog != null)
                    pDialog.dismiss();
            }
        };
        task.execute();
    }

}

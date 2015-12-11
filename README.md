# Cordova Soap plugin


Cordova使用Soap调用webservice。

安装：

```
cordova plugin add https://github.com/guolf/Cordova-Plugin-SoapHelper.git

```

删除

```
cordova plugin remove cordova-plugin-soaphelper
```

调用


```
try{
	window.plugins.soaphelper.caller(method,par,value,success,error);
}catch(ex){
	navigator.notification.alert("程序异常:"+ex.name + "," + ex.message);
}
function success(data){
	if(data.code == 1){
		//...
	}else{
		navigator.notification.alert(data.result);
	}
}
function error(error){
	navigator.notification.alert(error.result);
}
```

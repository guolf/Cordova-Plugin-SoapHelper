function SoapHelper() {
}

Toast.prototype.caller = function (options, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "SoapHelper", "caller", [options]);
};


SoapHelper.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.soaphelper= new SoapHelper();
  return window.plugins.soaphelper;
};

cordova.addConstructor(SoapHelper.install);
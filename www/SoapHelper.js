var exec = require('cordova/exec');

module.exports = {
    caller : function(methdod,par,value,sucess,error) {
        exec(sucess, error, 'SoapHelper', 'caller', [methdod,par,value]);
    }
};

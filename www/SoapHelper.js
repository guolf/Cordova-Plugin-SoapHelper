var exec = require('cordova/exec');

module.exports = {
    caller : function(sucess,error) {
        exec(sucess, error, 'SoapHelper', 'caller', []);
    }
};

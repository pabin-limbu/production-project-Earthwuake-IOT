var express = require('express');
var router = express.Router();

var admin = require("firebase-admin");
var serviceAccount = require("../fcmdemo-7d461-firebase-adminsdk-wbsb0-502da50541.json");
var registrationToken = "c-aWMqdtmzo:APA91bGLDyQVyDhMUFFUWw2O8r_5OHDOOCMUs4RLDfR20FS5DP1JpgAD-w0ZeU2tUXePjZ-g2Ky8wRGAPkEjNxtNM_63fgwOcUuhqx5G0TpuorhZeDQIeq7mkNBAAS32sgQoogaiwAwm";
var payload = {
    notification: {
        title: "EARTHQUAKE ALERT!",
        body: "Earthquake Detected!"
    }
};
var options = {
    priority: "high",
    timeToLive: 60 * 60 * 24
};
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://fcmdemo-7d461.firebaseio.com"
});

router.route('/send')
    .get((req, res, next) => {
        admin.messaging().sendToDevice(registrationToken, payload, options)
            .then(function (response) {
                console.log("Successfully sent message:", response);
                res.end('Notifiation sent succesfully!');
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
                res.end('Notifiation sending failed!');
            });
    });

module.exports = router;
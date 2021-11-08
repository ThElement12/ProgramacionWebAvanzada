var mqtt = require('mqtt');
options = {
  username: "admin",
  password: "admin"
};
var client = mqtt.connect(process.env.MQT_ROUTE || 'mqtt://localhost:1883')
client.on("connect", function () {
  console.log('connected!');
});

var timer_id = setInterval(function () {
  publish({
    id: null,
    idDevice: 2,
    generationDate: new Date().toLocaleString(),
    temperature: Math.floor((Math.random() * 100)),
    humidity: Math.floor((Math.random() * 100))
  });
}, 6000);

//publish function
client.subscribe('notificacion_sensores')
function publish(msg) {
  console.log("publishing", msg);
  if (client.connected == true) {
    client.publish("sensor-sub", JSON.stringify(msg));
  }
}

timer_id

/*const express = require('express');
const resultado = require('./request-api');
const cors = require('cors');

const app = express();
const router = express.Router();
require('dotenv').config();

const TIEMPO = process.env.TIEMPO || 2;

app.set('port', process.env.PORT || 5000)
app.use(express.json());
app.use(cors())


app.use(
  router.get('/', (req, res) => {
    res.send("Hola desde el cliente 1");
  })
)

app.listen(app.get('port'), () => {
  setInterval(resultado, TIEMPO * 1000)
  console.log("Cliente 1 en linea en el puerto:", app.get('port'));
  console.log(`Enviando info cada ${TIEMPO} segundos`);

})
*/

/*client.on("error", function (error) {
  console.log("Can't connect" + error);
  process.exit(1)
});
*/

var mqtt = require('mqtt');
options = {
  username: "admin",
  password: "admin"
};
var client = mqtt.connect("mqtt://localhost:1883")
client.on("connect", function () {

  console.log('connected!');

});

var timer_id = setInterval(function () { publish( {
  id:null,
  idDevice:1,
  generationDate:new Date().toLocaleString(),
  temperature:Math.floor((Math.random() * 100)),
  humidity:Math.floor((Math.random() * 100))
}); }, 6000);



//publish function
client.subscribe('notificacion_sensores')
function publish(msg) {
  console.log("publishing", msg);
  if (client.connected == true) {
    //client.publish("sensor-sub", JSON.stringify(msg));
    client.publish("notificacion_sensores", JSON.stringify(msg));
  }
}

timer_id


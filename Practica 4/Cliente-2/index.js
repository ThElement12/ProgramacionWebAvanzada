const express = require('express');
const resultado = require('./request-api');
const cors = require('cors');

const app = express();
const router = express.Router();
require('dotenv').config();

const TIEMPO = process.env.TIEMPO || 2;

app.set('port', process.env.PORT || 5001)
app.use(express.json());
app.use(cors())


app.use(
  router.get('/', (req,res) => {
    res.send("Hola desde el cliente 2");
  })
)

app.listen(app.get('port'), () => {
  setInterval(resultado, TIEMPO * 1000)
  console.log("Cliente 2 en linea en el puerto:", app.get('port'));
  console.log(`Enviando info cada ${TIEMPO} segundos`);

})

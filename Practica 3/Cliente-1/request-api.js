const request = require("request");

const requestOptions = {
  url: 'https://617ab359cb1efe001700ffda.mockapi.io/',
  method: 'GET',
  
};
const resultado = () => request(requestOptions, (err, response, body) => {
  if (err) {
    console.log(err);
  } else if (response.statusCode === 200) {
    console.log("Api aqui :D");
  } else {
    console.log(response.statusCode);
  }
});

module.exports = resultado;


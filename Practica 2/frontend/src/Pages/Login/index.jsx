import React, { useState } from "react";
import { Form, Button, Card, Alert } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import jwt_decode from "jwt-decode";

//import { Auth } from "../../utils/firebase";
//import logo from "../../assets/img/Logo.png";

import  AuthService from '../../Utils/auth.service.js'
import "./Login.css";
import "bootstrap/dist/css/bootstrap.min.css";

const Login = () => {
  const [credential, setUsername] = useState("");
  const [password, setPass] = useState("");
  const [msgError, setmsgError] = useState("");
  const history = useHistory();


  const onSubmit = (event) => {
    event.preventDefault();
    authentication();
  };
  const authentication = async () => {
    await AuthService.login(credential, password)
      .then(res => {
        console.log("Decodiao: ");
        var decoded = jwt_decode(res);
        sessionStorage.setItem('username', decoded.username);
        if(decoded.roles.includes("admin")){
          sessionStorage.setItem('rol', "admin")
        }
        history.push("/home");
      })
      .catch(res => {
        setmsgError("La contraseña o el usuario no es correcto")
      });
  }

  return (
    <div
      className="d-flex align-items-center justify-content-center"
      style={{ minHeight: "100vh" }}
    >
      <Card className="card-login">
        <Card.Body>
          <h2 className="text-center mb-4">Ingresar</h2>
          <Form onSubmit={onSubmit}>
            <Form.Group id="correo">
              <Form.Label>Usuario</Form.Label>
              <Form.Control
                type="username"
                name="username"
                onChange={(e) => {
                  setUsername(e.target.value);
                }}
                required
              />
            </Form.Group>
            <Form.Group id="pass">
              <Form.Label>Contraseña</Form.Label>
              <Form.Control
                type="password"
                name="password"
                autoComplete="on"
                onChange={(e) => {
                  setPass(e.target.value);
                }}
                required
              />
            </Form.Group>
            <br></br>
            {msgError !== "" && <Alert variant="danger">{msgError}</Alert>}
            <Button className="w-100" type="submit">
              Ingresar
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
};

export default Login;

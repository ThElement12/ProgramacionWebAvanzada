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
        console.log("Decodiao: ")
        var decoded = jwt_decode(res);
        console.log(decoded)
      })
      .catch(res => {
        setmsgError(res.message)
      });
  }

  /*const firebaseAuthentication = () => {
    Auth.signInWithEmailAndPassword(username, pass)
      .then(firebaseSuccess)
      .catch(firebaseError);
  };*/
  const firebaseSuccess = () => {
    setmsgError("");
    fetch(process.env.REACT_APP_BACK_URL + "usuarios/" + credential)
      .then((res) => res.json())
      .then((res) => {
        const entries = Object.entries(res[0]);
        for (const [key, value] of entries) {
          localStorage.setItem(key, value);
        }
        if (localStorage.getItem("Tipo Usuario") === "Abogado") {
          const idPersona = localStorage.getItem("Id Persona");
          fetch(process.env.REACT_APP_BACK_URL + "abogado-id/" + idPersona)
            .then((res) => res.json())
            .then((res) => {
              const entries = Object.entries(res[0]);
              for (const [key, value] of entries) {
                localStorage.setItem(key, value);
              }
            });
        }
        history.push("/home");
      })
      .catch((err) => console.error(err));
  };
  const firebaseError = (error) => {
    if (error.code === "auth/user-not-found") {
      setmsgError("El correo no existe");
      return;
    } else if (error.code === "auth/wrong-password") {
      setmsgError("La contraseña es incorrecta");
      return;
    }
  };

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

import React, { useState } from "react";
import { Form, Button, Card, Alert } from "react-bootstrap";
import { useHistory } from "react-router-dom";

//import { Auth } from "../../utils/firebase";
//import logo from "../../assets/img/Logo.png";

import "./Login.css";
import "bootstrap/dist/css/bootstrap.min.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [pass, setPass] = useState("");
  const [msgError, setmsgError] = useState("");
  const history = useHistory();

  const onSubmit = (event) => {
    event.preventDefault();
    console.log(event);
    //firebaseAuthentication();
  };

  /*const firebaseAuthentication = () => {
    Auth.signInWithEmailAndPassword(email, pass)
      .then(firebaseSuccess)
      .catch(firebaseError);
  };*/
  const firebaseSuccess = () => {
    setmsgError("");
    fetch(process.env.REACT_APP_BACK_URL + "usuarios/" + email)
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
              <Form.Label>Correo</Form.Label>
              <Form.Control
                type="email"
                name="email"
                onChange={(e) => {
                  setEmail(e.target.value);
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

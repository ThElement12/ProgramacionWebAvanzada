import React, { useState } from "react";
import { Form, Button, Card, Alert } from "react-bootstrap";

import Navigation from "../../Components/Navigation";

import "bootstrap/dist/css/bootstrap.min.css";
import "./Register.css"

import AuthService from '../../Utils/auth.service.js'

const Register = () => {
  const [username, setUsername] = useState("");
  const [mail, setEmail] = useState("");
  const [password, setPass] = useState("");
  const [confirm, setConfirm] = useState("");

  const [msgError, setmsgError] = useState("");

  const onSubmit = (event) => {
    event.preventDefault();
    if (password !== confirm) {
      setmsgError("Las contraseñas no coinciden");
    } else {
      register()
    }
  }
  const register = async () => {
    await AuthService.register(username, password, mail)

  }

  return (
    <div>
      <Navigation />
      <div className="d-flex align-items-center justify-content-center"
        style={{ minHeight: "100vh" }}>
        <Card className="card-register">
          <Card.Body>
            <h2 className="text-center mb-4">Registrate</h2>
            <Form onSubmit={onSubmit}>
              <Form.Label>Nombre de Usuario:</Form.Label>
              <Form.Control type="username" name="username" onChange={(e) => { setUsername(e.target.value); }} required></Form.Control>
              <Form.Label>Correo:</Form.Label>
              <Form.Control type="email" name="email" onChange={(e) => { setEmail(e.target.value); }} required></Form.Control>
              <Form.Label>Contraseña:</Form.Label>
              <Form.Control type="password" name="password" onChange={(e) => { setPass(e.target.value); }} required></Form.Control>
              <Form.Label>Confirmar Contraseña</Form.Label>
              <Form.Control type="password" name="confirmpass" onChange={(e) => { setConfirm(e.target.value); }} required></Form.Control>
              <br></br>
              {msgError !== "" && <Alert variant="danger">{msgError}</Alert>}
              <Button className="w-100" type="submit">
                Registrar
              </Button>
            </Form>
          </Card.Body>
        </Card>

      </div>
    </div>

  );

}

export default Register
import React, { useState } from "react";
import { Form, Button, Card, Alert, Modal } from "react-bootstrap";
import { useHistory } from "react-router-dom";

import Navigation from "../../Components/Navigation";

import "bootstrap/dist/css/bootstrap.min.css";
import "./Register.css"

import AuthService from '../../Utils/auth.service.js'

const Register = () => {
  const [username, setUsername] = useState("");
  const [mail, setEmail] = useState("");
  const [password, setPass] = useState("");
  const [confirm, setConfirm] = useState("");
  const [rol, setRol] = useState([]);

  const [msgError, setmsgError] = useState("");
  const [modalSuccess, setModalSuccess] = useState(false);

  const history = useHistory();

  const onSubmit = (event) => {
    event.preventDefault();
    if (password !== confirm) {
      setmsgError("Las contraseñas no coinciden");
    } else {
      register()
    }
  }
  const onSuccess = () => {
    setmsgError("");
    setModalSuccess(true);
  }
  const register = async () => {
    await AuthService.register(username, password, mail, rol)
      .then(onSuccess)
      .catch(() => {
        setmsgError("Hubo un error al registrar el usuario")
      })

  }
  const hideModalSuccess = () => {
    setModalSuccess(false);
    setUsername("");
    setEmail("");
    setPass("");
    setConfirm("");
    history.push('/home')
  }

  const success = () => {
    return <Modal
      show={modalSuccess}
      onHide={hideModalSuccess}
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Informacion</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        Operacion realizada con exito
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={() => hideModalSuccess()}>
          Ok
        </Button>
      </Modal.Footer>
    </Modal>
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
              <Form.Label>Rol: </Form.Label>
              <Form.Control as="select" name="rol" defaultValue="Elige..."
                    onChange={(e) => {
                      setRol([e.target.value])
                    }}
                    required>
                    <option>Elige...</option>
                    <option value={"admin"}>Admin</option>
                    <option value={"cliente"}>Cliente</option>
              </Form.Control>
              <br></br>
              {msgError !== "" && <Alert variant="danger">{msgError}</Alert>}
              <Button className="w-100" type="submit">
                Registrar
              </Button>
            </Form>
          </Card.Body>
        </Card>
        {success()}
      </div>
    </div>

  );

}

export default Register
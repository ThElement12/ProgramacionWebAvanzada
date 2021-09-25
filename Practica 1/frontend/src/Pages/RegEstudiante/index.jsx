import React, { useState } from 'react';
import { Form, Button, Card, Row, Col, Modal } from "react-bootstrap";

import "./PageRegister.css";
import "bootstrap/dist/css/bootstrap.min.css";

import { EstudianteService } from '../../Services/Estudiante.service';
import Estudiante from '../../Entities/Estudiante';


const RegEstudiante = () => {
  const [matricula, setMatricula] = useState("");
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [telefono, setTelefono] = useState("");
  const [modal, setModal] = useState(false);

  const handleClose = () => setModal(false);

  const clean = () => {
    setNombre("");
    setApellido("");
    setTelefono("");
    setMatricula("");
  }

  const onSubmit = event => {
    //TODO usar la misma pagina de registrar para modificar
    var estudiante = new Estudiante(matricula, nombre, apellido, telefono)
    event.preventDefault();
    const estudianteService = new EstudianteService();
    estudianteService.registrarEstudiante(estudiante);
    setModal(true);
    clean()
    console.log(estudiante);
  }

  const formatTelefono = value => {
    if (!value) return value;

    const phoneNumber = value.replace(/[^\d]/g, "");
    const phoneNumberLength = phoneNumber.length;

    if (phoneNumberLength < 4) return phoneNumber;

    if (phoneNumberLength < 7) {
      return `(${phoneNumber.slice(0, 3)})-${phoneNumber.slice(3)}`;
    }

    return `(${phoneNumber.slice(0, 3)})-${phoneNumber.slice(
      3,
      6
    )}-${phoneNumber.slice(6, 10)}`;
  };

  return (
    <div className="d-flex align-items-center justify-content-center"
      style={{ minHeight: "100vh" }}>
      <Card className="card-register">
        <Card.Body>
          <div className="card-logo">
            <h2 className="text-center mb-4">Registrar</h2>
          </div>
          <Form onSubmit={onSubmit}>
            <Form.Label>Matricula:</Form.Label>
            <Form.Control
              type="matricula"
              name="matricula"
              onChange={e => {
                setMatricula(e.target.value);
              }}
              value={matricula}
              required
            />
            <Row>
              <Col>
                <Form.Label>Nombre:</Form.Label>
                <Form.Control
                  type="nombre"
                  name="nombre"
                  onChange={e => {
                    setNombre(e.target.value);
                  }}
                  value={nombre}
                  required
                />
              </Col>
              <Col>
                <Form.Label>Apellido:</Form.Label>
                <Form.Control
                  type="apellido"
                  name="apellido"
                  onChange={e => {
                    setApellido(e.target.value);
                  }}
                  value={apellido}
                  required
                />
              </Col>
            </Row>
            <Form.Label>Telefono:</Form.Label>
            <Form.Control
              type="telefono"
              name="telefono"
              onChange={e => {
                setTelefono(formatTelefono(e.target.value));
              }}
              value={telefono}
              required
            />
            <Row>
              <Col>
                <br></br>
                <Button className="w-100" type="submit">
                  Registrar
                </Button>
              </Col>
            </Row>
          </Form>
        </Card.Body>

      </Card>

      <Modal
        show={modal}
        onHide={handleClose}
        keyboard={false}
      >
        <Modal.Header>
          <Modal.Title>Informacion</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Estudiante Registrado con exito
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Ok
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  )



}

export default RegEstudiante;

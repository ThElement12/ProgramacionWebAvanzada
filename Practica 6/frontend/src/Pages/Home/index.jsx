import React, { useState, useEffect } from 'react'
import { Container, Form, Row, Col, Button, Modal } from 'react-bootstrap';
import Swal from 'sweetalert2';

import "bootstrap/dist/css/bootstrap.min.css";

import Reserv from '../../Components/Reserv';

import Reservation from '../../Models/reservation'

export default function Home() {
  const [hideDates, sethideDates] = useState(true);
  const [startDate, setStartDate] = useState(new Date());
  const [finishDate, setfinishDate] = useState(new Date());
  const [reserv, setReserv] = useState([]);

  const [showRegister, setShowRegister] = useState(false);

  const [id, setId] = useState("");
  const [nombre, setNombre] = useState("");
  const [carrera, setCarrera] = useState("");
  const [laboratorio, setLaboratio] = useState("");
  const [fecha, setFecha] = useState("");
  const [hora, setHora] = useState(8)

  useEffect(() => {
    setReserv([
      new Reservation(1, "Joseph", "ISC", "ING", new Date(2021, 7, 12, 12, 30)),
      new Reservation(2, "Robert", "ISC", "ING", new Date(2021, 7, 12, 12, 30)),
      new Reservation(3, "Alicia", "ISC", "ING", new Date(2021, 7, 12, 12, 30)),
      new Reservation(4, "Ruben", "ISC", "ING", new Date(2021, 7, 12, 12, 30)),
      new Reservation(5, "Lendry", "ISC", "ING", new Date(2021, 7, 12, 12, 30)),
      new Reservation(6, "Francisco", "ISC", "ING", new Date(2021, 7, 12, 12, 30)),
      new Reservation(7, "Ariel", "ISC", "ING", new Date(2021, 7, 12, 12, 30))
    ])
  }, [])

  const onSubmit = e => {
    e.preventDefault();

    console.log(`Desde ${startDate} hasta ${finishDate}`);
  }
  const submitReserva = e => {
    e.preventDefault();

    console.log(new Reservation(id, nombre, carrera, laboratorio, fecha));
    Swal.fire(
      'Registrado!',
      'Reserva Realizada con exito',
      'success'
    );
    clean();
    setShowRegister(false);
  }
  const handleClose = () => setShowRegister(false);

  const clean = () => {
    setId("");
    setNombre("");
    setCarrera("");
    setLaboratio("");
    setFecha("");
    setHora(8);
  }

  const modalRegister = () => {
    return (
      <Modal show={showRegister} onHide={handleClose} backdrop="static" keyboard={false}>
        <Modal.Header>
          <Modal.Title>Registrar Reserva</Modal.Title>
        </Modal.Header>
        <Form onSubmit={submitReserva}>
          <Modal.Body>
            <Form.Group>
              <Form.Label>ID</Form.Label>
              <Form.Control placerholder="ID" value={id} onChange={(e) => { setId(e.target.value) }} required />
              <Form.Label>Nombre</Form.Label>
              <Form.Control placeholder="Nombre" value={nombre} onChange={(e) => { setNombre(e.target.value) }} required />
            </Form.Group>
            <Form.Group>
              <Form.Label>Carrera</Form.Label>
              <Form.Control placeholder="Carrera" value={carrera} onChange={(e) => { setCarrera(e.target.value) }} required />
              <Form.Label>Laboratorio</Form.Label>
              <Form.Control placeholder="Laboratorio" value={laboratorio} onChange={(e) => { setLaboratio(e.target.value) }} required />
            </Form.Group>
            {' '}
            <Form.Group>
              <Row>
                <Col>
                  <Form.Label>Fecha de Reserva</Form.Label>
                </Col>
                <Col>
                  <Form.Control type="date" value={fecha} onChange={(e) => { setFecha(e.target.value) }} required />
                </Col>
              </Row>
              <br></br>
              <Row>
                <Col>
                  <Form.Label>Hora de Reserva: (24 Hrs) </Form.Label>
                </Col>
                <Col>
                  <Form.Control type="number" value={hora} min={8} max={21} onChange={(e) => { setHora(e.target.value) }}/>
                </Col>
              </Row>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Cancelar
            </Button>
            <Button variant="primary" type="submit">Registrar</Button>
          </Modal.Footer>
        </Form>
      </Modal>
    );
  }

  const datePickers = () => {
    return (
      <Container>
        <Form onSubmit={onSubmit}>
          <Row>
            <Col><Form.Control type="date" size="sm" name="startDate" value={startDate} onChange={(e) => { setStartDate(e.target.value) }} required /></Col>
            a
            <Col><Form.Control type="date" size="sm" name="finishDate" value={finishDate} onChange={(e) => { setfinishDate(e.target.value) }} required /></Col>
            <Col><Button variant="outline-info" size="sm" type="submit">Buscar</Button></Col>
          </Row>
        </Form>
        <Button variant="link" onClick={() => { sethideDates(true) }}>Ocultar</Button>
      </Container>
    );
  }

  return (
    <Container>
      <Container>
        <Button variant="outline-primary" onClick={() => { setShowRegister(true) }}>Registrar</Button>{' '}
        <Button variant="outline-secondary" onClick={() => { sethideDates(false) }}>Historial</Button>
      </Container>
      <br></br>
      <Container>
        {!hideDates && datePickers()}
      </Container>
      <br></br>
      <Reserv reservations={reserv} />
      {modalRegister()}
    </Container>
  )
}

import React, { useState, useEffect, useCallback } from 'react'
import { Container, Form, Row, Col, Button, Modal } from 'react-bootstrap';
import Swal from 'sweetalert2';

import "bootstrap/dist/css/bootstrap.min.css";

import Reserv from '../../Components/Reserv';

import reservationService from '../../Utils/reservation.service';

import Reservation from '../../Models/reservation';

export default function Home() {
  const [forceReload, setReload] = useState(true);
  const [hideDates, sethideDates] = useState(true);
  const [startDate, setStartDate] = useState(new Date());
  const [finishDate, setfinishDate] = useState("");
  const [reserv, setReserv] = useState([]);

  const [showRegister, setShowRegister] = useState(false);

  const [id, setId] = useState("");
  const [nombre, setNombre] = useState("");
  const [carrera, setCarrera] = useState("");
  const [laboratorio, setLaboratio] = useState("");
  const [fecha, setFecha] = useState("");
  const [hora, setHora] = useState(8);

  const fetchApi = useCallback(() => {
    reservationService.getReservation()
    .then(res => res.data)
    .then(res => {
      var reservations = [];
      res.data.reservations.forEach((reserv) => {
        reservations.push(new Reservation(reserv.id, reserv.name, reserv.career, reserv.lab, reserv.date));
      });
      reservations = reservations.filter(reservation => {
        const date = reservation.date.substr(0,10);
        if(new Date(startDate) > new Date(finishDate)){
          Swal.fire('Error', 'La fecha inicial debe ser menor o igual a la final', 'error');
          return false;
        }else{
          if(finishDate === ""){
            return new Date(startDate) <= new Date(date);
          }
          else{
            return new Date(startDate) <= new Date(date) && new Date(finishDate) >= new Date(date);
          }
        }
      })
      setReserv(reservations);
    })
    .catch(err => console.error(err));
  },[finishDate, startDate])

  useEffect(() => {
    fetchApi();
  }, [forceReload, fetchApi])

  //Modal Registro y funcion registrar
  const submitReserva = e => {
    e.preventDefault();
    //TODO: Ponerle matricula xd
    //TODO: Y el id tome el ultimo y le sume 1

    if(checkAvailability()){
      const reservation = {
        "id": id,
        "name": nombre,
        "career":carrera,
        "lab": laboratorio,
        "date": `${fecha} ${hora}:00`
      }
      reservationService.postReservation(reservation)
      .then(res => {
        Swal.fire(
          'Registrado!',
          'Reserva Realizada con exito',
          'success'
        );
        clean();
        setShowRegister(false);
      })
      .catch(err => console.error(err))
    }else{
      Swal.fire(
        'Error',
        'No pueden haber mas de 7 personas a la misma hora en el mismo laboratorio',
        'error'
      )
    }
  }

  const checkAvailability = () => {
    var count = 0;
    reserv.forEach((reservation) => {
      if(reservation.date === `${fecha} ${hora}:00` && reservation.lab === laboratorio){
        count++;
      }
    })
    return count < 7 ? true : false;
  }

  const handleClose = () => setShowRegister(false);

  const clean = () => {
    setId("");
    setNombre("");
    setCarrera("");
    setLaboratio("");
    setFecha("");
    setHora(8);
    setReload(!forceReload);
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

  //Filtrar por fecha
  const datePickers = () => {
    return (
      <Container>
          <Row>
            <Col><Form.Control type="date" size="sm" name="startDate" value={startDate} onChange={(e) => { setStartDate(e.target.value) }} required /></Col>
            a
            <Col><Form.Control type="date" size="sm" name="finishDate" value={finishDate} onChange={(e) => { setfinishDate(e.target.value) }} required /></Col>
          </Row>
        <Button variant="link" onClick={() => { sethideDates(true) }}>Ocultar</Button>
        <Button variant="link" onClick={() => { reset() }}>Restablecer</Button>
      </Container>
    );
  }
  const reset = () => {
    setStartDate(new Date())
    setfinishDate("")
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

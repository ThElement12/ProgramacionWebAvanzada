import React, { useState, useEffect } from 'react'
import { Container, Form, Row, Col, Button } from 'react-bootstrap';
import "bootstrap/dist/css/bootstrap.min.css";

import Reserv from '../../Components/Reserv';

import Reservation from '../../Models/reservation'

export default function Home() {
  const [hideDates, sethideDates] = useState(true);
  const [startDate, setStartDate] = useState(new Date());
  const [finishDate, setfinishDate] = useState(new Date());
  const [reserv, setReserv] = useState([]);

  useEffect(() => {
    setReserv([
      new Reservation(1, "Joseph", "ISC", "ING", new Date(2021,7,12,12,30)),
      new Reservation(2, "Robert", "ISC", "ING", new Date(2021,7,12,12,30)),
      new Reservation(3, "Alicia", "ISC", "ING", new Date(2021,7,12,12,30)),
      new Reservation(4, "Ruben", "ISC", "ING", new Date(2021,7,12,12,30)),
      new Reservation(5, "Lendry", "ISC", "ING", new Date(2021,7,12,12,30)),
      new Reservation(6, "Francisco", "ISC", "ING", new Date(2021,7,12,12,30)),
      new Reservation(7, "Ariel", "ISC", "ING", new Date(2021,7,12,12,30))
    ])
  }, [])

  const onSubmit = e => {
    e.preventDefault();
    console.log(`Desde ${startDate} hasta ${finishDate}`);
  }

  const datePickers = () => {
    return (
      <Container>
        <Form onSubmit={onSubmit}>
          <Row>
            <Col><Form.Control type="date" size="sm" name="startDate" value={startDate} onChange={(e) => {setStartDate(e.target.value)}} required /></Col>
            a
            <Col><Form.Control type="date" size="sm" name="finishDate" value={finishDate} onChange={(e) => {setfinishDate(e.target.value)}} required /></Col>
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
        <Button variant="outline-primary">Registrar</Button>{' '}
        <Button variant="outline-secondary" onClick={() => { sethideDates(false) }}>Historial</Button>
      </Container>
      <br></br>
      <Container>
        {!hideDates && datePickers()}
      </Container>
      <br></br>
      <Reserv reservations={reserv}/>
    </Container>
  )
}
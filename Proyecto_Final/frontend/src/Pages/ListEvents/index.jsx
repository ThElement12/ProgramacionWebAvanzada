import React, { useState, useEffect } from 'react'

import Navigation from '../../Components/Navigation';

import { Modal, Button, Table, Container } from 'react-bootstrap'

import EventService from '../../Services/event.service.js';

export default function ListEvents() {

  const [events, setEvents] = useState([]);
  const [modalDetails, setModalDetails] = useState(false);
  const [event, setEvent] = useState(null);

  useEffect(() => {
    if (sessionStorage.getItem("rol") === "cliente") {
      EventService.getEvent(sessionStorage.getItem("username"))
        .then(res => {
          setEvents(res.data.events)})
        .catch(err => console.error(err));
    }
    else {
      EventService.getAllEvents()
        .then(res => setEvents(res.data))
        .catch(err => console.error(err));
    }

  }, [])

  const hideModalSuccess = () => {
    setModalDetails(false);
    setEvent(null);
  }
  const showModalDetails = (event) => {
    setEvent(event);
    setModalDetails(true);
  }

  const showDetails = () => {
    return <Modal
      show={modalDetails}
      onHide={hideModalSuccess}
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Informacion</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <h5>Precio: {event.totalPrice}</h5>
        <br></br>
        <h5>Productos:</h5>
        <Container>
          <Table className="table table-bordered" hover striped responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Producto</th>
                <th>Cantidad</th>
              </tr>
            </thead>
            <tbody>
              {console.log(event)}
              {event.productRequests.map((producto, i) => (
                <tr key={i}>
                  <th>{producto.productId}</th>
                  <th>{producto.name}</th>
                  <th>{producto.requested}</th>
                </tr>
              ))}
            </tbody>
          </Table>
        </Container>
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
      <br></br>
      <Container>
        <Table className="table table-bordered" hover striped responsive>
          <thead>
            <tr>
              <th>Id</th>
              <th>Nombre</th>
              <th>Fecha Inicio</th>
              <th>Fecha Fin</th>
              <th>Detalles</th>
            </tr>
          </thead>
          <tbody>
            {events.map((event, i) => (
              <tr key={i}>
                <th>{event.id}</th>
                <th>{event.name}</th>
                <th>{event.startTime.replace('T', ' ')}</th>
                <th>{event.endTime.replace('T', ' ')}</th>
                <th>{<Button variant="link" onClick={() => { showModalDetails(event) }}>Detalles</Button>}</th>
              </tr>
            ))}
          </tbody>

        </Table>
        {modalDetails && showDetails()}
      </Container>


    </div>
  )
}

import React from 'react'
import { Container, Card, ListGroup, ListGroupItem } from 'react-bootstrap';

import ListElement from '../ListElement';

import "bootstrap/dist/css/bootstrap.min.css";

export default function AfterPay(props) {
  return (
    <Container>
      <Card>
        <Card.Header>
          Resumen de su transacción
        </Card.Header>
        <ListGroup className="list-group-flush">
          <ListGroupItem>
            Plan seleccionado: {props.event.plan.name}
          </ListGroupItem>
          <ListGroupItem>
            Fecha y hora de inicio: {props.event.startTime.replace("T", " ")}
          </ListGroupItem>
          <ListGroupItem>
            Fecha y hora de fin: {props.event.endTime.replace("T", " ")}
          </ListGroupItem>
          <ListGroupItem>
            Productos Seleccionados:
            <ListElement headers={["","id", "Producto", "Cantidad"]} data={props.event.productsRequests}/>
          </ListGroupItem>
          <ListGroupItem>
            Total: <b>USD${props.event.totalPrice}</b>
          </ListGroupItem>


        </ListGroup>

      </Card>

    </Container>
  )
}

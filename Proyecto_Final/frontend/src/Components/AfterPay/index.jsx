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
            Plan seleccionado: {props.event.plan}
          </ListGroupItem>
          <ListGroupItem>
            Fecha y hora de inicio: {props.event.date_init.replace("T", " ")}
          </ListGroupItem>
          <ListGroupItem>
            Fecha y hora de fin: {props.event.date_finish.replace("T", " ")}
          </ListGroupItem>
          <ListGroupItem>
            Productos Seleccionados:
            <ListElement headers={["Producto", "Cantidad"]} data={props.event.products}/>
          </ListGroupItem>
          <ListGroupItem>
            Total: <b>{props.event.cost}</b>
          </ListGroupItem>


        </ListGroup>

      </Card>

    </Container>
  )
}

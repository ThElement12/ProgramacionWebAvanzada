import React, { useState, useEffect } from 'react'
import { Table, Container } from 'react-bootstrap';

export default function Reserv(props) {
  const [reservation, setReservation] = useState([]);

  useEffect(() => {
    if (props.reservations) {
      setReservation(props.reservations)
    }
  }, [props.reservations])

  return (
    <Container>
      <Table className="table table-bordered" hover striped responsive>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Laboratorio</th>
            <th>Fecha y Hora</th>
          </tr>
        </thead>
        <tbody>
          {reservation.map((element, i) => (
            <tr key={i}>
              <td>element.id</td>
              <td>element.name</td>
              <td>element.career</td>
              <td>element.date</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  )
}

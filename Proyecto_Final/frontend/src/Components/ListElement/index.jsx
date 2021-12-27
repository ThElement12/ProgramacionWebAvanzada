import React from 'react'
import { Table, Container } from 'react-bootstrap';

export default function ListElement(props) {

  const t_headers = () => {
    return <thead>
      <tr>
        {
          props.headers.map((element) => (
            <th>{element}</th>
          ))
        }
      </tr>
    </thead>
  }
  const t_data = () => {
    return(
      <tbody>
        {props.data.map((element, i) => (
            <tr key={i}>
              {Object.values(element).map((info)=> (
                <th>
                  {info}
                </th>
              ))}
            </tr>
        ))}
      </tbody>
    )
  }

  return (
    <Container>
      <Table className="table table-bordered" hover striped responsive>
        {t_headers()}
        {t_data()}
      </Table>
    </Container>
  )
}

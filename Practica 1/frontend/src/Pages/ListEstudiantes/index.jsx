import React, { Component } from 'react'
import { Button, Modal, Table, Container, Col, Row, Form } from "react-bootstrap";

import { EstudianteService } from '../../Services/Estudiante.service';
import Estudiante from '../../Entities/Estudiante';

const estudianteService = new EstudianteService();
export default class ListEstudiantes extends Component {

  state = {
    listEstudiantes: [],
    modalEditar: false,
    modalConfirm: false,
    matriculaSeleccionada: "",
    modalAviso: false,
    estudiante: {
      matricula: "",
      nombre: "",
      apellido: "",
      telefono: ""
    }
  }

  componentDidMount() {
    let estudiantes = []
    estudianteService.listarEstudiantes().then((Response) => {
      estudiantes = Response.data;
      this.setState({ listEstudiantes: estudiantes })

    });
  }
  onSubmit = event => {
    event.preventDefault();
    var modiEstudiante = new Estudiante(this.state.estudiante.matricula,
      this.state.estudiante.nombre, 
      this.state.estudiante.apellido,
      this.state.estudiante.telefono)

    const estudianteService = new EstudianteService();
    estudianteService.modificarEstudiante(modiEstudiante)
      .then((Response => {
        this.setState({ modalEditar: false, modalAviso: true })

      }))
      .catch((err) => {
        console.error(err)
      })
  }

  formatTelefono = value => {
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
  showModalEditar = estudiante => {
    var editEstud = {
      matricula: estudiante.enrollment,
      nombre: estudiante.name,
      apellido: estudiante.lastName,
      telefono: estudiante.phone
    }
    
    this.setState({ modalEditar: true, estudiante: editEstud })
  }
  hideModalEditar = () => {
    this.setState({ modalEditar: false })
  }

  handleDelete = () => {
    estudianteService.borrarEstudiante(this.state.matriculaSeleccionada)
      .then((Response) => {
        this.setState({ modalConfirm: false, matriculaSeleccionada: "", modalAviso: true })
      })
      .catch((error) => {
        console.error(error)
      })

  }
  handleOpenConfirm = matricula => {
    this.setState({ modalConfirm: true, matriculaSeleccionada: matricula })
  }

  handleCloseConfirm = () => {
    this.setState({ modalConfirm: false, matriculaSeleccionada: "" })
  }
  handleCloseModalAviso = () => {
    let estudiantes = []
    estudianteService.listarEstudiantes().then((Response) => {
      estudiantes = Response.data;
      this.setState({ listEstudiantes: estudiantes, modalAviso: false })

    });
  }

  modalDelete = () => {
    return <Modal
      show={this.state.modalConfirm}
      onHide={this.handleClose}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>ATENCION!</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        Esta seguro que desea eliminar el estudiante con la matricula: {this.state.matriculaSeleccionada}?
      </Modal.Body>
      <Modal.Footer>
        <Button variant="danger" onClick={this.handleDelete}>Si</Button>
        <Button variante="secondary" onClick={this.handleClose}>No</Button>
      </Modal.Footer>
    </Modal>
  }
  modalConfirm = () => {
    return <Modal
      show={this.state.modalAviso}
      onHide={this.handleCloseModalAviso}
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Informacion</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        Operacion realizada con exito
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={this.handleCloseModalAviso}>
          Ok
        </Button>
      </Modal.Footer>
    </Modal>
  }
  modalEdit = () => {
    return <Modal
      show={this.state.modalEditar}
      onHide={this.hideModalEditar}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Editar Estudiante</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={this.onSubmit}>
          <Form.Label>Matricula:</Form.Label>
          <Form.Control
            type="matricula"
            name="matricula"
            value={this.state.estudiante.matricula}
            disabled
          />
          <Row>
            <Col>
              <Form.Label>Nombre:</Form.Label>
              <Form.Control
                type="nombre"
                name="nombre"
                onChange={e => {
                  this.setState(prevState => ({
                    estudiante: {
                      ...prevState.estudiante,
                      nombre: e.target.value
                    }
                  }))
                }}
                value={this.state.estudiante.nombre}
                required
              />
            </Col>
            <Col>
              <Form.Label>Apellido:</Form.Label>
              <Form.Control
                type="apellido"
                name="apellido"
                onChange={e => {
                  this.setState(prevState => ({
                    estudiante: {
                      ...prevState.estudiante,
                      apellido: e.target.value
                    }
                  }))
                }}
                value={this.state.estudiante.apellido}
                required
              />
            </Col>
          </Row>
          <Form.Label>Telefono:</Form.Label>
          <Form.Control
            type="telefono"
            name="telefono"
            onChange={e => {
              this.setState(prevState => ({
                estudiante: {
                  ...prevState.estudiante,
                  telefono: this.formatTelefono(e.target.value)
                }
              }))
            }}
            value={this.state.estudiante.telefono}
            required
          />
          <Row>
            <Col>
              <br></br>
              <Button className="btn btn-primary" type="submit">
                Registrar
              </Button>
              <Button className="btn btn-secondary" onClick={this.hideModalEditar}>Cancelar</Button>

            </Col>
          </Row>
        </Form>
      </Modal.Body>

    </Modal>
  }

  render() {
    return (
      <div>
        <Container>
          <Table bordered hover>
            <thead>
              <tr>
                <th>Matricula</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Telefono</th>
                <th>Opciones</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.listEstudiantes.map(
                  (estudiante) => <tr key={estudiante.enrollment}>
                    <td>{estudiante.enrollment}</td>
                    <td>{estudiante.name}</td>
                    <td>{estudiante.lastName}</td>
                    <td>{estudiante.phone}</td>
                    <td>
                      <Button onClick={() => this.showModalEditar(estudiante)} className="btn btn-primary">Editar</Button>
                      <Button onClick={() => this.handleOpenConfirm(estudiante.enrollment)} className="btn btn-danger">Eliminar</Button>
                    </td>
                  </tr>
                ).sort()}
            </tbody>
          </Table>
          {this.modalDelete()}
          {this.modalConfirm()}
          {this.modalEdit()}
        </Container>
      </div>
    )
  }
}


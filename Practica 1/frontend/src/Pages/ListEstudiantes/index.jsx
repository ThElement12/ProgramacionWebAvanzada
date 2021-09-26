import React, { Component } from 'react'
import { Button, Modal, Table, Container } from "react-bootstrap";

import { EstudianteService } from '../../Services/Estudiante.service';

const estudianteService = new EstudianteService();
export default class ListEstudiantes extends Component {

   state = {
      listEstudiantes: [],
      modalEditar: false,
      modalConfirm: false,
      matriculaSeleccionada: "",
      modalAviso: false,
   }

   componentDidMount() {
      let estudiantes = []
      estudianteService.listarEstudiantes().then((Response) => {
         estudiantes = Response.data;
         this.setState({ listEstudiantes: estudiantes })

      });
   }
   showModalEditar = estudiante => {

      this.setState({ modalEditar: true })
      console.log("Editao" + estudiante.enrollment)
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

   handleClose = () => {
      this.setState({ modalConfirm: false, matriculaSeleccionada: "" })
   }
   handleCloseModalAviso = () => {
      let estudiantes = []
      estudianteService.listarEstudiantes().then((Response) => {
         estudiantes = Response.data;
         this.setState({ listEstudiantes: estudiantes, modalAviso: false })

      });      
   }

   render() {
      return (
         <div>
            {console.log(this.state.listEstudiantes)}
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
               <Modal
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
               <Modal
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
            </Container>
         </div>
      )
   }
}


import React, { useState, useEffect } from "react";
import { Form, Button, Modal, Table, Container } from "react-bootstrap";

import Navigation from '../../Components/Navigation'

import UserService from "../../Utils/user.service";

const Users = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [modalEdit, setModalEdit] = useState(false);
  const [modalConfirm, setModalConfirm] = useState(false);
  const [modalSuccess, setModalSuccess] = useState(false);

  const [id, setID] = useState("");
  const [username, setUsername] = useState("");
  const [mail, setEmail] = useState("");

  const [userTarget, setUserTarget] = useState("");
  const [reload, setReload] = useState("");

  useEffect(() => {
    UserService.getUsers().then((response) => {
      setUsuarios(response.data);
    })

  }, [reload]);


  const showModalEdit = (user) => {
    setID(user["id"]);
    setUsername(user["username"]);
    setEmail(user["mail"]);

    setModalEdit(true);
  }
  const hideModalEdit = () => {
    setModalEdit(false);
  }

  const showModalConfirm = (user) => {
    setUserTarget(user)
    setModalConfirm(true);
  }
  const hideModalConfirm = () => {
    setModalConfirm(false);
  }

  const showModalSuccess = () => {
    setModalSuccess(true);
  }
  const hideModalSuccess = () => {
    if (modalEdit) {
      setID("");
      setUsername("");
      setEmail("");
    }else if(modalConfirm){
      setUserTarget("");
    }
    setModalConfirm(false);
    setModalEdit(false);
    setModalSuccess(false);
    setReload(!reload);

  }
  const onDelete = () => {
    UserService.deleteUser(userTarget.username)
      .then(() => {
        console.log("Usuario borrado: " + userTarget.username);
        showModalSuccess();
      })
      .catch((res) => console.error(res));
  }

  const onSubmit = event => {
    event.preventDefault();
    var user = {
      id,
      username,
      mail
    }
    UserService.editUser(user)
      .then(showModalSuccess)
      .catch((res) => console.error(res));
  }

  const edit = () => {
    return <Modal
      show={modalEdit}
      onHide={hideModalEdit}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Editar Estudiante</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={onSubmit}>
          <Form.Label>ID: </Form.Label>
          <Form.Control
            type="id"
            name="id"
            onChange={e => {
              setID(e.target.value);
            }}
            value={id}
            disabled
          />
          <Form.Label>Username: </Form.Label>
          <Form.Control
            type="username"
            name="username"
            onChange={
              e => {
                setUsername(e.target.value);
              }
            }
            value={username}
            required
          />
          <Form.Label>Correo: </Form.Label>
          <Form.Control
            type="email"
            name="email"
            onChange={
              e => {
                setEmail(e.target.value)
              }
            }
            value={mail}
            required
          />
          <br></br>
          <Button className="btn btn-primary" type="submit">
            Editar
          </Button>
          {" "}
          <Button className="btn btn-secondary" onClick={hideModalEdit}>Cancelar</Button>

        </Form>
      </Modal.Body>

    </Modal>
  }
  const success = () => {
    return <Modal
      show={modalSuccess}
      onHide={hideModalSuccess}
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Informacion</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        Operacion realizada con exito
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={() => hideModalSuccess()}>
          Ok
        </Button>
      </Modal.Footer>
    </Modal>
  }
  const confirm = () => {
    return <Modal
      show={modalConfirm}
      onHide={hideModalConfirm}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>ATENCION!</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        Esta seguro que desea eliminar al usuario: {userTarget.username}?
      </Modal.Body>
      <Modal.Footer>
        <Button variant="danger" onClick={onDelete}>Si</Button>
        <Button variante="secondary" onClick={hideModalConfirm}>No</Button>
      </Modal.Footer>
    </Modal>


  }
  return (
    <div>
    <Navigation/>
    <br></br>
    <Container>
      <Table className="table table-bordered" hover size="sm" striped responsive>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Cant. Mockups</th>
            <th>Opciones</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((elemento, i) => (
            <tr
              key={i}
            >
              <td>{elemento["id"]}</td>
              <td>{elemento["username"]}</td>
              <td>{elemento["mail"]}</td>
              <td>{elemento["roles"].length === 0 ? "N/A" : elemento["roles"][0]}</td>
              <td>{elemento["mockups"].length}</td>
              <td>
                <Button variant="warning"
                  onClick={() => showModalEdit(elemento)}
                >
                  Editar
                 </Button>
                {" "}
                {elemento["roles"][0] !== "admin" &&
                <Button variant="danger"
                  onClick={() => showModalConfirm(elemento)}>
                  Eliminar
                </Button>}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      {edit()}
      {success()}
      {confirm()}
    </Container>
    </div>
  );
}

export default Users
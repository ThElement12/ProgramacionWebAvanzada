import React, { useState, useEffect } from "react";
import { Form, Button, Card, Table, Alert, Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

import Navigation from "../../Components/Navigation";

import Event from "../../Models/Event";

import "bootstrap/dist/css/bootstrap.min.css";
import "./RegEvent.css"

const RegEvent = (props) => {
  const [plan, setPlan] = useState("");
  const [products, setProducts] = useState({});
  const [cost, setCost] = useState(0.00);
  const [initDate, setInitDate] = useState(new Date());
  const [finishDate, setFinishDate] = useState(new Date());

  const [basePlan, setBasePlan] = useState({});
  const [msgError, setmsgError] = useState("");
  const [modalSuccess, setModalSuccess] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    /*fetch plans */

  }, [])

  const onSubmit = (event) => {

    event.preventDefault();
    if (initDate > finishDate) {
      setmsgError("La fecha de inicio no puede ser mayor que la de fin")

    } else {
      let newEvent = new Event(plan, products, cost, initDate, finishDate);
      /*await AuthService.register(username, password, mail, rol)
        .then(onSuccess)
        .catch(() => {
          setmsgError("Hubo un error al registrar el usuario")
        })*/

    }

  }

  const onSuccess = () => {
    setModalSuccess(true);
    setPlan("")
    setProducts([])
    setCost(0.00)
    setInitDate(new Date())
    setFinishDate(new Date())
    setmsgError("")
  }
  const hideModalSuccess = () => {
    setModalSuccess(false);
    navigate('/home')
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

  const onChangeProduct = (productName, cant) => {
    let selectedProducts = products;
    selectedProducts[productName] = cant;
    setProducts(selectedProducts);
    

  }

  const productPlanTable = () => {
    const productos = [{ producto: "Mesa", cantidad: 30 }, { producto: "Silla", cantidad: 40 }] //TODO: Fetch productos del plan actual
    return <Table className="table table-bordered" hover striped responsive>
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Cantidad</th>
        </tr>
      </thead>
      <tbody>
        {productos.map((elemento, i) => (
          <tr key={i}>
            <td>{elemento["producto"]}</td>
            <td>{<input type="number" min={1} value={1} max={elemento["cantidad"]}
              onChange={e => onChangeProduct(elemento["producto"], e.target.value)} />}</td>
          </tr>
        ))}
      </tbody>

    </Table>

  }

  return (
    <div>
      <Navigation />
      <div className="d-flex align-items-center justify-content-center"
        style={{ minHeight: "100vh" }}>
        <Card className="card-register">
          <Card.Body>
            <h2 className="text-center mb-4">Arma tu evento</h2>
            <Form onSubmit={onSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Plan: </Form.Label>
              <Form.Control as="select" name="plan" defaultValue="Selecciona un plan..."
                onChange={(e) => {
                  setPlan([e.target.value])
                }}
                required>
                <option>Selecciona un plan...</option>
                <option value={"Pre-Boda"}>Pre-Boda</option>
                <option value={"Boda"}>Boda</option>
                <option value={"Birthday"}>Cumpleaños</option>
                <option value={"Video Evento"}>Video de Evento</option>
              </Form.Control>
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Inicio del Evento</Form.Label>
                <Form.Control type="datetime-local" name="init_date" onChange={(e) => { setInitDate(e.target.value); }} required></Form.Control>
                <Form.Label>Fin del Evento:</Form.Label>
                <Form.Control type="datetime-local" name="finish_date" onChange={(e) => { setFinishDate(e.target.value); }} required></Form.Control>
              </Form.Group>
              <Form.Group className="mb-3">
                {productPlanTable()}
              </Form.Group>
              <Form.Group className="mb-3">
              {msgError !== "" && <Alert variant="danger">{msgError}</Alert>}
              <Button className="w-100" type="submit">
                Registrar
              </Button>
              </Form.Group>
            </Form>
          </Card.Body>
        </Card>
        {success()}
      </div>
    </div>

  );

}

export default RegEvent

import React, { useState, useEffect } from "react";
import { Form, Button, Card, Table, Alert, Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

import Navigation from "../../Components/Navigation";

import Event from "../../Models/Event";
import Product from "../../Models/Product";
import ProductRequested from '../../Models/ProductRequested';

import "bootstrap/dist/css/bootstrap.min.css";
import "./RegEvent.css"

import Paypal from "../../Components/Paypal";
import AfterPay from "../../Components/AfterPay";

import EventService from '../../Services/event.service.js';

const RegEvent = (props) => {
  const [name, setName] = useState("");
  const [plan, setPlan] = useState("");
  const [products, setProducts] = useState([]);
  const [cost, setCost] = useState(0.00);
  const [initDate, setInitDate] = useState(new Date());
  const [finishDate, setFinishDate] = useState(new Date());

  const [checkout, setCheckout] = useState(false);

  const [basePlan, setBasePlan] = useState([]);
  const [msgError, setmsgError] = useState("");
  const [modalSuccess, setModalSuccess] = useState(false);
  const [afterpay, setAfterPay] = useState(false);

  const [event, setEvent] = useState(null);
  const [selectedProducts, setSelectedProducts] = useState([])

  const navigate = useNavigate();

  useEffect(() => {
    if (basePlan.length === 0) {
      EventService.getPlans()
        .then(res => {
          setBasePlan(res.data)
        })
        .catch(err => console.error(err));
    }
    if (plan !== "" && plan !== "Selecciona un plan...") {
      const n_plan = basePlan.find(element => element.name === plan);
      setProducts(n_plan.products);

    } else {
      setProducts([])
    }
  }, [plan, basePlan])

  const onSubmit = (event) => {
    event.preventDefault();
    if (initDate > finishDate) {
      setmsgError("La fecha de inicio no puede ser mayor que la de fin")
    } else {
      let selectedPlan = basePlan.find(_plan => _plan.name === plan)

      let productEvent = selectedProducts.map(products => {
        return new ProductRequested(null, products.id, products.name, products.stock)
      })
      let newEvent = new Event(null, name,initDate,finishDate,selectedPlan,sessionStorage.getItem("username"), cost, productEvent)

      setEvent(newEvent);
      setCheckout(true);
    }
  }

  const onSuccess = () => {

    EventService.postEvent(event)
      .then(() => {
        setCheckout(false);
        setAfterPay(true);
        setModalSuccess(true);
        setPlan("")
        setProducts([])
        setCost(0.00)
        setInitDate(new Date())
        setFinishDate(new Date())
        setmsgError("")
        setName("")
      })
      .catch(err => console.error(err));


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

  const onChangeProduct = (id, productName, cant, price) => {
    let arrayProducts = selectedProducts;
    const index = arrayProducts.findIndex((product) => product.id === id)
    if (cant > 0) {
      let newProduct = new Product(id, productName, cant, price);
      if (index === -1) {
        arrayProducts.push(newProduct)
      } else {
        arrayProducts[index].stock = cant;
      }
    }
    else {
      arrayProducts.splice(index, 1);
    }
    let total_cost = 0;

    for (var i = 0; i < arrayProducts.length; i++) {
      total_cost += arrayProducts[i].price * arrayProducts[i].stock;
    }
    setCost(Math.round((total_cost + Number.EPSILON) * 100) / 100);
    setSelectedProducts(arrayProducts);

  }

  const productPlanTable = () => {
    return <Table className="table table-bordered" hover striped responsive>
      <thead>
        <tr>
          <th>Id</th>
          <th>Nombre</th>
          <th>Cantidad</th>
          <th>Precio Unidad</th>
        </tr>
      </thead>
      <tbody>
        {products.map((elemento, i) => (
          <tr key={i}>
            <td>{elemento.id}</td>
            <td>{elemento.name}</td>
            <td>{<input type="number" min={0} defaultValue={0} max={elemento.stock}
              onChange={e => onChangeProduct(elemento.id, elemento.name, e.target.value, elemento.price)} />}</td>
            <td>{elemento.price}</td>
          </tr>
        ))}
      </tbody>
    </Table>
  }
  const handleChangePlan = (value) => {
    setPlan(value);
    setSelectedProducts([]);
    setCost(0.00)
  }

  return (
    <div>
      <Navigation />
      {afterpay && <AfterPay event={event} />}
      {checkout && <div className="d-flex align-items-center justify-content-center">
        <Card className="card-register">
          <Card.Body>
            <Button className="btn btn-secondary" onClick={() => { setCheckout(false) }}>{"<Volver"}</Button>
            <Paypal name={name} plan={plan} price={cost} event={event} onSuccess={onSuccess} />
          </Card.Body>
        </Card>
      </div>
      }
      {!(checkout || afterpay) && <div className="d-flex align-items-center justify-content-center">
        <Card className="card-register">
          <Card.Body>
            <h2 className="text-center mb-4">Arma tu evento</h2>
            <Form onSubmit={onSubmit}>
              <Form.Group className="mb-3">
                <Form.Label>Plan: </Form.Label>
                <Form.Control as="select" name="plan" defaultValue="Selecciona un plan..."
                  onChange={(e) => {
                    handleChangePlan(e.target.value)
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
                <Form.Label> Nombre Del Evento: </Form.Label>
                <Form.Control type="name" name="name" onChange={(e) => { setName(e.target.value); }} required></Form.Control>
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
                <Form.Label><h5><b>Costo:</b> ${cost} USD</h5></Form.Label>
              </Form.Group>
              <Form.Group className="mb-3">
                {msgError !== "" && <Alert variant="danger">{msgError}</Alert>}
                <Button type="submit" className="w-100" disabled={plan === "Selecciona un plan..." || cost === 0.00}>Procesar Pago</Button>
              </Form.Group>
            </Form>
          </Card.Body>
        </Card>
        {success()}
      </div>}
    </div>

  );

}

export default RegEvent

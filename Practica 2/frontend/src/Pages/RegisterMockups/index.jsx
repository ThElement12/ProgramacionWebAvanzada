import React, { useState, useEffect } from "react";
import { Form, Button, Card, Modal, Col, Row } from "react-bootstrap";
import { useHistory } from "react-router";
import { FormattedMessage } from "react-intl";

import Navigation from "../../Components/Navigation";

import "bootstrap/dist/css/bootstrap.min.css";
import "./RegisterMockup.css"

import ProviderWrapper from '../../Components/ProviderWrapper';

import userService from "../../Utils/user.service";
import statusCodes from "../../Utils/status-code.json";

const RegisterMockups = () => {
  const [language, setLanguage] = useState("es-es")

  useEffect(() => {
    sessionStorage.setItem('lang', language);

  }, [language]);

  const [endpointName, setEndpointName] = useState("")
  const [status, setStatusCode] = useState("");
  const [accessMethod, setAccessMethod] = useState("");
  const [headers, setHeaders] = useState([]);
  const [contentType, setContentType] = useState("");
  const [contentBody, setContentBody] = useState("");
  const [endPointDescription, setEndPointDescription] = useState("");
  const [expiryType, setExpTime] = useState("año");
  const [resTime, setResTime] = useState(0);
  const [allowJWt, setAllowJWT] = useState(false);

  const [modalSuccess, setModalSuccess] = useState(false);

  const history = useHistory();

  const onSubmit = (event) => {
    event.preventDefault();
    const newMockup = {
      "uuid": "", //Id lo pone el back
      "name": endpointName,
      "description": endPointDescription,
      "status": statusCodes[status].code,
      "method": accessMethod,
      "headers": headers.length() === 0 ? [] : JSON.parse("[" + headers + "]"),
      "contentType": contentType,
      "body": contentBody,
      "creation": null, //Dia de creacion
      "expiryType": expiryType,  //1 dia, hora, semana, mes, año
      "expiryTime": null,
      "responseTime": resTime, //En segundos
      "allowJWT": allowJWt, //Bool
    }
    console.log(JSON.parse("[" + headers + "]"))
    userService.createNewMockup(newMockup, sessionStorage.getItem('username'))
      .then(onSuccess)
      .catch(res => console.error(res))

  }
  const onSuccess = () => {
    setModalSuccess(true);
  }
  const hideModalSuccess = () => {
    setModalSuccess(false);
    history.push('/mockups')
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

  return (
    <ProviderWrapper lang={language}>
      <Navigation />
      <div className="d-flex align-items-center justify-content-end">
        <Form.Label>
          <FormattedMessage id="lang" />
        </Form.Label>
        <Form.Control as="select" defaulValue="es-es" onChange={(e) => {
                    setLanguage(e.target.value)
                  }} 
          style={{width: "auto"}}>
          <option value={"es-es"}>Español</option>
          <option value={"en-us"}>English</option>
        </Form.Control>
      </div>

      <div className="d-flex align-items-center justify-content-center"
        style={{ minHeight: "100vh" }}>

        <Card className="card-register">
          <Card.Body>
            <h2 className="text-center mb-4">
              <FormattedMessage id="title" />
            </h2>
            <Form onSubmit={onSubmit}>
              <Row>
                <Col>
                  <Form.Label><FormattedMessage id="name" /></Form.Label>
                  <Form.Control onChange={(e) => {
                    setEndpointName(e.target.value)
                  }} required></Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label><FormattedMessage id="status" /></Form.Label>
                  <Form.Control as="select" name="status" defaultValue="Elige..."
                    onChange={(e) => {
                      setStatusCode(e.target.value);
                    }}
                    required>
                    <option>Elige...</option>
                    {statusCodes.map((codes, i) => (
                      <option key={i} value={i}>
                        {`${codes.code} - ${codes.message}`}
                      </option>
                    ))}
                  </Form.Control>
                </Col>
                <Col>
                  <Form.Label><FormattedMessage id="method" /> </Form.Label>
                  <Form.Control as="select" name="content" defaultValue="Elige..."
                    onChange={(e) => {
                      setAccessMethod(e.target.value)
                    }}
                    required>
                    <option>Elige...</option>
                    <option value={"GET"}>GET</option>
                    <option value={"POST"}>POST</option>
                    <option value={"PUT"}>PUT</option>
                    <option value={"PATCH"}>PATCH</option>
                    <option value={"DELETE"}>DELETE</option>
                    <option value={"OPTION"}>OPTION</option>
                  </Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label>Content-type: </Form.Label>
                  <Form.Control as="select" name="content" defaultValue="Elige..."
                    onChange={(e) => {
                      setContentType(e.target.value)
                    }}
                    required>
                    <option>Elige...</option>
                    <option value={"application/json"}>Application/json</option>
                    <option value={"application/xml"}>Application/xml</option>
                    <option value={"text/plain"}>Text/Plain</option>
                  </Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label><FormattedMessage id="description" /></Form.Label>
                  <Form.Control as="textarea" placeholder="Descripcion..."
                    onChange={(e) => {
                      setEndPointDescription(e.target.value)
                    }} required></Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label><FormattedMessage id="headers" /></Form.Label>
                  <Form.Control className="textarea--code" as="textarea" placeholder='{ &#10;"X-Foo-Bar": "Hello World"&#10;}' style={{ height: "86px" }}
                    onChange={(e) => {
                      setHeaders(e.target.value)
                    }} ></Form.Control>
                </Col>
              </Row><Row>
                <Col>
                  <Form.Label><FormattedMessage id="body" /></Form.Label>
                  <Form.Control className="textarea--code" as="textarea" placeholder='{
                        "identity": {&#10;"id": "b06cd03f-75d0-413a-b94b-35e155444d70",&#10;"login": "John Doe"&#10;},&#10;"permissions": {&#10;"roles": [&#10;"moderator"&#10;]&#10;}&#10;}' style={{ height: "209px" }}
                    onChange={(e) => {
                      setContentBody(e.target.value)
                    }}
                  ></Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Form.Label><FormattedMessage id="expTime" /></Form.Label>
                  <Form.Control as="select" name="expiration" defaultValue="1 year"
                    onChange={(e) => {
                      setExpTime(e.target.value)
                    }}
                  >
                    <option value={"Agno"}>1 Year</option>
                    <option value={"Mes"}>1 Month</option>
                    <option value={"Semana"}>1 Week</option>
                    <option value={"Dia"}>1 Day</option>
                    <option value={"Hora"}>1 Hour</option>

                  </Form.Control>
                </Col>
                <Col>
                  <Form.Label><FormattedMessage id="resTime" /></Form.Label>
                  <Form.Control type="number" min="0" value={resTime}
                    onChange={(e) => {
                      setResTime(e.target.value)
                    }}
                    required></Form.Control>
                </Col>
              </Row>
              <Row>
                <Col>
                  <br></br>
                  <Form.Check label={<FormattedMessage id="jwt" />} onChange={(e) => {
                    setAllowJWT(e.target.checked);
                  }}></Form.Check>

                </Col>
              </Row>
              <Row>
                <Col>
                  <Button type="submit"><FormattedMessage id="create" /></Button>
                </Col>
              </Row>
            </Form>
          </Card.Body>
        </Card>
        {success()}
      </div>
    </ProviderWrapper>
  )



}

export default RegisterMockups
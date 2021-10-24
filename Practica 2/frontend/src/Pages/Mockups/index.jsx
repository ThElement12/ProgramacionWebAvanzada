import React, { useState, useEffect } from "react";
import { Form, Button, Modal, Table, Container, Col, Row } from "react-bootstrap";

import Navigation from '../../Components/Navigation'

import userService from "../../Utils/user.service";
import statusCodes from "../../Utils/status-code.json";

const Mockups = (props) => {
  const [uuid, setUuid] = useState("")
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
  const [owner, setOwner] = useState("")
  const [mockups, setMockups] = useState([]);

  const [modalEdit, setModalEdit] = useState(false);
  const [modalConfirm, setModalConfirm] = useState(false);
  const [modalSucess, setModalSucess] = useState(false);

  const [mockupTarget, setMockupTarget] = useState("");
  const [reload, setReload] = useState("");

  useEffect(() => {
    if (props.all) {
      userService.getAllMockups().then((response) => {
        setMockups(response.data);

      });
    } else {
      userService.getUserMockups(sessionStorage.getItem("username")).then((response) => {
        setMockups(response.data.mockups);
      })

    }
  }, [reload, props.all]);


  const showModalEdit = (mockup) => {
    setUuid(mockup.uuid);
    setEndpointName(mockup.name);
    setStatusCode(mockup.status);
    setAccessMethod(mockup.method);
    setHeaders(JSON.stringify(mockup.headers));
    setContentType(mockup.contentType);
    setContentBody(mockup.body);
    setEndPointDescription(mockup.description);
    setModalEdit(true);
    setStatusCode(statusValue(status))
    setOwner(mockup.owner)
  }
  const hideModalEdit = () => {
    setModalEdit(false);
  }

  const showModalConfirm = (mockup) => {
    setMockupTarget(mockup)
    setModalConfirm(true);
  }
  const hideModalConfirm = () => {
    setModalConfirm(false);
  }

  const showModalSuccess = () => {
    setModalSucess(true);
  }
  const hideModalSuccess = () => {
    if (modalEdit) {
        setUuid("")
        setEndpointName("")
        setStatusCode("")
        setAccessMethod("")
        setOwner("")
        setHeaders([])
        setContentType("")
        setContentBody("")
        setEndPointDescription("")
        setModalEdit("")
    } else if (modalConfirm) {
      setMockupTarget("");
    }

    setModalConfirm(false);
    setModalEdit(false);
    setModalSucess(false);
    setReload(!reload);
  }
  const onDelete = ()=> {
    //Borrar el usuario del back
    console.log(mockupTarget.uuid)
    userService.deleteMockup(mockupTarget.uuid)
      .then(()=>{
        showModalSuccess()
      })
      .catch(res => console.error(res))
    showModalSuccess();
  }
  const statusValue = code => {
    for(let i = 0; i < statusCodes.length; i++){

      if(statusCodes[i].code === code.toString()){
        return i;
      }
    }
    return 0;
  }
  const onSubmit = event => {
    event.preventDefault();
    const newMockup = {
      "uuid": uuid, //Id lo pone el back
      "name": endpointName,
      "description": endPointDescription,
      "status": statusCodes[status].code,
      "method": accessMethod,
      "headers": headers.length() ? [] : JSON.parse("[" + headers + "]"),
      "contentType": contentType,
      "body": contentBody,
      "creation": null, //Dia de creacion
      "expiryType": expiryType,  //1 dia, hora, semana, mes, año
      "expiryTime": null,
      "responseTime": resTime, //En segundos
      "allowJWT": allowJWt, //Bool
      "owner": owner
    }
    userService.editMockup(newMockup, owner)
      .then(showModalSuccess)
      .catch(res => console.error(res))

  }

  const edit = () => {
    return <Modal
      show={modalEdit}
      onHide={hideModalEdit}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Editar Mockup</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={onSubmit}>
          <Row>
            <Col>
              <Form.Label>Nombre: </Form.Label>
              <Form.Control value={endpointName} onChange={(e) => {
                setEndpointName(e.target.value)
              }} required></Form.Control>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Label>HTTP Status: </Form.Label>
              <Form.Control value={status} as="select" name="status" defaultValue="Elige..."
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
              <Form.Label>Access Method: </Form.Label>
              <Form.Control value={accessMethod} as="select" name="content" defaultValue="Elige..."
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
              <Form.Control  value={contentType} as="select" name="content" defaultValue="Elige..."
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
              <Form.Label>Descripcion</Form.Label>
              <Form.Control value={endPointDescription} as="textarea" placeholder="Descripcion..."
                onChange={(e) => {
                  setEndPointDescription(e.target.value)
                }} required></Form.Control>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Label>HTTP Headers</Form.Label>
              <Form.Control value={headers} className="textarea--code" as="textarea" placeholder='{ &#10;"X-Foo-Bar": "Hello World"&#10;}' style={{ height: "86px" }}
                onChange={(e) => {
                  setHeaders(e.target.value)
                }} ></Form.Control>
            </Col>
          </Row><Row>
            <Col>
              <Form.Label>HTTP Response Body</Form.Label>
              <Form.Control 
              value={contentBody}
              className="textarea--code" as="textarea" placeholder='{
                        "identity": {&#10;"id": "b06cd03f-75d0-413a-b94b-35e155444d70",&#10;"login": "John Doe"&#10;},&#10;"permissions": {&#10;"roles": [&#10;"moderator"&#10;]&#10;}&#10;}' style={{ height: "209px" }}
                onChange={(e) => {
                  setContentBody(e.target.value)
                }}
              ></Form.Control>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Label>Expiration Time</Form.Label>
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
              <Form.Label>Response Time (in seconds) </Form.Label>
              <Form.Control type="number" min="0" value={resTime}
                onChange={(e) => {
                  setResTime(e.target.value)
                }}
                required></Form.Control>
            </Col>
          </Row>
          <Row>
            <Col>
              <Button type="submit">Editar Mockup</Button>{" "}
              <Button variant="secondary" onClick={()=> {
              hideModalEdit();
            }}>Cancelar</Button>
            </Col>
          </Row>
        </Form>
      </Modal.Body>

    </Modal>
  }
  const success = () => {
    return <Modal
      show={modalSucess}
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
        Esta seguro que desea eliminar al usuario: {mockupTarget.uuid}?
      </Modal.Body>
      <Modal.Footer>
        <Button variant="danger" onClick={onDelete}>Si</Button>
        <Button variante="secondary" onClick={hideModalConfirm}>No</Button>
      </Modal.Footer>
    </Modal>
  }
  return (
    <div>
      <Navigation />
      <br></br>
      <Container>
        <Table className="table table-bordered" hover striped responsive>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Descripcion</th>
              <th>Status</th>
              <th>Metodo</th>
              <th>Endpoint</th>
              <th>JWT</th>
              <th>Fecha de Expiracion</th>
              <th>Opciones</th>
            </tr>
          </thead>
          <tbody>
            {mockups.map((elemento, i) => (
              <tr
                key={i}
              >
                <td>{elemento["name"]}</td>
                <td>{elemento["description"]}</td>
                <td>{elemento["status"]}</td>
                <td>{elemento["method"]}</td>
                <td>{`http://localhost:8082/${elemento["uuid"]}`}</td>
                <td>{elemento["token"] === null ? "N/A" : <a href="#/"><span onClick={() => { navigator.clipboard.writeText(elemento["token"]) }}>Copiar token</span></a>}</td>
                <td>{elemento["expiryTime"].slice(0, 10)}</td>
                <td>
                  <Button variant="warning"
                    onClick={() => showModalEdit(elemento)}
                  >
                    Editar
                  </Button>
                  {" "}
                  <Button variant="danger"
                    onClick={() => showModalConfirm(elemento)}>
                    Eliminar
                  </Button>
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

export default Mockups;
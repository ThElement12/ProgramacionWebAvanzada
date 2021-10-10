import React, { useState } from "react";
import { Form, Button, Card, Alert, Modal, Col, Row } from "react-bootstrap";
import { useHistory } from "react-router";

import Navigation from "../../Components/Navigation";

import "bootstrap/dist/css/bootstrap.min.css";
import "./RegisterMockup.css"

import userService from "../../Utils/user.service";
import statusCodes from "../../Utils/status-code.json";

const RegisterMockups = () => {

  const [endPoint, setEndPoint] = useState("");
  const [accessMethod, setAccessMethod] = useState("");
  const [headers, setHeaders] = useState([]);
  const [resCode, setResCode] = useState("");
  const [contentType, setContentType] = useState("");
  const [contentBody, setContentBody] = useState("");
  const [endPointName, setEndPointName] = useState("");
  const [endPointDescription, setEndPointDescription] = useState("");
  const [expTime, setExpTime] = useState("1 year");
  const [resTime, setResTime] = useState(0);
  const [allowJWt, setAllowJWT] = useState(false);

  const history = useHistory();


  return (
    <div>
      <Navigation />
      <div className="d-flex align-items-centes justify-content-center"
        style={{ minHeight: "100vh" }}>
        <Card className="card-register">
          <Card.Body>
            <h2 className="text-center mb-4">Crea tu mock</h2>
            <Form>
              <Row className="mb-3">
                <Form.Group as={Col} controlId="httpResCode">
                  <Form.Label>HTTP Status</Form.Label>
                  <Form.Select defaultValue="Elige...">
                    <option>Elige...</option>
                    
                  </Form.Select>
                </Form.Group>
              </Row>

            </Form>
          </Card.Body>
        </Card>
      </div>
    </div>
  )



}

export default RegisterMockups
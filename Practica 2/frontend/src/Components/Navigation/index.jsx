import React, { useEffect, useState } from "react";
import { useHistory, Link } from "react-router-dom";
import { Navbar, Nav, NavDropdown, Button, Container } from "react-bootstrap";

import Auth from "../../Utils/auth.service.js";

import "bootstrap/dist/css/bootstrap.min.css";
//import Logo from "../../assets/img/Logo.png";
import "./Navigation.css";

export default function Navigation() {
  const [usuario, setUsuario] = useState("");
  const history = useHistory();

  useEffect(() => {
   /* Auth.onAuthStateChanged((user) => {
      if (user) {
        setUsuario(localStorage.getItem("email"));
      }
    });*/
    setUsuario("")
  }, [usuario]);

  const logOut = () => {
    // Auth.signOut();
    setUsuario("");
    localStorage.clear();
    history.push("/login");
  };
  const loggedNavBar = () => {
    if (!sessionStorage.getItem('jwt')) {
      return <Nav className="me-auto">
        <Nav.Link className="MenuItem" as={Link} to="/home">
          Inicio
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/login">
          Ingresa
        </Nav.Link>
      </Nav>
    }else{
      return <Nav className="me-auto">
      <Nav.Link className="MenuItem" as={Link} to="/home">
        Inicio
      </Nav.Link>
      <Nav.Link className="MenuItem" as={Link} to="/mokups">
        Mis Mokups
      </Nav.Link>
      <NavDropdown title="Cuenta" id="basic-nav-dropdown">
        <NavDropdown.Item as={Link} to="/users" className="link">
          Administrar Usuarios
        </NavDropdown.Item>
        <NavDropdown.Item  as={Link} to="/register" className="link">
          Registrar Usuario
        </NavDropdown.Item>
        <NavDropdown.Item onClick={logOut} className="link">
          Cerrar Sesion
        </NavDropdown.Item>
      </NavDropdown>
    </Nav>
    }
  }

  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/home">
          Mockito
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
        {loggedNavBar()}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

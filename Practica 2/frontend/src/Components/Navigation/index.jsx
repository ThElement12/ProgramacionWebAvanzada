import React from "react";
import { useHistory, Link } from "react-router-dom";
import { Navbar, Nav, NavDropdown, Container } from "react-bootstrap";

import Auth from "../../Utils/auth.service.js";

import "bootstrap/dist/css/bootstrap.min.css";
import "./Navigation.css";

export default function Navigation() {
  const history = useHistory();

  const logOut = () => {
    Auth.logout();
    history.push("/home");
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
    } else {
      return <Nav className="me-auto">
        <Nav.Link className="MenuItem" as={Link} to="/home">
          Inicio
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/mockups">
          Mis Mokups
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/create-mockup">
          Crear Mock
        </Nav.Link>
        <NavDropdown title="Cuenta" id="basic-nav-dropdown">
          {sessionStorage.getItem('rol') === "admin" &&
            <>
              <NavDropdown.Item as={Link} to="/users" className="link">
                Administrar Usuarios
              </NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/all-mockups" className="link">
                Todos los Mockups
              </NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/register" className="link">
                Registrar Usuario
              </NavDropdown.Item>
            </>}
          <NavDropdown.Item onClick={logOut} className="link">
            Cerrar Sesion
          </NavDropdown.Item>
        </NavDropdown>
      </Nav>
    }
  }

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
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

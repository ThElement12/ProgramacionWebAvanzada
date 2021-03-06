import React from "react";
import { useNavigate, Link } from "react-router-dom";
import { Navbar, Nav, NavDropdown, Container } from "react-bootstrap";

//import Auth from "../../Utils/auth.service.js";

import "bootstrap/dist/css/bootstrap.min.css";
import "./Navigation.css";
import UserService from '../../Services/user.service.js'

export default function Navigation() {
  const navigate = useNavigate();

  const logOut = () => {
    UserService.logout();
    navigate("/home");
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
        <Nav.Link className="MenuItem" as={Link} to="/register">
          Registrate
        </Nav.Link>
      </Nav>
    }
    else if(sessionStorage.getItem('rol') === "cliente"){
      return <Nav className="me-auto">
         <Nav.Link className="MenuItem" as={Link} to="/home">
          Inicio
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/events">
          Mis Eventos
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/create-event">
          Reservar Evento
        </Nav.Link>
        <NavDropdown title="Cuenta" id="basic-nav-dropdown">
          <NavDropdown.Item onClick={logOut} className="link">
            Cerrar Sesion
          </NavDropdown.Item>
        </NavDropdown>
      </Nav>
    }
    else{
      return <Nav className="me-auto">
        <Nav.Link className="MenuItem" as={Link} to="/home">
          Inicio
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/inventory">
          Inventario
        </Nav.Link>
        <Nav.Link className="MenuItem" as={Link} to="/events">
          Todos Los Eventos
        </Nav.Link>
        <NavDropdown title="Cuenta" id="basic-nav-dropdown">
          {sessionStorage.getItem('rol') === "admin" &&
            <>
              <NavDropdown.Item as={Link} to="/users" className="link">
                Administrar Usuarios
              </NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/register-employee" className="link">
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
          {"Y & R Party Supply"}
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          {loggedNavBar()}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

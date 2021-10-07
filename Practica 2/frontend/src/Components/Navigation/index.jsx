import React, { useEffect, useState } from "react";
import { useHistory, Link } from "react-router-dom";
import { Navbar, Nav, NavDropdown, Button } from "react-bootstrap";

//import { Auth } from "../../utils/firebase";

//import Logo from "../../assets/img/Logo.png";
import "./Navigation.css";

export default function Navigation() {
  const [usuario, setUsuario] = useState("");
  const history = useHistory();

  /*useEffect(() => {
    Auth.onAuthStateChanged((user) => {
      if (user) {
        setUsuario(localStorage.getItem("email"));
      }
    });
  }, [usuario]);*/

  const logOut = () => {
   // Auth.signOut();
    setUsuario(null);
    localStorage.clear();
    history.push("/login");
  };

  return (
    <Navbar bg="light" expand="lg">
      <Navbar.Brand as={Link} to="/home">
        Mockito
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link className="MenuItem" as={Link} to="/home">
            Inicio
          </Nav.Link>
          <NavDropdown title="Portafolio" id="basic-nav-dropdown">
            <NavDropdown.Item as={Link} to="/clientes" className="link">
              Clientes
            </NavDropdown.Item>
            <NavDropdown.Item as={Link} to="/casos" className="link">
              Casos
            </NavDropdown.Item>
            <NavDropdown.Item as={Link} to="/movimientos" className="link">
              Movimientos
            </NavDropdown.Item>
          </NavDropdown>
          <NavDropdown title="Gestión" id="basic-nav-dropdown">
            <NavDropdown.Item as={Link} to="/tareas" className="link">
              Tareas
            </NavDropdown.Item>
            <NavDropdown.Item as={Link} to="/cobros" className="link">
              Cobros
            </NavDropdown.Item>
            {localStorage.getItem("Tipo Usuario") === "Administrador" && (
              <NavDropdown.Item as={Link} to="/register" className="link">
                Registrar Usuario
              </NavDropdown.Item>
            )}
          </NavDropdown>
          <Nav.Link className="MenuItem" as={Link} to="/cuenta">
            Mi Perfil
          </Nav.Link>
        </Nav>
        <Navbar.Collapse className="justify-content-end">
          <Navbar.Text>
            Correo:
            {usuario && <a href="/">{usuario}</a>}
          </Navbar.Text>
          <Button onClick={logOut} className="logout-boton" variant="danger">
            Salir
          </Button>
        </Navbar.Collapse>
      </Navbar.Collapse>
    </Navbar>
  );
}

import React from 'react'
import { Link } from "react-router-dom";
import { Navbar, Nav, } from "react-bootstrap";

import "./MenuTop.css";

export default function Menu() {
    return (
        <Navbar bg="light" expand="lg">
            <Nav className="mr-auto">
                <Nav.Link className="MenuItem" as={Link} to="/registrar">
                    Registrar
                </Nav.Link>
                <Nav.Link className="MenuItem" as={Link} to="/listar">
                    Listar Estudiantes
                </Nav.Link>
            </Nav>
        </Navbar>
    )
}

import React from 'react'
import { Container } from "react-bootstrap";
import Navigation from '../../Components/Navigation'
import "bootstrap/dist/css/bootstrap.min.css";


export default function Home() {

  const welcome = () => {
    if (sessionStorage.getItem('username'))
      return <h2>
        Bienvenido de nuevo, {sessionStorage.getItem('username')}!
      </h2>
    else
      return <h2>Bienvenido a Mockys</h2>
  }
  return (

    <div>
      <Navigation />
      <Container>
        {welcome()}
      </Container>

    </div>
  )
}

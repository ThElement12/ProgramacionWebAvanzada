import React from 'react'
import { Container } from "react-bootstrap";

import Navigation from '../../Components/Navigation';
import Graph from '../../Components/Graph';

import "bootstrap/dist/css/bootstrap.min.css";
import "./Home.css"


export default function Home() {

  const welcome = () => {
    if (sessionStorage.getItem('username'))
      return <h2>
        Bienvenido de nuevo, {sessionStorage.getItem('username')}!
      </h2>
    else
      return <h2>{"Bienvenido a Yosef & Rove Party Supply"}</h2>
  }
  const graphEvents = () => {
    //TODO: Si es admin o empleado muestre todos los eventos en general, si es usuario muestra solo los de el
    
    const testEvents = {
      'Enero': 2,
      'Febrero': 4,
      'Marzo': 5,
      'Abril': 6
    }
    return <Graph title={"Cantidad Eventos por Mes"} labels={Object.keys(testEvents)} data={Object.values(testEvents)} 
    color={'rgba(53, 162, 235, 0.5)'} single_label={"Cantidad"}/>
    

  
  }
  return (
    //TODO: Se muestre los graficos solo si esta logueado
    <div>
      <Navigation />
      <br></br>
      <Container classname="principal">
        {welcome()}
        {graphEvents()}
      </Container>

    </div>
  )
}
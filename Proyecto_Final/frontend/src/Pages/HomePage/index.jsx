import React, {useEffect, useState} from 'react'
import { Container } from "react-bootstrap";

import Navigation from '../../Components/Navigation';
import Graph from '../../Components/Graph';

import EventService from '../../Services/event.service';

import "bootstrap/dist/css/bootstrap.min.css";
import "./Home.css"


export default function Home() {
  const [events, setEvents] = useState([])

  useEffect(()=>{
    if (sessionStorage.getItem("rol") === "cliente") {
      EventService.getEvent(sessionStorage.getItem("username"))
        .then(res => setEvents(res.data))
        .catch(err => console.error(err));
    }
    else {
      EventService.getAllEvents()
        .then(res => setEvents(res.data))
        .catch(err => console.error(err));
    }


  })

  const welcome = () => {
    if (sessionStorage.getItem('username'))
      return <h2>
        Bienvenido de nuevo, {sessionStorage.getItem('username')}!
      </h2>
    else
      return <h2>{"Bienvenido a Yosef & Rove Party Supply"}</h2>
  }

  const graphEvents = () => {
    let count = [0,0,0,0,0,0,0,0,0,0,0,0]
    for(let i = 0; i < events.length; i++){
      count[new Date(events[i].startTime).getMonth()]++
    }
    const months=["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre",
    "Octubre","Noviembre","Diciembre"]

    return <Graph title={"Cantidad Eventos por Mes"} labels={months} data={count} 
    color={'rgba(53, 162, 235, 0.5)'} single_label={"Cantidad"}/>
  }
  return (
    <div>
      <Navigation />
      <br></br>
      <Container classname="principal">
        {welcome()}
        {sessionStorage.getItem("username") && graphEvents()}
      </Container>

    </div>
  )
}
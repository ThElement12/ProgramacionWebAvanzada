import React, {useState, useEffect} from 'react'

import ListElement from '../../Components/ListElement';
import Navigation from '../../Components/Navigation';

import EventService from '../../Services/event.service.js';

export default function ListEvents() {
  const [events, setEvents] = useState([])
  useEffect(() => {
    EventService.getAllEvents()
    .then(res => setEvents(res.data))
    .catch(err => console.error(err));

  }, [])
  //TODO: Listar eventos
  return (
    <div>
      <Navigation/>
      <br></br>
      <div>
        <ListElement data={events} headers={['Id', 'Nombre', 'Fecha Inicio', 'Fecha Fin', 'Detalles']}/>
      </div>

      
    </div>
  )
}

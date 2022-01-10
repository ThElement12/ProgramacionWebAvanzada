import React, {useState, useEffect} from 'react'

import ListElement from '../../Components/ListElement';
import Navigation from '../../Components/Navigation';

import EventService from '../../Services/event.service.js';

export default function Inventory() {
  const [producto, setProductos] = useState([])

  useEffect(() => {
    EventService.getProducts()
    .then(res => setProductos(res.data))
    .catch(err => console.error(err));
  }, [])

  return (
    <div>
      <Navigation />
      <br></br>
      <div>
        <ListElement headers={['Id', 'Nombre',  'Valor','Stock']} data={producto} />
      </div>
    </div>
  )
}

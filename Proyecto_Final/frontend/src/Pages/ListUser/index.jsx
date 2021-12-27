import React from 'react'

import ListElement from '../../Components/ListElement';
import Navigation from '../../Components/Navigation';

import Client from '../../Models/Client';


export default function ListUser() {
  const users = [
    new Client('Prueba', "Joe@doe.com", "no"),
    new Client('Prueba1', "joe@doe.com", "Ola"),
    new Client('Prueba2', "Joe@doe.com", "ke")
  ]
  return (
    <div>
      <Navigation />
      <br></br>
      <div>
        <ListElement headers={['Nombre', 'Email', 'Eventos']} data={users} />
      </div>
    </div>
  )
}

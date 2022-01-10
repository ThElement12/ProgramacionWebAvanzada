import React from 'react'

import ListElement from '../../Components/ListElement';
import Navigation from '../../Components/Navigation';
import User from '../../Models/User';



export default function ListUser() {
  const users = [
    new User('Prueba', "Joe@doe.com", "no"),
    new User('Prueba1', "joe@doe.com", "Ola"),
    new User('Prueba2', "Joe@doe.com", "ke")
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

import React, {useState, useEffect} from 'react'

import ListElement from '../../Components/ListElement';
import Navigation from '../../Components/Navigation';

import UserService from '../../Services/user.service.js'

export default function ListUser() {
  const [users, setusers] = useState([])

  useEffect(() => {
    UserService.getUsers()
    .then(res => {
      const newData = res.data;
      for(let data of newData){
        delete data.password
      }
      setusers(res.data)
    })
    .catch(err => console.error(err));
  }, [])

  return (
    <div>
      <Navigation />
      <br></br>
      <div>
        <ListElement headers={['Id', 'Nombre de Usuario', 'Nombre Completo', 'Email', 'Rol']} data={users} />
      </div>
    </div>
  )
}

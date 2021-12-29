import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate
} from "react-router-dom";
import { Helmet } from 'react-helmet';
import HomePage from './Pages/HomePage';
import Login from './Pages/Login';
import Register from './Pages/Register';
import RegEvent from './Pages/RegEvent';
import ListUser from './Pages/ListUser';
import ListEvents from './Pages/ListEvents';
import Inventory from './Pages/Inventory';


function App() {
  //TODO: quitar comentarios cuando se conecte con el back
  return (
    <div>
      <Helmet>
        <style>{'body {background-color: gray;}'}</style>
      </Helmet>
    <Router>
      <Routes>
        <Route exact path='/home' element={<HomePage/>} />
        <Route exact path='/login' element={/*sessionStorage.getItem('username') ? <Navigate to='/home'/> :*/ <Login />} />
        <Route exact path="/register" element={/*sessionStorage.getItem('username') ? <Navigate to='/home'/> :*/ <Register rol="user"/>} />
        <Route exact path='/events' element={/*!sessionStorage.getItem('username') ? <Navigate to='/home'/> :*/ <ListEvents/>} />
        <Route exact path='/inventory' element={/*sessionStorage.getItem('rol') === "cliente" ? <Navigate to='/home'/> :*/ <Inventory/>} />
        <Route exact path="/register-employee" element={/*sessionStorage.getItem('rol') !== "admin" ? <Navigate to='/home'/> :*/ <Register rol="employee"/>} />
        <Route exact path='/users' element={/*sessionStorage.getItem('rol') !== "admin" ? <Navigate to='/home'/> :*/ <ListUser/>} />
        <Route exact path='/create-events' element={/*sessionStorage.getItem('rol') !== "cliente" ? <Navigate to='/home'/> :*/ <RegEvent/>} />
        <Route path="*" element={<Navigate to='/home'/>}/>
      </Routes>
    </Router>
    </div>

  );
}

export default App;

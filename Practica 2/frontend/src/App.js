import './App.css';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
  useLocation

} from "react-router-dom";
import Home from './Pages/Home';
import Login from './Pages/Login';
import Register from './Pages/Register';
import Mockups from './Pages/Mockups';
import Users from './Pages/Users';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path='/home' component={Home}/>
        <Route exact path='/login' render={
          () => {
            if(sessionStorage.getItem('username'))
              return <Home/>
            else
              return <Login/>
          }
        }/>
        <Route exact path="/register" render = {
          () => {
            if(sessionStorage.getItem('rol') === 'admin')
              return <Register/>
            else
              return <Home/>
          }
        }/>
        <Route exact path='/mockups' render={
          () => {
            if(sessionStorage.getItem('username'))
              return <Mockups/>
            else
              return <Home/>
          }
        }/>
        <Route exact path="/users" render = {
          () => {
            if(sessionStorage.getItem('rol') === 'admin')
              return <Users/>
            else
              return <Home/>
          }
        }/>
        <Route exact path="/all-mockups" render = {
          () => {
            if(sessionStorage.getItem('rol') === 'admin')
              return <Mockups/> //Agregar props para saber si es de el admin
            else
              return <Home/>
          }
        }/>
        
        <Route exact path='/'
          render={
            () => {
                return <Redirect to='/home' />
            }
          }
        />
        <Route render={() => <Redirect to='/' />} />
      </Switch>

    </Router>
  );
}

export default App;

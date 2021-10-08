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
import Navigation from './Components/Navigation';
import Register from './Pages/Register';

function App() {
  return (
    <Router>
      <Navigation/>
      <Switch>
        <Route exact path='/home' render = {
          () => {
            return <Home/>
          }
        }/>
        <Route exact path='/login' render={
          () => {
            return <Login/>
          }
        }/>
        <Route exact path="/register" render = {
          () => {
            return <Register/>
          }
        }/>
        <Route exact path='/'
          render={
            (props) => {
              if (localStorage.getItem('email') !== null)
                return <Redirect to='/home' />
              else
                return <Redirect to='login' />
            }
          }
        />
        <Route render={() => <Redirect to='/' />} />
      </Switch>

    </Router>
  );
}

export default App;

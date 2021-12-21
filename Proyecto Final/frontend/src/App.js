import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import HomePage from './Pages/HomePage';
import Login from './Pages/Login';
import Register from './Pages/Register';

function App() {

  return (
    <Router>
      <Routes>
        <Route exact path='/home' component={HomePage} />
        <Route exact path='/login' render={
          () => {
            if (sessionStorage.getItem('username'))
              return <HomePage />
            else
              return <Login />
          }
        } />
        <Route exact path="/register" render={
          () => {
            if (sessionStorage.getItem('rol') === 'admin')
              return <Register />
            else
              return <HomePage />
          }
        } />
        <Route exact path='/'
          render={
            () => {
              return <Navigate to='/home' />
            }
          }
        />
        <Route render={() => <Navigate to='/' />} />
      </Routes>

    </Router>
  );
}

export default App;

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

function App() {

  return (
    <div>
      <Helmet>
        <style>{'body {background-color: gray;}'}</style>
      </Helmet>
    <Router>
      <Routes>
        <Route exact path='/home' element={<HomePage/>} />
        <Route exact path='/login' element={sessionStorage.getItem('username') ? <HomePage /> : <Login />} />
        <Route exact path="/register" element={sessionStorage.getItem('username') ? <HomePage /> : <Register />} />
        <Route path="*" element={<Navigate to='/home'/>}/>
      </Routes>
    </Router>
    </div>

  );
}

export default App;

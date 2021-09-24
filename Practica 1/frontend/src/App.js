import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import './App.css';

//Components
import ListEstudiantes from "./Component/ListEstudiantes";
import RegEstudiante from "./Component/RegEstudiante";


function App() {
  return (
    <div className="App">
      <Router>
        <ul>
          <li>
            <Link to="/registrar">Registrar</Link>
          </li>
          <li>
            <Link to="/listar">Listar</Link>
          </li>
        </ul>
        <Switch>
          <Route exact path="/registrar">
            <RegEstudiante/>
          </Route>
          <Route exact path="/listar">
            <ListEstudiantes/>
          </Route>

        </Switch>

      </Router>

    </div>
  );
}

export default App;

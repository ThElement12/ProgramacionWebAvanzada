import React from "react";
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import './App.css';

//Pages
import ListEstudiantes from "./Pages/ListEstudiantes";
import RegEstudiante from "./Pages/RegEstudiante";

//Components
import Menu from "./Component/Menu";

function App() {
  return (
    <div className="App">
      <Router>
        <Menu />
        <Switch>
          <Route exact path="/registrar">
            <RegEstudiante />
          </Route>
          <Route exact path="/listar">
            <ListEstudiantes />
          </Route>
          <Route render={() => <Redirect to={{pathname: "/registrar"}} />} />
        </Switch>

      </Router>

    </div>
  );
}

export default App;

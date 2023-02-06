import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import CreateUser from "./pages/CreateUser";
import Home from "./pages/Home";
import Graph from "./pages/Graph";

function App() {
    return (
        <div className="page">
            <Router>
                <Switch>
                    <Route exact path="/"><CreateUser/></Route>
                    <Route path="/home"><Home/></Route>
                    <Route path="/graph"><Graph/></Route>
                </Switch>
            </Router>
        </div>

    )
}

export default App;
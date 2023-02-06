import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import CreateUser from "./pages/CreateUser";
import Home from "./pages/Home";
import Graph from "./pages/Graph";

/**
 * The main component of the application.
 * Renders the correct component depending on the current route.
 *
 * @returns {JSX.Element} The component to be rendered
 */
function App() {
    return (
        <div className="page">
            <Router>
                <Switch>
                    <Route exact path="/">
                        {/* Renders the CreateUser component */}
                        <CreateUser/>
                    </Route>
                    <Route path="/home">
                        {/* Renders the Home component */}
                        <Home/>
                    </Route>
                    <Route path="/graph">
                        {/* Renders the Graph component */}
                        <Graph/>
                    </Route>
                </Switch>
            </Router>
        </div>
    )
}

export default App;

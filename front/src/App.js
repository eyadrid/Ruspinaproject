import "./App.css";
import Navbar from "./Components/Navbar";
import Home from "./Components/pages/Home";
import AppFooter from "./Components/footerr";
import Course from "./Components/Courses/Course"; 
import { BrowserRouter as Router, Route,Routes } from "react-router-dom";
import Login from "./Components/Login/index";
import SignUp from "./Components/Signup/index";
function App(){
  return (
    <div className="App">
      <Router>
        <Navbar/>
        <Routes>
          <Route path="/" exact Component={Home}/>
          <Route path="/Courses" exact Component={Course}/>
          <Route path="/Login/index" Component={Login}/>
          <Route path="/Signup/index" Component={SignUp} />

        </Routes>
        <AppFooter/>
      </Router>
    </div>
  );
}
export default App;
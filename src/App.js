import "./App.css";
import Navbar from "./frontend/Components/Navbar/index";
import Home from "./frontend/Components/pages/Home";
import AppFooter from "./frontend/Components/footerr/index";
import Course from "./frontend/Components/Courses/Course"; 
import { BrowserRouter as Router, Route,Routes } from "react-router-dom";
import Login from "./frontend/Components/Login/index";
import SignUp from "./frontend/Components/Signup/index";
import PageContent from "./frontend/Components/PageContent/index";
import AdminSettings from "./frontend/Components/AdminSettings/AdminSettings";
import { AuthProvider } from "./frontend/Components/AuthContext/index";
function App(){
  return (
    <div className="App">
      <AuthProvider>
        <Router>
          <Navbar/>
          <Routes>
            <Route path="/" exact Component={Home}/>
            <Route path="/Courses" exact Component={Course}/>
            <Route path="/Login/index" Component={Login}/>
            <Route path="/Signup/index" Component={SignUp} />
            <Route path="/PageContent" Component={PageContent}/>
            <Route path="/AdminSettings" Component={AdminSettings}/>
          </Routes>
            <AppFooter/>
         </Router>
      </AuthProvider>
    </div>
  );
}
export default App;
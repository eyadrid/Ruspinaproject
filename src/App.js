import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Login, SignUp, Course, Footer, Navbar, PrivateRoute } from "./frontend/Components/index";
import { DashBoard, Home, Contact, About } from "./frontend/pages/index";
import { AuthProvider } from "./frontend/Components/authContext/authContext";
function App() {
  return (
    <div className="App">
      <AuthProvider>
        <Router>
          <Navbar />
          <Routes>
            <Route path="/" Component={Home} />
            <Route path="/Courses" Component={Course} />
            <Route path="/Login/index" Component={Login} />
            <Route path="/Signup/index" Component={SignUp} />
            <Route path="/contact" Component={Contact}/>
            <Route path="/about" Component={About}/>
            <Route path="/dashboard" element=
              {
                <PrivateRoute>
                  <DashBoard />
                </PrivateRoute>
              }
            />            
            <Route path="*" element={<h1>Not Found</h1>} />
          </Routes>
          <Footer />
        </Router>
      </AuthProvider>
    </div>
  );
}
export default App;
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Login, SignUp, Course, Footer, Navbar, PageContent, PrivateRoute, Slider,Loader, BreadCrumb, ArticleDetails } from "./frontend/Components/index";
import { DashBoard, Home , About, Contact} from "./frontend/pages/index";
import { AuthProvider } from "./frontend/Components/authContext/authContext";
import { Provider } from "react-redux";
import store from './frontend/redux/store'
function App() {

    

  return (
    <div className="App">
      <Provider store = {store}>
        <Loader />
        <AuthProvider>
          <Router>
            <Navbar />
            <Routes>
              <Route path="/" Component={Home} />
              <Route path="/courses" Component={Course} />
              <Route path="/login" Component={Login} />
              <Route path="/signup" Component={SignUp} />
              <Route path="/about" Component={About} />
              <Route path="/contact" Component={Contact} />
              <Route path="/pageContent" Component={PageContent} />
              <Route path="/article/:articleId" element={<ArticleDetails />} />
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
      </Provider>
    </div>
  );
}
export default App;
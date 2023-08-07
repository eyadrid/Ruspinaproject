import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from 'antd';
import { AuthContext } from '../AuthContext';
import '../styles/Navbar.css';
import Logo from '../images/logo1.png';

function Navbar() {
  const navigate = useNavigate();
  const { user, toggleLogin } = useContext(AuthContext);

  const handleLoginClick = () => {
    navigate('./login/index');
  };

  const handleLogoutClick = () => {
    toggleLogin();
    navigate('/'); // Navigue vers la page d'accueil (Home)
  };

  return (
    <div className='navbar'>
      <div className='leftSide'>
        <img src={Logo} alt="Logo" />
      </div>
      <div className='rightSide'>
        <Link to="/">Home</Link>
        <Link to="/Courses">Our Training Courses</Link>
        <Link to="/About">About us</Link>
        <div className='loginButton'>
          {user.isLoggedIn ? (
            <Button onClick={handleLogoutClick}>Logout</Button>
          ) : (
            <Button onClick={handleLoginClick}>Login</Button>
          )}
        </div>
      </div>
    </div>
  );
}

export default Navbar;

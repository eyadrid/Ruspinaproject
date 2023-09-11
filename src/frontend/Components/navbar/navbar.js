import React, { useContext, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from 'antd';
import { useAuth } from '../authContext/authContext';
import './navbar.css';
import Logo from '../images/logo1.png';
import { useState } from 'react';

import { AuthActions } from '../../redux/actions';
import { useSelector, useDispatch } from 'react-redux';

function Navbar() {

  const auth = useSelector(state => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();


  const handleLoginClick = () => {
    navigate('./login');
  };

  const handleLogoClick = () => {
    navigate('/');
  }
  const handleLogoutClick = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    dispatch(AuthActions.logout());

    navigate('/');
  };


  return (
    <div className='navbar'>
      <div className='leftSide'>
        <img src={Logo} onClick={handleLogoClick} alt="Logo" />
      </div>
      <div className='rightSide'>
        <Link to="/">Home</Link>
        <Link to="/courses">Our Training Courses</Link>
        <Link to="/about">About us</Link>
        <Link to="/contact">Contact Us</Link>
        {(auth.user?.role?.name === 'ADMIN' && auth.isAuthenticated) &&
          <Link to="/dashboard">DashBoard</Link>
        }
        <div className='loginButton'>
          {(auth.isAuthenticated) ? (
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

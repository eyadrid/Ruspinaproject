import React from 'react';
import { Link } from 'react-router-dom';
import "../styles/Navbar.css";
import Logo from "../images/logo1.png";
import { Button } from 'antd';
import { useNavigate } from 'react-router-dom';

function Navbar(){
    const navigate = useNavigate();
    const handleLoginClick = () => {
        navigate('./login/index');
    };

    return(
        <div className='navbar'>
            <div className='leftSide' >
                <img src={Logo} alt="Logo" />            
            </div>
            <div className='rightSide'>
               <Link to="/">Home</Link> 
               <Link to="/Courses">Our Training Courses</Link>
               <Link to="/About">About us</Link> 
               <div className='loginButton'>
                    <Button onClick={handleLoginClick}>Login</Button>
                </div>
            </div>
        </div>
    );
}

export default Navbar;
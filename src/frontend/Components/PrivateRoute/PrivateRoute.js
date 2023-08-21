import React from 'react'
import { Navigate } from 'react-router-dom';
const PrivateRoute = ({children}) => {
    
    const accessToken = localStorage.getItem('token');
    return (
        accessToken ? children : <Navigate to='/Login/index'/> 
  )
}

export default PrivateRoute;

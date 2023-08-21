import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const storedUser = localStorage.getItem('user');
  const initialUser = storedUser
    ? JSON.parse(storedUser)
    : { isLoggedIn: false, isAdmin: false };

  const [user, setUser] = useState(initialUser);

  useEffect(() => {
    localStorage.setItem('user', JSON.stringify(user));
  }, [user]);

  const toggleLogin = () => {
    setUser((prevUser) => ({ ...prevUser, isLoggedIn: !prevUser.isLoggedIn }));
  };

  return (
    <AuthContext.Provider value={{ user, toggleLogin }}>
      {children}
    </AuthContext.Provider>
  );
};

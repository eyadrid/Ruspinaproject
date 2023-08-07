import React, { useContext, useState } from 'react';
import { AuthContext } from '../AuthContext';
import { SettingOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const PageContent = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate(); // Initialize useNavigate

  const handleSettingClick = () => {
    if (user.isAdmin) {
      navigate('/AdminSettings'); // Navigate to AdminSettings when clicked
    }
  };

  return (
    <div className='body'>
      <h1>Welcome to Page Content</h1>
      
      {user.isAdmin && (
        <div>
          <SettingOutlined style={{ fontSize: '24px', cursor: 'pointer' }} onClick={handleSettingClick} />
        </div>
      )}
      
      {/* Other content */}
    </div>
  );
};

export default PageContent;

import React, { useContext } from 'react';
import { AuthContext } from '../AuthContext';
import { SettingOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const PageContent = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSettingClick = () => {
    if (user.isAdmin) {
      navigate('/dashboard');
    }
  };

  return (
    <div className='body'>
      
      {user.isAdmin && (
        <div>
          <SettingOutlined style={{ fontSize: '24px', cursor: 'pointer' }} onClick={handleSettingClick} />
        </div>
      )}
    </div>
  );
};

export default PageContent;

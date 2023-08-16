import React, { useContext } from 'react';
import { Form, Input, Button, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../AuthContext';
import '../Login/index.css';

const Login = () => {
  const navigate = useNavigate();
  const { toggleLogin } = useContext(AuthContext);
  
  const onFinish = (values) => {
    console.log('Form values:', values);
    toggleLogin();
    navigate('/PageContent');
  };
  const handleSignUpClick = () => {
    navigate('/Signup/index');
  };

  return (
    <div className='loginfo'>
      <Form name="login-form" onFinish={onFinish}>
        <Typography.Title>Login</Typography.Title>
        <Form.Item
          label="User Name"
          name="username"
          rules={[{ required: true, message: 'Ce champ est obligatoire' }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: 'Ce champ est obligatoire' }]}
        >
          <Input.Password />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">Login</Button>
          <Button type="primary" onClick={handleSignUpClick} style={{ marginLeft: '20px' }}>
            SignUp
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default Login;

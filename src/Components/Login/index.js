import React from 'react';
import { Form, Input, Button,Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import "../styles/Login.css";


const Login = () => {
  const onFinish = (values) => {
    console.log('Form values:', values);
  };
  const navigate = useNavigate();
  const handleSignUpClick = () => {
    navigate('/Signup/index');
  };

  return (
    <div className='loginfo'>

        <Form name="login-form" onFinish={onFinish}>  
            <Typography.Title>Login</Typography.Title>      
            <Form.Item label="User Name" name="username" rules={[{ required: true, message: 'Ce champ est obligatoire' }]}>
                <Input />
            </Form.Item>
            <Form.Item label="Password" name="password" rules={[{ required: true, message: 'Ce champ est obligatoire' }]}>
                <Input.Password />
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit">Login</Button>
                <Button type="primary" htmlType="submit" onClick={handleSignUpClick}>SignUp</Button>
            </Form.Item>
        </Form>
    </div>
  );
};

export default Login;
import React, { useState } from 'react';
import { Form, Input, Button, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import { LoginService } from "../../service/login/AuthService"
import './login.css';

const Login = () => {
  const navigate = useNavigate();
  const [data, setData] = useState({
    email: "",
    password: "",
  });

  const handleChangeEmail = (event) =>{
      setData({
          ...data,
          email: event.target.value
      })
  };
  const handleChangePassword = (event) =>{
      setData({
          ...data,
          password: event.target.value
      })
  };
  const handleSubmit = (e)=>{
      e.preventDefault()
      LoginService(data).then((res)=>{
          localStorage.setItem('token', res.data.accessToken);
          localStorage.setItem('refreshToken', res.data.refreshToken);
          localStorage.setItem('user', JSON.stringify(res.data.userEntityDTO));

      }).catch((e)=>{
        console.log(e);
      })
    }
    const handleSignUpClick = () => {
      navigate('/Signup/index');
    };

  return (
    <div className='loginfo'>
      <Form name="login-form" >
        <Typography.Title>Login</Typography.Title>
        <Form.Item
          label="Email"
          name="email"
          rules={[{ required: true, message: 'Ce champ est obligatoire' }]}
        >
          <Input value={data.email} onChange={handleChangeEmail}/>
        </Form.Item>
        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: 'Ce champ est obligatoire' }]}
        >
          <Input.Password value={data.password} onChange={handleChangePassword} />
        </Form.Item>
        <Form.Item>
          <Button onClick={handleSubmit} type="primary" >Login</Button>
          <Button type="primary" onClick={handleSignUpClick} style={{ marginLeft: '20px' }}>
            SignUp
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default Login;

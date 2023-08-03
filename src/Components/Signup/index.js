import React from 'react';
import { Form, Input, Button, Typography } from 'antd';
import "../styles/signup.css";
const SignUp = () => {
  const onFinish = (values) => {
    console.log('Form values:', values);
  };

  return (
    <div className='inscrireinfo'>
      <Form name="signup-form" onFinish={onFinish}>
        <Typography.Title>SignUp</Typography.Title>
        <Form.Item label="User Name" name="username" rules={[{ required: true, message: 'Ce champ est obligatoire' }]}>
          <Input />
        </Form.Item>
        <Form.Item label="Password" name="password" rules={[{ required: true, message: 'Ce champ est obligatoire' }]}>
          <Input.Password />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" block>SignUp</Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default SignUp;

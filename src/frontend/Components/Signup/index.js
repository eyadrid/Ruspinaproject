import React, { useState } from 'react';
import { Form, Input, Button, Typography, message } from 'antd';
import "../Signup/index.css";

const SignUp = () => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isSignUpSuccessful, setIsSignUpSuccessful] = useState(false);

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const validatePassword = (_, value) => {
    if (value && value !== password) {
      return Promise.reject('Passwords do not match');
    }
    return Promise.resolve();
  };

  const onFinish = (values) => {
    console.log('Form values:', values);
    setIsSignUpSuccessful(true);
    sendVerificationEmail(values.email);
  };

  const sendVerificationEmail = (email) => {
    console.log(`Sending verification email to ${email}`);
    message.success('Verification email sent successfully');
  };

  return (
    <div className="inscrireinfo">
      {isSignUpSuccessful && (
        <div className="success-message">
          <Typography.Text strong>Sign up successful!</Typography.Text>
          <Typography.Text>Please check your email for verification.</Typography.Text>
        </div>
      )}
      <Form name="signup-form" onFinish={onFinish}>
        <Typography.Title>Sign Up</Typography.Title>
        <Form.Item
          label="Email"
          name="email"
          rules={[
            { required: true, message: 'Please enter your email' },
            { type: 'email', message: 'Please enter a valid email' },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Password"
          name="password"
          rules={[
            { required: true, message: 'Please enter your password' },
            { min: 6, message: 'Password must be at least 6 characters' }
          ]}
        >
          <Input.Password onChange={handlePasswordChange} />
        </Form.Item>
        <Form.Item
          label="Confirm Password"
          name="confirmPassword"
          rules={[
            { required: true, message: 'Please confirm your password' },
            { validator: validatePassword },
          ]}
        >
          <Input.Password onChange={handleConfirmPasswordChange} />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Sign Up
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default SignUp;

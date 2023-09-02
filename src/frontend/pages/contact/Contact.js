import React from "react";
import image from "../../assets/image.jpg";
import { Button, Form, Input } from "antd";
import '../contact/contact.css';

function Contact() {
    return(
        <><div className="contact">
            <div className="leftSide" style={{ backgroundImage: `url(${image})`}}>

            </div>
            <div className="rightSide">
                <h1>Contact Us</h1>
                <Form id="contact-form" method="Post">
                    <label htmlFor="name">Full Name</label>
                    <Input name="name" placeholder="Enter full name.." type="text"/>
                    <label htmlFor="email">Email</label>
                    <Input name="email" placeholder="Enter email.." type="email"/>
                    <label htmlFor="message">Message</label>
                    <textarea rows="6" placeholder="Enter message..." name="message" required ></textarea>
                    <Button htmlType="submit" type="primary"> Send Message</Button>
                </Form>
            </div>
        </div></>
    )
}
export default Contact;
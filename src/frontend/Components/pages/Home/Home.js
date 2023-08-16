import React from 'react';
import img from "../../images/R.jpeg";
import "../Home/home.css";

function Home() {
  return (
    <div className='home' style={{backgroundImage: `url(${img})`}}>
      <div className='headerContainer'>
        <h1>Welcome to Our Training Center</h1>
        <a> We offer a wide range of courses to enhance your skills and knowledge in various fields.</a>
      </div>
    </div>
  );
}

export default Home;

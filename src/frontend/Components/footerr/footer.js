import React from 'react';
import { FacebookOutlined, TwitterOutlined, InstagramOutlined, YoutubeOutlined } from '@ant-design/icons';
import "./footer.css";
import { Layout, Row} from 'antd';

const { Footer } = Layout;


const AppFooter = () => {
  return (
    <div className='Footer'>
    <Footer>
      <Row justify={"center"}>
          <div style={{ padding: '20px' }}>
            <h3>About us</h3>
            <p>Adresse : Bizerte, Rue Hsan Nouiri Bloc A 2ème étage Bureau 205</p>
            <p>Téléphone : +216 54 431 803</p>
            <p>Email : ruspinabizerteformation@gmail.com</p>
          </div>
          <div style={{ padding: '20px' }}>
            <h3>Follow us</h3>
            <div>
              <a href="https://www.facebook.com/monsiteweb" target="_blank" rel="noopener noreferrer">
                <FacebookOutlined style={{ fontSize: '20px', marginRight: '10px' }} />
              </a>
              <a href="https://www.twitter.com/monsiteweb" target="_blank" rel="noopener noreferrer">
                <TwitterOutlined style={{ fontSize: '20px', marginRight: '10px' }} />
              </a>
              <a href="https://www.instagram.com/monsiteweb" target="_blank" rel="noopener noreferrer">
                <InstagramOutlined style={{ fontSize: '20px', marginRight: '10px' }} />
              </a>
              <a href="https://www.youtube.com/c/monsiteweb" target="_blank" rel="noopener noreferrer">
                <YoutubeOutlined style={{ fontSize: '20px' }} />
              </a>
            </div>
          </div>
      </Row>
    </Footer>
    </div>
  );
};

export default AppFooter;

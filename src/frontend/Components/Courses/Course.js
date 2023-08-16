import React, { useContext} from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from "antd";
import { AuthContext } from '../AuthContext';
import "../Courses/index.css"

function Course() {
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const handleEnrollClick = (courseName) => {
    if (user.isLoggedIn) {
      navigate('/PageContent');
    } else {
      navigate('/Login/index');
    }
  };
  return (
    <div className='Menu'>
      <h1 style={{marginLeft: '650px',marginTop: '20px',fontFamily:'cursive', color: '#28666e', fontSize: '40px'}}>Data Science</h1>
          <section class="articles">
            <article>
              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/R.c06d5efa6616ec0eef214dfc12fb3765?rik=wjRp%2feJaMzHrnw&riu=http%3a%2f%2fwww.digitalvidya.com%2fwp-content%2fuploads%2f2017%2f04%2fIntroduction_to_data_science.jpg&ehk=1Q4LT1BwdZhINFPlKQlf0ESHZPBe61%2f38h50iwCUHSQ%3d&risl=&pid=ImgRaw&r=0" alt="" />
                </figure>
                <div class="article-body">
                  <h2>Introduction to Data Science</h2>
                  <p>
                    This course introduces the core principles of data science, covering data collection, cleaning, exploration, and basic statistical analysis. Students gain proficiency in visualizing data and understanding the entire data science workflow, making it an ideal starting point for those new to the field.</p>
                  <Button onClick={handleEnrollClick} type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
            <article>

              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/R.a39a91aeb99706850ba4e716de68a819?rik=A8RV8VfcrJEvjA&pid=ImgRaw&r=0" alt="" />
                </figure>
                <div class="article-body">
                  <h2>Machine Learning</h2>
                  <p>
                    In the Machine Learning course, students delve into algorithms that empower computers to learn from data. Supervised and unsupervised learning techniques are explored, including decision trees, neural networks, and model optimization. Practical projects ensure hands-on experience in creating and assessing machine learning models.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
            <article>

              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/OIP.sjGPWe-kuKYDLjmL701AQwHaDt?pid=ImgDet&rs=1" alt="" />
                </figure>
                <div class="article-body">
                  <h2>Statistical Analysis</h2>
                  <p>Focused on statistical techniques, this course equips learners with the tools to derive valuable insights from data. Topics span probability, hypothesis testing, regression analysis, and experimental design. By mastering these concepts, students enhance their ability to make informed decisions, validate hypotheses, and uncover meaningful trends in data.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
          </section>
      <h1 style={{marginLeft: '600px', fontFamily: 'cursive', color: '#28666e', fontSize: '40px'}}>Web development</h1>
          <section class="articles">
            <article>
              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/OIP.AJv_0-C9LWbBeCSEsyQ-iAHaDt?pid=ImgDet&w=710&h=355&rs=1" alt="" />
                </figure>
                <div class="article-body">
                  <h2>Full-Stack Web Development</h2>
                  <p>This comprehensive course equips students with skills in both front-end and back-end development. They learn to create interactive user interfaces using HTML, CSS, and JavaScript. Additionally, they delve into server-side programming using technologies like Node.js or Python, connecting databases to store and manage data. Upon completion, students are capable of building end-to-end web applications.</p>
                    <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
            <article>

              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/OIP.xVxVRyVeks1zwOpvI9aTjAHaEG?pid=ImgDet&w=1356&h=750&rs=1" alt="" />
                </figure>
                <div class="article-body">
                  <h2> Front-End Web Development</h2>
                  <p>Focused on user interface and experience, this course teaches students HTML, CSS, and JavaScript. They gain proficiency in designing responsive and visually appealing websites, ensuring compatibility across various devices and browsers. Interactive features are emphasized to create engaging user interactions, making graduates adept at crafting user-friendly websites.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
            <article>

              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/OIP.CrPrx3T4nJ4ABEXO8bUseQHaEo?pid=ImgDet&w=800&h=500&rs=1" alt="" />
                </figure>
                <div class="article-body">
                  <h2>Back-End Web Development</h2>
                  <p>Concentrating on the server-side, this course covers server programming and database management. Students work with server frameworks like Node.js or Django and explore database technologies such as SQL or NoSQL. They learn to build APIs, manage user authentication, handle data storage, and implement security measures, enabling them to develop robust and functional web applications.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
          </section>
      <h1 style={{marginLeft: '600px', fontFamily: 'cursive', color: '#28666e', fontSize: '40px'}}>Mobile Development</h1>
                <section class="articles">
            <article>
              <div class="article-wrapper">
                <figure>
                  <img src="https://blog.potenzaglobalsolutions.com/wp-content/uploads/2021/06/Why-is-custom-android-app-development-a-boon-for-your-business.jpg" alt="" />
                </figure>
                <div class="article-body">
                  <h2> Android App Development</h2>
                  <p>This course focuses on creating applications specifically for the Android platform. Students learn how to design user interfaces using XML and implement functionality using Java or Kotlin. Topics cover navigation, data storage, integrating device features like camera and location services, and deploying apps to the Google Play Store.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
            <article>

              <div class="article-wrapper">
                <figure>
                  <img src="https://th.bing.com/th/id/R.0584a9e186b1dba606f78d894efadbb0?rik=kT%2b5VK%2fgkynWQQ&pid=ImgRaw&r=0" alt="" />
                </figure>
                <div class="article-body">
                  <h2> iOS App Development</h2>
                  <p>Geared towards iOS devices like iPhones and iPads, this course teaches students Swift or Objective-C, the core programming languages for iOS app development. They gain expertise in crafting interfaces using Interface Builder and UIKit, and explore animations, touch interactions, and data management. The course prepares students to publish apps on the Apple App Store.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
            <article>

              <div class="article-wrapper">
                <figure>
                  <img src="https://www.getsmartcoders.com/blog/wp-content/uploads/2020/05/cross-platform-app-development.jpg" alt="" />
                </figure>
                <div class="article-body">
                  <h2>Cross-Platform Mobile Development</h2>
                  <p>This course introduces frameworks that enable the creation of mobile apps for multiple platforms using a single codebase. React Native allows building apps for both iOS and Android using JavaScript and React. Flutter employs Dart to develop visually appealing and high-performance apps. Students learn to balance code reuse with a native user experience on various platforms.</p>
                  <Button onClick={handleEnrollClick} htmlType='submit' type='primary'>Enroll</Button>
                </div>
              </div>
            </article>
          </section>
    </div>
  )
}

export default Course;

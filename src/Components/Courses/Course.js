import React from 'react';
import { MenuList } from '../../holpers/MenuList';
import { WebDevList } from '../../holpers/WebDevList';
import { MobDevList } from '../../holpers/MobileDevList';
import MenuItems from '../MenuItems/MenuItems';
import "../styles/Menu.css";
function Course() {
  return (
    <div className='Menu'>
      <h1 style={{marginLeft: '700px',marginTop: '20px',fontFamily:'cursive',}}>Data Science</h1>
      <div className='menuList'>
        {MenuList.map((menuItem, key)=>{ 
          return <MenuItems key={key} image={menuItem.image} name={menuItem.name} /> })}
      </div>
      <h1 style={{marginLeft: '650px', fontFamily: 'cursive',}}>Web development</h1>
      <div className='menuList'>
        {WebDevList.map((menuItem, key)=>{
          return <MenuItems  key={key} image={menuItem.image} name={menuItem.name} /> })}
      </div>
      <h1 style={{marginLeft: '650px', fontFamily: 'cursive',}}>Mobile Development</h1>
      <div className='menuList'>
        {MobDevList.map((menuItem, key)=>{
          return <MenuItems key={key} image={menuItem.image} name={menuItem.name} /> })}
      </div>
    </div>
  )
}

export default Course;

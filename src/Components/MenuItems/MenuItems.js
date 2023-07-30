import React from 'react';

function MenuItems({ image, name}) {
  return (
    <div className='menuItem'>
      <div style={{backgroundImage: `url(${image})`}}></div>
      <div style={{ fontFamily:'cursive', fontSize: '20px', padding: '30px', paddingLeft: '20%'}}>{name}</div>
    </div>
  )
}

export default MenuItems;

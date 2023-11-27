// Home.jsx

import React, { useState } from 'react';
import './Home.css';
import NavigationBar from './components/NavigationBar';

function Home() {
  const [notification, setNotification] = useState('');

  const handleButtonClick = (buttonName) => {
    setNotification(`You clicked ${buttonName} button!`);
  };

  return (
    
    <div className="home-container">
      <NavigationBar handleButtonClick={handleButtonClick}
      
      />
      {notification && (
        <div className="notification">
          <p>{notification}</p>
        </div>
      )}
    </div>
  );
}

export default Home;

import React, { useState } from 'react';

function Home() {
  const [notification, setNotification] = useState('');

  const handleButtonClick = (buttonName) => {
    setNotification(`You clicked ${buttonName} button!`);
  };

  return (
    <div className="home-container">
      {notification && <div className="notification-bar">{notification}</div>}

      <div className="button-container">
        <button className="big-button" onClick={() => handleButtonClick('Button 1')}>
          Button 1
        </button>
        <button className="big-button" onClick={() => handleButtonClick('Button 2')}>
          Button 2
        </button>
        <button className="big-button" onClick={() => handleButtonClick('Button 3')}>
          Button 3
        </button>
      </div>
    </div>
  );
}

export default Home;
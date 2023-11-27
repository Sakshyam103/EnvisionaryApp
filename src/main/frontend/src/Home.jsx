// Home.jsx

import React, { useState } from 'react';
import './Home.css';
import NavigationBar from './components/NavigationBar';

function Home() {
  const [notification, setNotification] = useState('');

  const handleMakePredictions = () => {
    // Logic for 'Make Prediction' button
    console.log("Make Prediction clicked");
  };

  const handleViewPredictions = () => {
    // Logic for 'View Prediction' button
    console.log("View Prediction clicked");
  };

  const handleViewStatistics = () => {
    // Logic for 'View Statistics' button
    console.log("View Statistics clicked");
  };

  const handleNotifications = () => {
    // Logic for 'Notifications' button
    console.log("Notifications clicked");
  };

  return (
    
    <div className="home-container">
      <NavigationBar 
      handleMakePredictions={handleMakePredictions}
      handleViewPredictions={handleViewPredictions}
      handleViewStatistics={handleViewStatistics}
      handleNotifications={handleNotifications}
      
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

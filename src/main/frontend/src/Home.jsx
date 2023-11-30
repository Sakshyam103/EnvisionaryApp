// Home.jsx

import React, { useState } from 'react';
import './Home.css';
import NavigationBar from './components/NavigationBar';
import {Link, useNavigate} from 'react-router-dom';
// import {Choice} from './components/Choice';


//const [choice, setChoice] = useState(null);

function Home() {
  const [notification, setNotification] = useState('');
  const navigate = useNavigate();

  const handleMakePredictions = () => {
    navigate('/Home/MakePredictions');
  };

  const handleViewPredictions = () => {
    // Logic for 'View Prediction' button
    console.log("View Prediction clicked");
    // <Choice choice = "ViewPredictions"/>
  };

  const handleViewStatistics = () => {
    // Logic for 'View Statistics' button
    console.log("View Statistics clicked");
    // <Choice choice = "ViewStatistics"/>

  };

  const handleNotifications = () => {
    // Logic for 'Notifications' button
    console.log("Notifications clicked");
    // <Choice choice = "ViewNotifications"/>
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

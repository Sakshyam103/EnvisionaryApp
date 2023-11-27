// NavigationBar.jsx

import React from 'react';
import { Link } from 'react-router-dom';
import './NavigationBar.css';
//import myLogo from './assets/myLogo.png';
function NavigationBar({ handleMakePredictions, handleViewPredictions, handleViewStatistics, handleNotifications }) {
  return (
    <nav className="navbar">
      <ul>
      {/* <div className="logo-container">
        <img src={myLogo} alt="logo" className="My logo" />
      </div> */}
      <li><button className="nav-button" onClick={handleMakePredictions}>Make Predictions</button></li>
        <li><button className="nav-button" onClick={handleViewPredictions}>View Predictions</button></li>
        <li><button className="nav-button" onClick={handleViewStatistics}>View Statistics</button></li>
        <li><button className="nav-button" onClick={handleNotifications}>Notifications</button></li>
        {/* Add more navigation links as needed */}
      </ul>
    </nav>
  );
}

export default NavigationBar;

// NavigationBar.jsx

import React from 'react';
import { Link } from 'react-router-dom';
import './NavigationBar.css';
//import myLogo from './assets/myLogo.png';
function NavigationBar({ handleButtonClick }) {
  return (
    <nav className="navbar">
      <ul>
      {/* <div className="logo-container">
        <img src={myLogo} alt="logo" className="My logo" />
      </div> */}
        <li><button className="nav-button" onClick={() => handleButtonClick('MakePrediction')}>Make Prediction</button></li>
        <li><button className="nav-button" onClick={() => handleButtonClick('ViewPrediction')}>View Prediction</button></li>
        <li><button className="nav-button" onClick={() => handleButtonClick('ViewStatistics')}>View Statistics</button></li>
        <li><button className="nav-button" onClick={() => handleButtonClick('Notifications')}>Notifications</button></li>
        {/* Add more navigation links as needed */}
      </ul>
    </nav>
  );
}

export default NavigationBar;

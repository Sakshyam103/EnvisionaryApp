// Home.jsx

import React, { useState } from 'react';
import './Home.css';
import NavigationBar from './components/NavigationBar';
import {Link, useNavigate} from 'react-router-dom';
function Home() {
  const [notification, setNotification] = useState('');
  const navigate = useNavigate();

  const handleMakePredictions = () => {
    navigate('/Home/MakePredictions');
  };

  const handleViewPredictions = () => {
    // Logic for 'View Prediction' button
    console.log("View Prediction clicked");
    fetch("http://localhost:8080/viewPrediction", {
      method:"GET",
      headers:{
        "Content-Type": "application/json",
       },
      }).then(res => {
        if(!res.ok){
          console.error('Request failed with status:' , res.status);
          return res.text();
        }
        return res.text();
      })
      .then(data => {
        console.log(data);
      }).catch(error=>{
        console.error('Error: ', error);
      })
     
     ;

  };

  const handleViewStatistics = () => {
    // Logic for 'View Statistics' button
    console.log("View Statistics clicked");
    fetch("http://localhost:8080/viewStatistics", {
      method:"GET",
      headers:{
        "Content-Type": "application/json",
       },
      }).then(res => {
        if(!res.ok){
          console.error('Request failed with status:' , res.status);
          return res.text();
        }
        return res.text();
      })
      .then(data => {
        console.log(data);
      }).catch(error=>{
        console.error('Error: ', error);
      })
     
     ;
  };

  const handleNotifications = () => {
    // Logic for 'Notifications' button
    console.log("Notifications clicked");
    fetch("http://localhost:8080/viewNotification", {
      method:"GET",
      headers:{
        "Content-Type": "application/json",
       },
      }).then(res => {
        if(!res.ok){
          console.error('Request failed with status:' , res.status);
          return res.text();
        }
        return res.text();
      })
      .then(data => {
        console.log(data);
      }).catch(error=>{
        console.error('Error: ', error);
      })
     
     ;
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

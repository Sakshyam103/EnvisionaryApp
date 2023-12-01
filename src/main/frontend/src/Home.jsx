// Home.jsx

import React, { useState } from 'react';
import './Home.css';
import NavigationBar from './components/NavigationBar';
import {Link, useNavigate} from 'react-router-dom';
// import {Choice} from './components/Choice';


//const [choice, setChoice] = useState(null);

function Home({user}) {
  const [notification, setNotification] = useState('');
  const navigate = useNavigate();

  const handleMakePredictions = () => {
    navigate('/Home/MakePredictions');
  };

  const handleViewPredictions = () => {
    // Ensure user.sub is available
    if (!user.sub) {
      console.error('User.sub is not available');
      return;
    }
  
    const data = { idString: user.sub };
  
    fetch("http://localhost:8080/view-predictions", {
      method: "POST",
      body: JSON.stringify(data),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) {
          console.error('Request failed with status:', res.status);
          return res.text();
        }
        return res.text();
      })
      .then((data) => {
        console.log(data);
        // Add any additional logic here based on the response from the backend
      })
      .catch((error) => {
        console.error('Error:', error);
      });
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

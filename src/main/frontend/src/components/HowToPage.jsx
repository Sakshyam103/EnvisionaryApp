import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';
import NavigationBar from "./NavigationBar.jsx";

const HowTo = () => {

    return(
<NavigationBar>
<div>
  <div className="container-fluid">
    <div className="row g-4 mb-3">
      <h1>Welcome to Envisionary!</h1>
      <h2>Predict the future today!</h2>
      <h4>This page will help you get to know the website a bit better!</h4>
    </div>
  </div>
  <div className="container-fluid mb-3" style={{borderStyle: 'double', backgroundColor: '#141414'}}>
    <h3>Make Predictions!</h3>
    <p style={{color: '#5538f5'}}>
    This page allows you to go and make predictions about anything! There
    are four preset categories:
    <p>(1) Football : Make a prediction about upcoming matches to see if you can guess the winning team!</p>
    <p>(2) Astronomy : Make a prediction about when a new object in space will be found!</p>
    <p>(3) Weather : Make a prediction about upcoming weather, guess the high or low of the day!</p>
    <p>(4) Entertainment : Make a prediction about when your favorite movies were released!</p>
    All these categories will automatically resolve for you on your account. And once they are resolved
    we will be sure to notify you! You can also make a custom prediction, which allows you to make
    any type of prediction you would like. Simply input an end date and some text, and it is all saved!
    </p>
    <p></p>
  </div>
  <div className="container-fluid g-4 mb-3" style={{borderStyle: 'double', backgroundColor: '#141414'}}>
    <h3>View Predictions!</h3>
    <p style={{color: '#5538f5'}}>
    Here you can view all of your prediction! Choose to view them by category and
    view resolved prediction!
    </p>
    <p></p>
  </div>
  <div className="container-fluid g-4 mb-3" style={{borderStyle: 'double', backgroundColor: '#141414'}}>
    <h3>View Statistics!</h3>
    <p style={{color: '#5538f5'}}>
    View all of your statistics to see how you are fairing in comparison. View not
    only individual stats but also the statistics of other users!
    </p>
    <p></p>
  </div>
  <div className="container-fluid g-4 mb-3" style={{borderStyle: 'double', backgroundColor: '#141414'}}>
    <h3>Resolve Predictions!</h3>
    <p style={{color: '#5538f5'}}>
    When making custom predictions, users must resolve these themselves!
    When you have custom predictions active, this page will allow you to mark
    if you have guessed correctly or incorrectly from this prediction!
    </p>
    <p></p>
  </div>
  <div className="container-fluid g-4 mb-3" style={{borderStyle: 'double', backgroundColor: '#141414'}}>
    <h3>Notifications</h3>
    <p style={{color: '#5538f5'}}>
    See all of the notifications about your category based predictions!
    </p>
    <p></p>
  </div>
  <div className="container-fluid g-4 mb-3" style={{borderStyle: 'double', backgroundColor: '#141414'}}>
    <h3>About Us</h3>
    <p style={{color: '#5538f5'}}>
    See all of the developers information who worked on this project!
    </p>
    <p></p>
  </div>
</div>
</NavigationBar>

);
}

export default HowTo;
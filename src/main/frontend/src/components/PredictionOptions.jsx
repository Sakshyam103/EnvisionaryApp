import React from 'react';
import Home from '../Home';
import {Link, useNavigate} from 'react-router-dom';

function PredictionOptions ({user}) {
  const navigate = useNavigate();

  const handleFootballPredictions = () => {
    // Handle football predictions logic here
    console.log('Handling Football Predictions');

  };

  const handleMoviesPredictions = () => {
    // Handle movies predictions logic here
    console.log('Handling Movies Predictions');
  };

  const handleAstronomyPredictions = () => {
    // Handle astronomy predictions logic here
    console.log('Handling Astronomy Predictions');
  };

  const handleWeatherPredictions = () => {
    // Handle weather predictions logic here
    console.log('Handling Weather Predictions');
  };

  const handleCustomPredictions = () => {
    // Handle custom predictions logic here
    console.log('Handling Custom Predictions');
    navigate('/Home/MakePredictions/Custom');
  };

  return (
    <>
   {/* <div>
    {
      Object.keys(user).length != 0 && (
        <div>
          <p>User Information:</p>
          <pre>{user.sub}</pre>
        </div>
      )
    }
   </div> */}
   <h1>What type of predictions would you like to make?</h1>
    <div style={{ display: 'flex', flexDirection: 'column', height: '50vh' }}>
      <button
        style={{
          flex: 1,
          fontSize: '2em',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'pink',
        }}
        onClick={handleFootballPredictions}
      >
        Football Matches
      </button>
      <button
        style={{
          flex: 1,
          fontSize: '2em',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'pink',
        }}
        //onClick={handleMoviesPredictions}
      >
        Movies
      </button>
      <button
        style={{
          flex: 1,
          fontSize: '2em',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'pink',
        }}
        // onClick={handleAstronomyPredictions}
      >
        Astronomy
      </button>
      <button
        style={{
          flex: 1,
          fontSize: '2em',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'pink',
        }}
        // onClick={handleWeatherPredictions}
      >
        Weather
      </button>
      <button
        style={{
          flex: 1,
          fontSize: '2em',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'pink',
        }}
        onClick={handleCustomPredictions}
      >
        Custom
      </button>
    </div>
    </>
  );
};

export default PredictionOptions;
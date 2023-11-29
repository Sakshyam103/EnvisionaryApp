import React from 'react';
import Home from '../Home';

const PredictionOptions = () => {
  return (
    <>
   
   <h1>What type of predictions would you like to make?</h1>
    <div style={{ display: 'flex', flexDirection: 'column', height: '80vh' }}>
      <button
        style={{
          flex: 1,
          fontSize: '2em',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'pink',
        }}
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
      >
        Custom
      </button>
    </div>
    </>
  );
};

export default PredictionOptions;
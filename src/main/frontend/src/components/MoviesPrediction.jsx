import React, { useState } from 'react';

function MoviesPrediction() {
  const [movie, setMovie] = useState('');
  const [releaseYear, setReleaseYear] = useState('');
  
  const years = Array.from({ length: 74 }, (_, index) => (2023 - index).toString());

  return (
    <div style={{ textAlign: 'center', margin: '20px' }}>
      <h1>I predict</h1>
      <input
        type="text"
        value={movie}
        onChange={(e) => setMovie(e.target.value)}
        placeholder="Enter a movie"
        style={{ padding: '10px', fontSize: '16px' }}
      />

      <h2>was released in</h2>
      <select
        value={releaseYear}
        onChange={(e) => setReleaseYear(e.target.value)}
        style={{ padding: '10px', fontSize: '16px' }}
      >
        <option value="" disabled>Select release year</option>
        {years.map((year, index) => (
          <option key={index} value={year}>
            {year}
          </option>
        ))}
      </select>

      <br />

      <button
        onClick={() => console.log(`Prediction: ${movie}, Release Year: ${releaseYear}`)}
        style={{
          padding: '10px',
          fontSize: '16px',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'none',
          cursor: 'pointer',
          marginTop: '20px',
        }}
      >
        Submit Prediction
      </button>
    </div>
  );
}

export default MoviesPrediction;

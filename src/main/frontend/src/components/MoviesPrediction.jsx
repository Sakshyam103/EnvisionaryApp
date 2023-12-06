import React, { useState } from 'react';

function MoviesPrediction() {
  const [movie, setMovie] = useState('');
  const [releaseYear, setReleaseYear] = useState('');
  
  const years = Array.from({ length: 74 }, (_, index) => (2023 - index).toString());

  function handleCallbackResponse(response) {
      fetch("http://localhost:8080/movies", {
        method:"POST",
        body: response,
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
        .then(response => {
          console.log(response);
        }).catch(error=>{
          console.error('Error: ', error);
        })

       ;

    }

  return (
    <div style={{ textAlign: 'center', margin: '20px' }}>
      <h2>I predict</h2>
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
        onClick={() => handleCallbackResponse(`{"Prediction": "${movie}", "ReleaseYear": ${releaseYear}}`)}
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

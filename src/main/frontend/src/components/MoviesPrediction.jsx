import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';


function MoviesPrediction() {
  const [movie, setMovie] = useState('');
  const [releaseYear, setReleaseYear] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
  
  const years = Array.from({ length: 74 }, (_, index) => (2023 - index).toString());


  function handleCallbackResponse(response) {
      if (movie && releaseYear) {
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
          if(response = "true"){
            alert('Correct! Great Job!');
            navigate('/Home');}
          else if (response = "false"){
            setError('Sorry, that was incorrect!');
            navigate('/Home');}
          else if (response = "Custom Prediction Saved"){
            alert('Due to an overflow of predictions, your prediction was saved in the custom category! Please see resolve predictions to resolve it!')
            navigate('/Home')}
          else{
          setError('Sorry, there was an error saving your prediction, please try again.');
          }
        }).catch(error=>{
          console.error('Error: ', error);
        })

       ;

          //
          // console.log("Prediction resolving");
          // fetch("http://localhost:8080/resolveMoviePrediction", {
          //     method:"GET",
          //     headers:{
          //         "Content-Type": "application/json",
          //     },
          // }).then(res => {
          //     if(!res.ok){
          //         console.error('Request failed with status:' , res.status);
          //         return res.text();
          //     }
          //     return res.json();
          //
          // })
          //     .then(data => {
          //         console.log(data);
          //     }).catch(error=>{
          //     console.error('Error: ', error);
          // })
          //
          // ;
          //
          // if(data == true){
          //     alert('Prediction made and you were so right! Go make other predictions now pookie<3');
          //     navigate('/Home');
          // }else{
          //     alert('Prediction made and unfortunately you were not right. Try again pookie<3');
          //     navigate('/Home');
          // }


      } else {
          setError('Please select movie and date both first!');
      }

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
        <br />

        {error && <div style={{ color: 'red', fontWeight: 'bold' }}>{error}</div>}

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

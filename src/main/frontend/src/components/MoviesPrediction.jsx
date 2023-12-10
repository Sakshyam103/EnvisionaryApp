import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';
import NavigationBar from "./NavigationBar.jsx";


function MoviesPrediction() {
  const [movie, setMovie] = useState('');
  const [releaseYear, setReleaseYear] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

  function isDisabled() {
    if(movie !== '' && releaseYear !== ''){
    return false;}
    else{return true;}
  }

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
          if(response === "true"){
            alert('Correct! Great Job!');
            navigate('/Home');}
          else if (response === "false"){
            setError('Sorry, that was incorrect!');
            navigate('/Home');}
          else if (response === "Custom Prediction Saved"){
            alert('Due to an overflow of predictions, your prediction was saved in the custom category! Please see resolve predictions to resolve it!')
            navigate('/Home')}
          else{
          setError('Sorry, there was an error saving your prediction, please try again.');
          }
        }).catch(error=>{
          console.error('Error: ', error);
        })

       ;
      } else {
          setError('Please select movie and date both first!');
      }

  }

  return (
  <NavigationBar>
    <div className="container-fluid d-flex flex-column align-items-center justify-content-center">
      <div className="row mb-3">
        <h1>I predict...</h1>
      </div>

      <div className="row mb-3 justify-content-center">
        <div className="col-12">
          <div className="form">
            <input
              className="form-control form-control-lg"
              id="movieTitle"
              placeholder="Movie Title"
              onChange={(e) => setMovie(e.target.value)}
            />
          </div>
        </div>
      </div>

      <div className="row mb-3">
        <div className="col-12">
          <h1>was released in...</h1>
        </div>
      </div>

      <div className="container-fluid d-flex justify-content-center align-items-center">
        <div className="row mb-3 d-flex justify-content-center align-items-center">
          <div className="col-10 mb-3">
            <select
              className="form-select form-select-lg"
              aria-label="Default select example"
              value={releaseYear}
              onChange={(e) => setReleaseYear(e.target.value)}
            >
              <option selected disabled>
                Choose Release Year
              </option>
              {years.map((year, index) => (
                <option key={index} value={year}>
                  {year}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      <div className="row mb-3">
        <div className="col-12 col-md-5">
          <button
            type="button"
            className="btn btn-large btn-custom-primary"
            disabled={isDisabled()}
            onClick={() => handleCallbackResponse(`{"Prediction": "${movie}", "ReleaseYear": ${releaseYear}}`)}
          >
            Submit
          </button>
        </div>
      </div>
    </div>
</NavigationBar>

  );
}

export default MoviesPrediction;

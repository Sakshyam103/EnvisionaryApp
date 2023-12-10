import React, { useState, useEffect } from 'react';
import {useNavigate} from "react-router-dom";
import NavigationBar from "./NavigationBar.jsx";

function FootballPrediction() {
  const [match, setMatch] = useState('');
  const [team, setTeam] = useState('');
  const [result, setResult] = useState('');
  const [buttonPressed, setButtonPressed] = useState(false);
  const [buttonPressed1, setButtonPressed1] = useState(false);
  const [matchOptions, setMatchOptions] = useState([]);
  const [teamOptions, setTeamOptions] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const [teamTwo, setTeamTwo] = useState('');
  const [footballMatchPrediction, setFootballMatchPrediction] = useState('');

  const resultOptions = ['Win', 'Draw', 'Loss'];

  useEffect(() => {
    // Fetch data when the component mounts
    fetch("http://localhost:8080/viewMatches", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
        .then(res => {
          if (!res.ok) {
            console.error('Request failed with status:', res.status);
            return res.text();
          }
          return res.text();
        })
        .then(data => {
          console.log(data);

            // Remove "[" and "]" from the beginning and end of the string
            const data1 = data.replace(/^\[|\]$/g, '');

            // Remove double quotes from the beginning and end of the string
            const data2 = data1.replace(/^"(.*)"$/g, '$1');

          const matches = data2.split('","');
          setMatchOptions(matches);
        })
        .catch(error => {
          console.error('Error: ', error);
        });
  }, []); // Empty dependency array ensures that the effect runs only once when the component mounts

  const submitPredictions = () => {
    if (team && result) {
      handleCallbackResponse()
      setButtonPressed1(true);

        // const footballData = {
        //     match,
        //     team,
        //     result,
        // };
        //
        // const footballMatchPrediction1 = JSON.stringify(footballData, null, 2);
        // console.log(footballMatchPrediction);
        // setFootballMatchPrediction(footballMatchPrediction1);

        // let data = {footballData};  //json
        // fetch("http://localhost:8080/sendMatches", {
        //     method:"POST",
        //     body:JSON.stringify(data),
        //     headers:{
        //         "Content-Type": "application/json",
        //     },
        // }).then(res => {
        //     if(!res.ok){
        //         console.error('Request failed with status:' , res.status);
        //         return res.text();
        //     }
        //     return res.text();
        // })
        //     .then(data => {
        //         console.log(data);
        //     }).catch(error=>{
        //     console.error('Error: ', error);
        // })
        //
        // ;
    } else {
      setError('Please select Team and Result before submitting predictions.');
    }


  };

  const handleSubmitMatch = () => {
    if (match) {
      setButtonPressed(true);
      setTeamOptions(match.split(' vs '));
    } else {
      setError('Please select Match first!');
    }

  };

  function handleCallbackResponse() {
            let response = `{"match": "${match}", "team": "${team}", "result": "${result}"}`
            fetch("http://localhost:8080/football", {
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
                if(response = true){
                                    alert('Prediction Made! Redirecting to home<3');
                                    navigate('/Home');}
                                    else{
                                    setError('Sorry there was an error saving your prediction!')}
              }).catch(error=>{
                console.error('Error: ', error);
              })

             ;

          }
  function isDisabledMatch() {
      if(match !== ''){
      return false;}
      else{return true;}
    }
  function isDisabled() {
    if(match !== '' && team !== '' && result !== ''){
    return false;}
    else{return true;}
  }

  return (
  <NavigationBar>
      <div style={{ textAlign: 'center' }}>
        <div className="container-fluid d-flex flex-column justify-content-center align-items-center">
                          <div className="row mt-4">
                            <h1>Choose your match</h1>
                          </div>
                          <div className="row mt-4">
                            <div className="col-8">
                              <select className="form-select form-select-lg" value={match} onChange={(e) => setMatch(e.target.value)}>
                                <option selected disabled>Select Match</option>
                                {matchOptions.map((matchOption, index) => (
                                                <option key={index} value={matchOption}>
                                                  {matchOption}
                                                </option>
                                            ))}
                              </select>
                            </div>
                            <div className="col">
                              <button
                                 type="button"
                                 className="btn btn-sm btn-custom-primary"
                                 disabled={isDisabledMatch()}
                                 onClick={handleSubmitMatch}>
                                    Submit Match
                                 </button>
                            </div>
                          </div>
                        </div>




        {buttonPressed && (
            <div className="container-fluid d-flex flex-column justify-content-center align-items-center">
            <div class="row mt-4">
                <h1>I predict that Team </h1>
            </div>
            <div className="row mt-4">
                <div className="col">
                <select className="form-select form-select-lg"
                                  value={team}
                                  onChange={(e) => setTeam(e.target.value)}
                              >
                                <option value="" disabled>Select Team</option>
                                {teamOptions.map((teamOption, index) => (
                                    <option key={index} value={teamOption}>
                                      {teamOption}
                                    </option>
                                ))}
                              </select>
                </div>
                <div className="row mt-4">
                <div className="col">
                                <h1>is going to</h1>
                                </div>
                </div>
                <div className="row mt-4">
                <div classname="col">
                                <select className="form-select form-select-lg"
                                                  value={result}
                                                  onChange={(e) => setResult(e.target.value)}
                                              >
                                                <option value="" disabled>Result</option>
                                                {resultOptions.map((resultOption, index) => (
                                                    <option key={index} value={resultOption}>
                                                      {resultOption}
                                                    </option>
                                                ))}
                                              </select>
                                </div>
                </div>


            </div>



              <br />
                <br/>

                {error && <div style={{ color: 'red', fontWeight: 'bold' }}>{error}</div>}

                <br/>

              <button
                  onClick={submitPredictions}
                  className="btn btn-sm btn-custom-primary"
                  disabled={isDisabled()}
                  >Submit Predictions
              </button>
            </div>
        )}
      </div>
</NavigationBar>
  );
}

export default FootballPrediction;

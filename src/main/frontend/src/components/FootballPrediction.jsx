import React, { useState, useEffect } from 'react';
import {useNavigate} from "react-router-dom";

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

  return (
      <div style={{ textAlign: 'center' }}>
        {/* Display fetched data */}
        {/*<p>{matchX}</p>*/}

        <div style={{ margin: '10px' }}>
          <h2>Choose Match </h2>
          <select
              value={match}
              onChange={(e) => setMatch(e.target.value)}
              style={{ padding: '10px', fontSize: '16px' }}
          >
            <option value="" disabled>Select Match</option>
            {matchOptions.map((matchOption, index) => (
                <option key={index} value={matchOption}>
                  {matchOption}
                </option>
            ))}
          </select>
          <button
              onClick={handleSubmitMatch}
              style={{
                padding: '10px',
                fontSize: '16px',
                backgroundColor: '#9F2B68',
                color: 'white',
                border: 'none',
                cursor: 'pointer',
              }}
          >
            Submit Match
          </button>
        </div>



        {buttonPressed && (
            <div style={{ margin: '10px' }}>
              <h2>I predict that Team </h2>
              <select
                  value={team}
                  onChange={(e) => setTeam(e.target.value)}
                  style={{ padding: '10px', fontSize: '16px' }}
              >
                <option value="" disabled>Select Team</option>
                {teamOptions.map((teamOption, index) => (
                    <option key={index} value={teamOption}>
                      {teamOption}
                    </option>
                ))}
              </select>
              <label> is going to </label>
              <select
                  value={result}
                  onChange={(e) => setResult(e.target.value)}
                  style={{ padding: '10px', fontSize: '16px' }}
              >
                <option value="" disabled>Result</option>
                {resultOptions.map((resultOption, index) => (
                    <option key={index} value={resultOption}>
                      {resultOption}
                    </option>
                ))}
              </select>

              <br />
                <br/>

                {error && <div style={{ color: 'red', fontWeight: 'bold' }}>{error}</div>}

                <br/>

              <button
                  onClick={submitPredictions}
                  style={{
                    padding: '10px',
                    fontSize: '16px',
                    backgroundColor: '#9F2B68',
                    color: 'white',
                    border: 'none',
                    cursor: 'pointer',
                  }}
              >
                Submit Predictions
              </button>
            </div>
        )}
      </div>
  );
}

export default FootballPrediction;

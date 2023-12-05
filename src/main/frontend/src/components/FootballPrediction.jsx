import React, { useState, useEffect } from 'react';

function FootballPrediction() {
  const [match, setMatch] = useState('');
  const [team, setTeam] = useState('');
  const [result, setResult] = useState('');
  const [buttonPressed, setButtonPressed] = useState(false);
  const [buttonPressed1, setButtonPressed1] = useState(false);
  const [matchX, setMatchX] = useState('');

  const matchOptions = ['Match 1', 'Match 2', 'Match 3'];
  const teamOptions = ['Team A', 'Team B', 'Team C'];
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
          setMatchX(data);
        })
        .catch(error => {
          console.error('Error: ', error);
        });
  }, []); // Empty dependency array ensures that the effect runs only once when the component mounts

  const submitPredictions = () => {
    if (team && result) {
      setButtonPressed1(true);
    } else {
      alert('Please select Team and Result before submitting predictions.');
    }

    const footballData = {
      match,
      team,
      result,
    };

    const footballMatchPrediction = JSON.stringify(footballData, null, 2);
    console.log(footballMatchPrediction);
  };

  const handleSubmitMatch = () => {
    if (match) {
      setButtonPressed(true);
    } else {
      alert('Please select Match first!');
    }
  };

  return (
      <div style={{ textAlign: 'center' }}>
        {/* Display fetched data */}
        <p>{matchX}</p>

        <div style={{ margin: '10px' }}>
          <label>Choose Match </label>
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
              <label>I predict that Team </label>
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
              <label>Is going to </label>
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

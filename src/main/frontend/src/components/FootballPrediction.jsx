import React, { useState } from 'react';

function FootballPrediction() {
  const [match, setMatch] = useState('');
  const [team, setTeam] = useState('');
  const [result, setResult] = useState('');
  const[buttonPressed, setButtonPressed] = useState(false);
  const[buttonPressed1, setButtonPressed1] = useState(false);

  const matchOptions = ['Match 1', 'Match 2', 'Match 3'];
  const teamOptions = ['Team A', 'Team B', 'Team C'];
  const resultOptions = ['Win', 'Draw', 'Loss'];

  const submitPredictions = () => {
    if (team && result) {
        setButtonPressed1(true);
      } else {
        alert('Please select Team and Result before submitting predictions.');
      }
    // Handle the selected predictions, you can log them or send them to the backend
    console.log(`Selected Predictions: Match - ${match}, Team - ${team}, Result - ${result}`);
  };
  
  const handleSubmitMatch = () => {
    // {match &&(
    // setButtonPressed(true)
    // )}
    if (match) {
        setButtonPressed(true);
      } else {
        alert('Please select Match first!');
      }

  }

  return (
    <div style={{ textAlign: 'center' }}>
      {/* <h1>I want to predict that in the match </h1> */}

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

      {buttonPressed &&(
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
        <label>Is going to  </label>
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

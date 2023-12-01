import React, { useState } from 'react';

function CustomPrediction() {
  const [prediction, setPrediction] = useState('');
  const [selectedDate, setSelectedDate] = useState('');

  const submitPrediction = () => {
    const customData = {
      prediction,
      date: selectedDate,
    }

    const customPrediction = JSON.stringify(customData, null, 2);
    console.log("Generated JSON", customPrediction);
    //console.log(`Prediction: ${prediction}, Date: ${selectedDate}`);
  };

  return (
    <div style={{ textAlign: 'center' }}>
      <h1>I predict</h1>
      <input
        type="text"
        placeholder="Enter your prediction"
        value={prediction}
        onChange={(e) => setPrediction(e.target.value)}
        style={{ padding: '10px', margin: '10px', fontSize: '16px' }}
      />
      <br />
      <label htmlFor="datePicker">will happen on</label>
      <input
        type="date"
        id="datePicker"
        value={selectedDate}
        onChange={(e) => setSelectedDate(e.target.value)}
        style={{ padding: '10px', margin: '10px', fontSize: '16px' }}
      />
      <br />
      <button
        onClick={submitPrediction}
        style={{
          padding: '10px',
          fontSize: '16px',
          backgroundColor: '#9F2B68',
          color: 'white',
          border: 'none',
          cursor: 'pointer',
        }}
      >
        Submit Prediction
      </button>
    </div>
  );
}

export default CustomPrediction;

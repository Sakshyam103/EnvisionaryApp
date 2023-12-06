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
  function handleCallbackResponse(response) {
        fetch("http://localhost:8080/custom", {
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
        onClick={() => handleCallbackResponse(`{"Prediction": "${prediction}", "ResolveDate": "${selectedDate}"}`)}
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

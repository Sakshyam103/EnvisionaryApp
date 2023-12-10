import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";

function CustomPrediction() {
  const [prediction, setPrediction] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const[error, setError] = useState('');
    const navigate = useNavigate();
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate()+1);
    const tomorrowX = tomorrow.toISOString().split('T')[0];

  const submitPrediction = () => {

      if (prediction && selectedDate) {
      const customData = {
          prediction,
          date: selectedDate,
      }
      handleCallbackResponse();}
      else {
          setError('Please enter prediction first!')
      }
  }

  function handleCallbackResponse() {
        let response = `{"Prediction": "${prediction}", "ResolveDate": "${selectedDate}"}`
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
            if(response = true){
                                alert('Prediction Made! Redirecting to home<3');
                                navigate('/Home');}
                                else{
                                setError('Please select the options first')}
          }).catch(error=>{
            console.error('Error: ', error);
          })

         ;

      }

  return (
    <div style={{ textAlign: 'center' }}>
      <h2>I predict</h2>
      <input
        type="text"
        placeholder="Enter your prediction"
        value={prediction}
        onChange={(e) => setPrediction(e.target.value)}
        style={{ padding: '10px', margin: '10px', fontSize: '16px' }}
      />
      <br />
      <label htmlFor="datePicker"> on </label>
      <input
        type="date"
        id="datePicker"
        value={selectedDate}
        onChange={(e) => setSelectedDate(e.target.value)}
        style={{ padding: '10px', margin: '10px', fontSize: '16px' }}
        min={tomorrowX}
      />
      <br />
        {error && <div style={{ color: 'red', fontWeight: 'bold' }}>{error}</div>}
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

import React, { useState } from 'react';
//import {useNavigate} from "react-router-dom";

function WeatherPrediction() {
  const [temperatureType, setTemperatureType] = useState('');
  const [temperatureValue, setTemperatureValue] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [error, setError] = useState('');
  //const navigate = useNavigate();

  const temperatureTypes = ['High', 'Low'];

  const handleInputChange = (e) => {
    const value = e.target.value;

    // Validate if the input is a valid number or a valid negative number
    if (/^-?\d+$/.test(value) || value === '' || (/^-?\d*\.\d+$/.test(value) && value.indexOf('.') !== value.length - 1)) {
      setTemperatureValue(value);
      setError('');
    } else {
      setError('Please enter a valid number.');
    }
  };

  const handleDateChange = (e) => {
    setSelectedDate(e.target.value);
  };

  const submitPrediction = () => {
    if (temperatureType && temperatureValue !== '' && selectedDate !== '' && error === '') {
      console.log(`Prediction: ${temperatureType} of ${temperatureValue} degrees Fahrenheit on ${selectedDate}`);
      handleCallbackResponse()
    } else {
      setError('Please fill in all fields with valid values.');
    }
  };

  function handleCallbackResponse() {
          let response = `{"temperatureType": "${temperatureType}", "temperatureValue": ${temperatureValue}, "ResolveDate": "${selectedDate}"}`
          fetch("http://localhost:8080/weather", {
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
      <h1>I predict that the temperature will be</h1>

      <select
        value={temperatureType}
        onChange={(e) => setTemperatureType(e.target.value)}
        style={{ padding: '10px', fontSize: '16px' }}
      >
        <option value="" disabled>Select temperature type</option>
        {temperatureTypes.map((type, index) => (
          <option key={index} value={type}>
            {type}
          </option>
        ))}
      </select>

      <span>of</span>

      <input
        type="number"
        value={temperatureValue}
        onChange={handleInputChange}
        placeholder="Enter temperature"
        style={{ padding: '10px', fontSize: '16px' }}
      />

      <span>degrees Fahrenheit</span>

      <br />

      <label>Select date:</label>
      <input
        type="date"
        value={selectedDate}
        onChange={handleDateChange}
        style={{ padding: '10px', fontSize: '16px' }}
      />

      {error && <div style={{ color: 'red' }}>{error}</div>}

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
          marginTop: '20px',
        }}
      >
        Submit Prediction
      </button>
    </div>
  );
}

export default WeatherPrediction;

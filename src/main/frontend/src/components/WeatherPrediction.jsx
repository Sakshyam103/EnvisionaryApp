import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";
import NavigationBar from "./NavigationBar.jsx";

function WeatherPrediction() {
  const [temperatureType, setTemperatureType] = useState('');
  const [temperatureValue, setTemperatureValue] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const tomorrow = new Date();
      tomorrow.setDate(tomorrow.getDate()+1);
      const tomorrowX = tomorrow.toISOString().split('T')[0];

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
  function isDisabled(){
  if(temperatureValue !== '' && temperatureType !== '' && selectedDate !== ''){return false;}
  else{return true;}
  }

  return (
  <NavigationBar>
    <div className="container-fluid d-flex flex-column justify-content-center align-items-center">
        <div className="row mt-4">
            <div className="col">
                <h1>I predict that there will be a</h1>
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <select className="form-select form-select-lg"
                        value={temperatureType}
                        onChange={(e) => setTemperatureType(e.target.value)}>
                        <option selected disabled>Select temperature type</option>
                        {temperatureTypes.map((type, index) => (
                          <option key={index} value={type}>
                            {type}
                          </option>
                        ))}
                      </select>
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <h1>a</h1>
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <input className="form-control"
                        type="number"
                        value={temperatureValue}
                        onChange={handleInputChange}
                        placeholder="Enter temperature"
                        style={{ padding: '10px', fontSize: '16px' }}
                      />
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <h1>degrees Fahrenheit</h1>
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <input className="form-control"
                        type="date"
                        min={tomorrowX}
                        value={selectedDate}
                        onChange={handleDateChange}
                        style={{ padding: '10px', fontSize: '16px' }}
                      />
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <button
                        className="btn btn-sm btn-custom-primary"
                        onClick={submitPrediction}
                                          disabled={isDisabled()}>Submit Prediction
                      </button>
            </div>
        </div>
    </div>

    </NavigationBar>
  );
}

export default WeatherPrediction;

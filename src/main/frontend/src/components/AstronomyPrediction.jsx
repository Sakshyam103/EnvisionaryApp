// PredictionPage.jsx

import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";

const AstronomyPrediction = () => {
    const [selectedValue, setSelectedValue] = useState('1');
    const [selectedDate, setSelectedDate] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate()+1);
    const tomorrowX = tomorrow.toISOString().split('T')[0];

    const predict = () => {

        if(selectedValue && selectedDate){
            handleCallbackResponse();
            alert('Prediction Made! Redirecting to home<3');
            navigate('/Home');
        }else{
            setError('Please select the options first')
        }
    };

    function handleCallbackResponse() {
                let response = `{"value": "${selectedValue}", "resolveDate": "${selectedDate}"}`
                fetch("http://localhost:8080/space", {
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
        <div>
            <h2>I predict that the number of </h2>
            {/*<label htmlFor="celestialBodySelect">Select Celestial Body:</label>*/}
            <select id="celestialBodySelect"
                    value={selectedValue}
                    onChange={(e) => setSelectedValue(e.target.value)}
                    style={
                            {width: '200px', height: '30px', fontSize: '90%'}}
            >
                <option value="planet">Planet</option>
                <option value="dwarfPlanet">Dwarf Planet</option>
                <option value="asteroid">Asteroid</option>
                <option value="comet">Comet</option>
                <option value="moonsPlanet">Moons Planet</option>
                <option value="moonsDwarfPlanet">Moons Dwarf Planet</option>
                <option value="moonsAsteroid">Moons Asteroid</option>
                {/* Add more celestial bodies as needed */}
            </select>

            <h2> will change by </h2>
            <input type="date" id="dateInput" value={selectedDate} onChange={(e) => setSelectedDate(e.target.value)}
                   style={
                       {width: '200px', height: '30px', fontSize: '90%'}}
                   min={tomorrowX}
            />

            <br />
            <br />
            {error && <div style={{ color: 'red' }}>{error}</div>}
            <br />

            <button onClick={predict}>Predict</button>
        </div>
    );
};

export default AstronomyPrediction;

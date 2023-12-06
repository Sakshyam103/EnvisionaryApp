// PredictionPage.jsx

import React, { useState } from 'react';

const AstronomyPrediction = () => {
    const [selectedNumber, setSelectedNumber] = useState('1');
    const [selectedDate, setSelectedDate] = useState('');

    const predict = () => {
        handleCallbackResponse()
        // Display the prediction
        return(
            <div>
                Prediction made!
            </div>
        )
        // alert(`I predict that there will be a change in the number of ${selectedNumber} by ${selectedDate}.`);
    };

    function handleCallbackResponse() {
                let response = `{"planet": "${selectedNumber}", "resolveDate": "${selectedDate}"}`
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
            <label>I predict that the number of </label>
            {/*<label htmlFor="celestialBodySelect">Select Celestial Body:</label>*/}
            <select id="celestialBodySelect" value={selectedNumber} onChange={(e) => setSelectedNumber(e.target.value)}>
                <option value="planet">Planet</option>
                <option value="dwarfPlanet">Dwarf Planet</option>
                <option value="asteroid">Asteroid</option>
                <option value="comet">Comet</option>
                <option value="moonsPlanet">Moons Planet</option>
                <option value="moonsDwarfPlanet">Moons Dwarf Planet</option>
                <option value="moonsAsteroid">Moons Asteroid</option>
                {/* Add more celestial bodies as needed */}
            </select>

            <label> will change by </label>
            <input type="date" id="dateInput" value={selectedDate} onChange={(e) => setSelectedDate(e.target.value)} />

            <br />

            <button onClick={predict}>Predict</button>
        </div>
    );
};

export default AstronomyPrediction;

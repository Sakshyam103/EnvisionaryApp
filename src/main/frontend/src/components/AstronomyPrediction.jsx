// PredictionPage.jsx

import React, { useState } from 'react';

const AstronomyPrediction = () => {
    const [selectedNumber, setSelectedNumber] = useState('1');
    const [selectedDate, setSelectedDate] = useState('');

    const predict = () => {
        // Display the prediction
        return(
            <div>
                Prediction made!
            </div>
        )
        // alert(`I predict that there will be a change in the number of ${selectedNumber} by ${selectedDate}.`);
    };

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

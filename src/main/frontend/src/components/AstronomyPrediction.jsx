// PredictionPage.jsx

import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";
import NavigationBar from "./NavigationBar.jsx";

const AstronomyPrediction = () => {
    const [selectedValue, setSelectedValue] = useState('');
    const [selectedDate, setSelectedDate] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate()+1);
    const tomorrowX = tomorrow.toISOString().split('T')[0];

    const predict = () => {

        if(selectedValue && selectedDate){
            handleCallbackResponse();

        }else{
            setError('Please select the options first')
        }
    };

    function isDisabled() {
    if(selectedValue !== '0' && selectedDate !== ''){
    return false;}
    else{return true;}
    }



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
   <NavigationBar>
   <div className="container-fluid d-flex flex-column justify-content-center align-items-center">
   <div>
           <div className="row mt-4">
           <h1>I predict that the number of </h1>
           </div>
           <div className="row mt-4 justify-content-center">
           <select id="celestialBodySelect" className="form-select form-select-lg"
                                  value={selectedValue}
                                  onChange={(e) => setSelectedValue(e.target.value)}
                                  style={
                                          {width: '200px', height: '30px', fontSize: '90%'}}
                          >
                              <option selected disabled>Select Planetary Object</option>
                              <option value="planet">Planet</option>
                              <option value="dwarfPlanet">Dwarf Planet</option>
                              <option value="asteroid">Asteroid</option>
                              <option value="comet">Comet</option>
                              <option value="moonsPlanet">Moons Planet</option>
                              <option value="moonsDwarfPlanet">Moons Dwarf Planet</option>
                              <option value="moonsAsteroid">Moons Asteroid</option>
                          </select>
           </div>
           <div className="row mt-4">
           <h1> will change by </h1>
           </div>
           <div className="row mt-4 justify-content-center">
           <input type="date" id="dateInput" className="form-control"
           value={selectedDate} onChange={(e) => setSelectedDate(e.target.value)}style={  {width: '200px', fontSize: '90%'}} min={tomorrowX}/>
           </div>
           <br /><br />{error && <div style={{ color: 'red' }}>{error}</div>}<br />
           <div className="justify-content-center">
            <button className="btn btn-sm btn-custom-primary" disabled={isDisabled()} onClick={predict}>Predict</button>
           </div>

           </div>
   </div>

        </NavigationBar>
    );
};

export default AstronomyPrediction;

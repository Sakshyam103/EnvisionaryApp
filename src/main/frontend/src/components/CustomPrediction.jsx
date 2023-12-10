import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";
import NavigationBar from "./NavigationBar.jsx";

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
  function isDisabled(){
  if(prediction !== '' && selectedDate !== ''){return false;}
  else{return true;}
  }

  return (
  <NavigationBar>
    <div className="container-fluid d-flex flex-column justify-content-center align-items-center">
        <div className="row mt-4">
            <div className="col">
                <h1>I predict</h1>
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                <input className="form-control form-control-lg"
                        type="text"
                        placeholder="Enter your prediction"
                        value={prediction}
                        onChange={(e) => setPrediction(e.target.value)}
                        style={{ padding: '10px', margin: '10px', fontSize: '16px' }}
                      />
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
                 <input  className="form=control"
                         type="date"
                         id="datePicker"
                         value={selectedDate}
                         onChange={(e) => setSelectedDate(e.target.value)}
                         style={{ padding: '10px', margin: '10px', fontSize: '16px' }}
                         min={tomorrowX}
                       />
            </div>
        </div>
        <div className="row mt-4">
            <div className="col">
            <button
            className="btn btn-sm btn-custom-primary"
            disabled={isDisabled()}
                    onClick={submitPrediction}
                  >Submit Prediction
                  </button>
            </div>
        </div>

    </div>
</NavigationBar>
  );
}

export default CustomPrediction;

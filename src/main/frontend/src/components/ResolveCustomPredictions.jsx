import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";

const ResolveThePredictions = ({ data }) => {
  // Your array of strings
  const optionsArray = data;
  const navigate = useNavigate();

  // State to keep track of the selected option
  const [selectedOption, setSelectedOption] = useState('');

  function handleResolve(response) {
    fetch("http://localhost:8080/customResolve", {
      method: "POST",
      body: response,
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) {
          console.error('Request failed with status:', res.status);
          return res.text();
        }
        return res.text();
      })
      .then((response) => {
        console.log(response);
        if (response === 'true') {
          alert('Custom Prediction Resolved!');
          window.location.reload();
        } else {
          setError('Please select the options first');
        }
      })
      .catch((error) => {
        console.error('Error: ', error);
      });
  }

  return (
    <div>
      <label htmlFor="selectBox">Select an option:</label>
      <select
        id="selectBox"
        className="form-control"
        value={selectedOption}
        onChange={(e) => setSelectedOption(e.target.value)}
      >
        <option value="" disabled>Select an option</option>
        {/* Map over the array and generate options */}
        {optionsArray.map((option, index) => (
          <option key={index} value={option}>
            {option}
          </option>
        ))}
      </select>

      {/* Display selected option */}
      {selectedOption && (
        <div>
          <h5>You have selected to resolve: {selectedOption}</h5>
          <div className="container-fluid">
            {/* Use onClick instead of onclick */}
            <button
              type="button"
              className="btn btn-success m-2 p-2"
              onClick={() =>
                handleResolve(
                  `{"predictionContent": "${selectedOption}", "resolution": true}`
                )
              }
            >
              I was Right <i className="fas fa-smile-o"/>
            </button>
            <button
              type="button"
              className="btn btn-warning m-2 p-2"
              onClick={() =>
                handleResolve(
                  `{"predictionContent": "${selectedOption}", "resolution": false}`
                )
              }
            >
              I was Wrong <i className="fas fa-frown-o"/>
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ResolveThePredictions;

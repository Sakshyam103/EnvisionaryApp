import React from "react";
import './custom.css';
import { useNavigate } from 'react-router-dom';

const ResolveCustomPredictionsTable = ({ data }) => {

    const navigate = useNavigate();

    if (!Array.isArray(data) || data.length < 1) {
        return <p>Insufficient data to display the table.</p>;
    }
    if (!data || data.length === 0) {
        return <p>No data available.</p>;
    }
    const formatValue = (value) => {
                if (typeof value === 'boolean') {
                  return value.toString();
                } else if (typeof value === 'string' && value.includes('America/New_York')) {
                  return value.slice(0, 10);
                } else if( value === null){
                return 'Standard';}
                else {
                  return value;
                }
              };
    const formatTitle = (value) => {
        if (value === 'predictionContent'){
        return 'Prediction';}
        else if (value === 'predictionEndDate'){
        return 'Ending Date';}
        else if (value === 'predictionMadeDate'){
        return 'Created On';}
        else{
        return value;}
    };

    const fieldsToDisplay = ['predictionContent', 'predictionMadeDate', 'predictionEndDate'];

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
              navigate('/Home')
            } else {
              setError('Please select the options first');
            }
          })
          .catch((error) => {
            console.error('Error: ', error);
          });
      };

    return (
        <div className="container mt-5">
              <table className="table table-dark table-hover text-center">
                <thead >
                  <tr>
                    {fieldsToDisplay.map((field) => (
                      <th key={field}>{formatTitle(field)}</th>
                    ))}
                    <th>Resolve the Prediction!</th>
                  </tr>
                </thead>
                <tbody>
                  {data.map((item, index) => (
                    <tr key={index}>
                      {fieldsToDisplay.map((field) => (
                        <td className="custom-center-align" key={field}>{formatValue(item[field])}</td>
                      ))}
                        <td>
                        <button type="button" className="btn btn-success custom-btn-spacing-right"
                        onClick={() =>handleResolve(`{"predictionContent": "${item.predictionContent}", "resolution": true}`)}>I was Right!</button>
                        <button type="button" className="btn btn-danger custom-btn-spacing-left"
                        onClick={() =>handleResolve(`{"predictionContent": "${item.predictionContent}", "resolution": true}`)}>I was Wrong!</button>
                        </td>
                    </tr>
                  ))}
                  <tr></tr>
                </tbody>
              </table>
            </div>
    );
};


export default ResolveCustomPredictionsTable;

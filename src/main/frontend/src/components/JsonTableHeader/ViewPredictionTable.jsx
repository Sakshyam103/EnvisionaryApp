import React from "react";
import './custom.css';
import { useNavigate } from 'react-router-dom';

const ViewPredictionsTable = ({ data }) => {

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


    return (
        <div className="container mt-5">
              <table className="table table-dark table-hover text-center">
                <thead >
                  <tr>
                    {fieldsToDisplay.map((field) => (
                      <th key={field}>{formatTitle(field)}</th>
                    ))}
                  </tr>
                </thead>
                <tbody>
                  {data.map((item, index) => (
                    <tr key={index}>
                      {fieldsToDisplay.map((field) => (
                        <td className="custom-center-align" key={field}>{formatValue(item[field])}</td>
                      ))}
                    </tr>
                  ))}
                  <tr></tr>
                </tbody>
              </table>
            </div>
    );
};


export default ViewPredictionsTable;

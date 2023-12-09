import React, { useState } from 'react';
import NavigationBar from "./NavigationBar.jsx";

const ButtonPanel = ({ onButtonClick }) => {

    const [predictionData, setPredictionData] = useState([]);


    // Define handlers for each button
    const handleViewMovie = async () => {
        try {
            const res = await fetch("http://localhost:8080/viewEntertainmentPredictions", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!res.ok) {
                console.error('Request failed with status:', res.status);
                const errorText = await res.text();
                throw new Error(errorText);
            }

            const data = await res.json();
            setPredictionData(data)
            console.log(data);
            onButtonClick(predictionData);
        } catch (error) {
            console.error('Error:', error);
        }
    };
    const handleViewFootball = async () => {
            try {
                const res = await fetch("http://localhost:8080/viewFootballPredictions", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!res.ok) {
                    console.error('Request failed with status:', res.status);
                    const errorText = await res.text();
                    throw new Error(errorText);
                }

                const data = await res.json();
                setPredictionData(data)
                console.log(data);
                onButtonClick(predictionData);
            } catch (error) {
                console.error('Error:', error);
            }
    };
    const handleViewWeather = async () => {
            try {
                const res = await fetch("http://localhost:8080/viewWeatherPredictions", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!res.ok) {
                    console.error('Request failed with status:', res.status);
                    const errorText = await res.text();
                    throw new Error(errorText);
                }

                const data = await res.json();
                setPredictionData(data)
                console.log(data);
                onButtonClick(predictionData);
            } catch (error) {
                console.error('Error:', error);
            }
    };

    const handleViewSpace = async () => {
            try {
                const res = await fetch("http://localhost:8080/viewSpacePredictions", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!res.ok) {
                    console.error('Request failed with status:', res.status);
                    const errorText = await res.text();
                    throw new Error(errorText);
                }

                const data = await res.json();
                setPredictionData(data)
                console.log(data);
                onButtonClick(predictionData);
            } catch (error) {
                console.error('Error:', error);
            }
    };

    const handleViewCustom = async () => {
            try {
                const res = await fetch("http://localhost:8080/viewCustomPredictions", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!res.ok) {
                    console.error('Request failed with status:', res.status);
                    const errorText = await res.text();
                    throw new Error(errorText);
                }

                const data = await res.json();
                setPredictionData(data)
                console.log(data);
                onButtonClick(predictionData);
            } catch (error) {
                console.error('Error:', error);
            }
        };

     const handleViewResolved = async () => {
                try {
                    const res = await fetch("http://localhost:8080/viewResolvedPrediction", {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                        },
                    });

                    if (!res.ok) {
                        console.error('Request failed with status:', res.status);
                        const errorText = await res.text();
                        throw new Error(errorText);
                    }

                    const data = await res.json();
                    setPredictionData(data)
                    console.log(data);
                    onButtonClick(predictionData);
                } catch (error) {
                    console.error('Error:', error);
                }
            };

    return (
        <div style={{display: 'flex', flex:'left', flexDirection: 'column' }}>
            {/* Render five buttons with their respective click handlers */}
            <button style={{marginBottom:'5px', padding:'8px', width:'15%', marginTop:'40px'}} onClick={handleViewFootball}>View Football Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleViewMovie}>View Movies Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleViewSpace}>View Astronomy Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleViewWeather}>View Weather Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleViewCustom}>View Custom Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleViewResolved}>View Resolved Predictions</button>
        </div>
    );
};

const JsonTable = ({ data }) => {
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

    return (
        <div style={{ textAlign: 'center' }}>
            <table style={{ borderCollapse: 'collapse', width: '100%' }}>
                <thead>
                <tr>
                    {Object.keys(data[0]).map((header) => (
                        <th key={header} style={tableHeaderStyle}>
                            {header}
                        </th>
                    ))}
                </tr>
                </thead>
                <tbody>
                  {data.map((row, index) => (
                   <tr key={index}>
                     {Object.values(row).map((value, i) => (
                         <td key={i}>{formatValue(value)}</td>
                         ))}
                         </tr>
                         ))}
                </tbody>
            </table>
        </div>
    );
};

// CSS styles for the table
const tableHeaderStyle = {
    border: '1px solid #ddd',
    padding: '8px',
    background: 'black',
};

const tableRowStyle = {
    border: '1px solid #ddd',
};

const tableCellStyle = {
    border: '1px solid #ddd',
    padding: '8px',
};

const MainContent = ({ content }) => (
    <div style={{ flex:'right', marginLeft:'300px', marginTop:'-14%' }}>
        {<JsonTable data = {content}/>}
        {/*<p>{content}</p>*/}
    </div>
);

const ViewPredictions = () => {
    const [displayedContent, setDisplayedContent] = useState('');

    const handleButtonClick = (content) => {
        setDisplayedContent(content);
    };

    return (

            <div style={{ height: '100vh', overflowY: 'auto' }}>
                <ButtonPanel onButtonClick={handleButtonClick} />
                <MainContent content={displayedContent} />
            </div>

    );
};

export default ViewPredictions;

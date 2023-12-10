import React, { useState } from 'react';
import NavigationBar from "./NavigationBar.jsx";
import ViewPredictionsTable from './JsonTableHeader/ViewPredictionTable.jsx';
import ViewResolvedPredictions from './JsonTableHeader/ViewResolvedPredictions.jsx';

const ButtonPanel = ({ onButtonClick }) => {

    const [predictionData, setPredictionData] = useState([]);
    const [resolved, setResolved] = useState(false);

    const handleViewMovie = async () => {
        setResolved(false);
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
    setResolved(false);
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
    setResolved(false);
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
    setResolved(false);
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
    setResolved(false);
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
     setResolved(true);
                try {
                    const res = await fetch("http://localhost:8080/viewResolvedPredictions", {
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
        <div className="container-fluid">
            <button className="btn btn-dark" onClick={handleViewFootball}>View Football Prediction</button>
            <button className="btn btn-dark" onClick={handleViewMovie}>View Movies Prediction</button>
            <button className="btn btn-dark" onClick={handleViewSpace}>View Astronomy Prediction</button>
            <button className="btn btn-dark" onClick={handleViewWeather}>View Weather Prediction</button>
            <button className="btn btn-dark" onClick={handleViewCustom}>View Custom Prediction</button>
            <button className="btn btn-dark" onClick={handleViewResolved}>View Resolved Predictions</button>
        </div>
    );
};

const MainContent = ({ content, resolved }) => (
    <div className="container-fluid">
        {resolved && <ViewPredictionsTable data={content} />}
        {!resolved && <ViewResolvedPredictions data={content} />}
    </div>
);

const ViewPredictions = () => {
    const [displayedContent, setDisplayedContent] = useState('');


    const handleButtonClick = (content) => {
        setDisplayedContent(content);
    };

    return (
        <NavigationBar>
            <div style={{ height: '100vh', overflowY: 'auto' }}>
                <ButtonPanel onButtonClick={handleButtonClick} />
                <MainContent content={displayedContent} />
            </div>
        </NavigationBar>
    );
};

export default ViewPredictions;

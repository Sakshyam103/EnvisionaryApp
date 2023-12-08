import React from 'react';
import Home from '../Home';
import { useNavigate } from 'react-router-dom';
import NavigationBar from "./NavigationBar.jsx";

function PredictionOptions({ user }) {
    const navigate = useNavigate();

    const handleFootballPredictions = () => {
        console.log('Handling Football Predictions');
        navigate('/Home/MakePredictions/Football');
    };

    const handleMoviesPredictions = () => {
        console.log('Handling Movies Predictions');
        navigate('/Home/MakePredictions/Movies');
    };

    const handleAstronomyPredictions = () => {
        console.log('Handling Astronomy Predictions');
        navigate('/Home/MakePredictions/Astronomy');
    };

    const handleWeatherPredictions = () => {
        console.log('Handling Weather Predictions');
        navigate('/Home/MakePredictions/Weather');
    };

    const handleCustomPredictions = () => {
        console.log('Handling Custom Predictions');
        navigate('/Home/MakePredictions/Custom');
    };

    return (
        <div>
        <NavigationBar>
            <div>
                <h1>What type of predictions would you like to make?</h1>
                <div style={{ display: 'flex', flexDirection: 'column', height: '50vh' }}>
                    <button
                        style={{
                            flex: 1,
                            fontSize: '2em',
                            backgroundColor: '#9F2B68',
                            color: 'white',
                            border: 'pink',
                        }}
                        onClick={handleFootballPredictions}
                    >
                        Football Matches
                    </button>
                    <button
                        style={{
                            flex: 1,
                            fontSize: '2em',
                            backgroundColor: '#9F2B68',
                            color: 'white',
                            border: 'pink',
                        }}
                        onClick={handleMoviesPredictions}
                    >
                        Movies
                    </button>
                    <button
                        style={{
                            flex: 1,
                            fontSize: '2em',
                            backgroundColor: '#9F2B68',
                            color: 'white',
                            border: 'pink',
                        }}
                        onClick={handleAstronomyPredictions}
                    >
                        Astronomy
                    </button>
                    <button
                        style={{
                            flex: 1,
                            fontSize: '2em',
                            backgroundColor: '#9F2B68',
                            color: 'white',
                            border: 'pink',
                        }}
                        onClick={handleWeatherPredictions}
                    >
                        Weather
                    </button>
                    <button
                        style={{
                            flex: 1,
                            fontSize: '2em',
                            backgroundColor: '#9F2B68',
                            color: 'white',
                            border: 'pink',
                        }}
                        onClick={handleCustomPredictions}
                    >
                        Custom
                    </button>
                </div>
            </div>
        </NavigationBar>
        </div>
    );
}

export default PredictionOptions;

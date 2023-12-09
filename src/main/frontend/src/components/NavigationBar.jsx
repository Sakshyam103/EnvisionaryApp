// NavigationBar.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './NavigationBar.css';
import myLogo from '../assets/myLogo.png';

function NavigationBar({children}) {
    const navigate = useNavigate();

    const handleViewPredictions = () => {
        navigate('/Home/ViewPredictions');
    };

    const handleMakePredictions = () => {
        navigate('/Home/MakePredictions');
    };

    const handleViewStatistics = () => {
        navigate('/Home/ViewStatistics');
    };

    const handleNotifications = () => {
        navigate('/Home/ViewNotifications');
    };

    const handleResolvePredictions = () => {
        // For now, just navigate to the 'Resolve Predictions' route
        navigate('/Home/ResolvePredictions');
    };

    const handleAbout = () => {
        navigate('/Home/AboutUs');
    }

    return (
        <div>
        <nav className="navbar">
            <ul>
                <li>
                    <button className="logo-button" onClick={handleAbout}
                            style={{textAlign: 'center', color:'white'}}

                    >
                        <img src={myLogo} alt="logo" className="MyLogo" style={{ marginTop:'-12%', marginBottom:'-12%', marginRight:'-3%'}} />
                        NVISIONARY
                    </button>
                </li>

                <li>
                    <button className="nav-button" onClick={handleMakePredictions}
                    >
                        Make Predictions
                    </button>
                </li>
                <li>
                    <button className="nav-button" onClick={handleViewPredictions}>
                        View Predictions
                    </button>
                </li>
                <li>
                    <button className="nav-button" onClick={handleViewStatistics}>
                        View Statistics
                    </button>
                </li>
                <li>
                    <button className="nav-button" onClick={handleResolvePredictions}>
                        Resolve Predictions
                    </button>
                </li>
                <li>
                    <button className="nav-button" onClick={handleNotifications}>
                        Notifications
                    </button>
                </li>
            </ul>
        </nav>
    {children}
        </div>
    );
}

export default NavigationBar;

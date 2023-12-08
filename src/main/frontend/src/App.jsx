import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom'
import myLogo from './assets/myLogo.png'
import './App.css'
import SignIn from './components/oauth'
import Home from './Home'
import NavigationBar from './components/NavigationBar';
import PredictionOptions from './components/PredictionOptions'
import CustomPrediction from './components/CustomPrediction'
import FootballPrediction from './components/FootballPrediction'
import MoviesPrediction from './components/1.jsx'
import WeatherPrediction from './components/WeatherPrediction'
import AstronomyPrediction from "./components/AstronomyPrediction.jsx";
import ViewPredictions from "./components/ViewPredictions.jsx";
import ViewStatistics from "./components/ViewStatistics.jsx";
//import home from "./Home";

function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleUserLogin = (user) => {
    try {
      setLoading(true);
      setCurrentUser(user);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

    const handleMakePredictions = () => {
        alert('Please sign in first!')

    }
    const handleViewPredictions = () => {
        alert('Please sign in first!')

    }
    const handleViewStatistics = () => {
        alert('Please sign in first!')

    }
    const handleNotifications = () => {
        alert('Please sign in first!')

    }
    const handleResolvePredictions = () => {
        alert('Please sign in first!')

    }
  return (
    <Router>
    <div className="app-container">

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {!currentUser && (
      <div >
          {/*<div>*/}
          {/*    <NavigationBar*/}
          {/*        handleMakePredictions={handleMakePredictions}*/}
          {/*        handleViewPredictions={handleViewPredictions}*/}
          {/*        handleViewStatistics={handleViewStatistics}*/}
          {/*        handleResolvePredictions={handleResolvePredictions}*/}
          {/*        handleNotifications={handleNotifications}*/}
          {/*    />*/}
          {/*</div>*/}
          <div className='card'>
              <Routes>
                  <Route path="/" element = {<SignIn onUserLogin={handleUserLogin}/>} />
              </Routes>
          </div>
          <h1>Sign in to make predictions!</h1>
      </div>)}

      <Routes>
      <Route path="/Home" element={currentUser ? (
        <NavigationBar user={currentUser} />
      ) : (
        <>
        <Navigate to="/" replace />
        <SignIn onUserLogin={handleUserLogin} />
        </>
      )}
      />
          <Route path="/Home/MakePredictions" element={currentUser ? (
              <PredictionOptions user={currentUser} />
          ) : (
              <>
                  <Navigate to="/" replace />
                  <SignIn onUserLogin={handleUserLogin} />
              </>
          )}
          />
     {/*<Route path="/Home/MakePredictions" element={*/}
     {/*currentUser ? (*/}
     {/* <PredictionOptions user={currentUser}/></>*/}
     {/*) : (*/}
     {/* <Navigate to="/" replace />*/}
     {/*    <>*/}
     {/* <SignIn onUserLogin={handleUserLogin} />*/}
     {/* )*/}
     {/* }/>*/}
          <Route path="/Home/ViewPredictions" element={
              currentUser ? (
                  <>
                      <ViewPredictions user={currentUser}/></>
              ) : (
                  <>
                      <Navigate to="/" replace />

                      <SignIn onUserLogin={handleUserLogin} />
                  </>
              )
          }/>

          <Route path="/Home/ViewStatistics" element={
              currentUser ? (
                  <>
                      <ViewStatistics user={currentUser}/></>
              ) : (
                  <>
                      <Navigate to="/" replace />

                      <SignIn onUserLogin={handleUserLogin} />
                  </>
              )
          }/>
      <Route path="/Home/MakePredictions/Custom" element={
     currentUser ? (
      <>
      <CustomPrediction user={currentUser}/></>
     ) : (
      <>
      <Navigate to="/" replace />
      <SignIn onUserLogin={handleUserLogin} />
      </>
      )
      }/>
      <Route path="/Home/MakePredictions/Football" element={
     currentUser ? (
      <>
      <FootballPrediction user={currentUser}/></>
     ) : (
      <>
      <Navigate to="/" replace />
      <SignIn onUserLogin={handleUserLogin} />
      </>
      )
      }/>
      <Route path="/Home/MakePredictions/Movies" element={
     currentUser ? (
      <>
      <MoviesPrediction user={currentUser}/></>
     ) : (
      <>
      <Navigate to="/" replace />
      <SignIn onUserLogin={handleUserLogin} />
      </>
      )
      }/>
      <Route path="/Home/MakePredictions/Weather" element={
     currentUser ? (
      <>
      <WeatherPrediction user={currentUser}/></>
     ) : (
      <>
      <Navigate to="/" replace />
      <SignIn onUserLogin={handleUserLogin} />
      </>
      )
      }/>
          <Route path="/Home/MakePredictions/Astronomy" element={
              currentUser ? (
                  <>
                      <AstronomyPrediction user={currentUser}/></>
              ) : (
                  <>
                      <Navigate to="/" replace />
                      <SignIn onUserLogin={handleUserLogin} />
                  </>
              )
          }/>
        </Routes>
      </div>

      </Router>
  );
}

export default App

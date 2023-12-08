import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom'
import myLogo from './assets/myLogo.png'
import './App.css'
import SignIn from './components/oauth'
import NavigationBar from './components/NavigationBar.jsx';
import PredictionOptions from './components/PredictionOptions.jsx'
import CustomPrediction from './components/CustomPrediction.jsx'
import FootballPrediction from './components/FootballPrediction.jsx'
import MoviesPrediction from "./components/MoviesPrediction.jsx";
import WeatherPrediction from './components/WeatherPrediction.jsx'
import AstronomyPrediction from "./components/AstronomyPrediction.jsx";
import ViewPredictions from "./components/ViewPredictions.jsx";
import ViewStatistics from "./components/ViewStatistics.jsx";
import ViewNotifications from "./components/ViewNotifications.jsx";
import ResolvePredictions from "./components/ResolvePredictions.jsx";
import AboutUs from "./components/AboutUs.jsx";


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
  return (
    <Router>
    <div className="app-container">
    {!currentUser && (
      <div className='header-container'>
         <div className='logo'> <img src={myLogo} className="logo" alt="My logo" /></div>
          <h2 style={{marginLeft:'-11%'}}>nvisionary</h2>
      </div>)}
      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <div className='card'>
        <Routes>
      <Route path="/" element = {<SignIn onUserLogin={handleUserLogin}/>} />

      </Routes>
      </div>
      {!currentUser && (
      <div >
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
          <Route path="/Home/ViewNotifications" element={
              currentUser ? (
                  <>
                      <ViewNotifications user={currentUser}/></>
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
          <Route path="/Home/ResolvePredictions" element={
              currentUser ? (
                  <>
                      <ResolvePredictions user={currentUser}/></>
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
          <Route path="/Home/AboutUs" element={
              currentUser ? (
                  <>
                      <AboutUs user={currentUser}/></>
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

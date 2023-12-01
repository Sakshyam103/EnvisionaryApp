import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom'
import myLogo from './assets/myLogo.png'
import './App.css'
import SignIn from './components/oauth'
import Home from './Home'
import PredictionOptions from './components/PredictionOptions'
import CustomPrediction from './components/CustomPrediction'

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
          <h3>nvisionary</h3>
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
      <Route
      path="/Home"
      element={
      currentUser ? (
        <Home user={currentUser} />
      ) : (
        <>
        <Navigate to="/" replace />
        <SignIn onUserLogin={handleUserLogin} /> 
        </>
      )}
      />
     <Route path="/Home/MakePredictions" element={
     currentUser ? (
      <>
      <PredictionOptions user={currentUser}/></>
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
        </Routes>
      </div>
      
      </Router>
  );
}

export default App

import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom'
import myLogo from './assets/myLogo.png'
import './App.css'
import SignIn from './components/oauth'
import Home from './Home'


function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  //const navigate = useNavigate();

  const handleUserLogin = (user) => {
    try {
      // Assuming signIn is an asynchronous function for user authentication
      setLoading(true);
      // Call your authentication function (e.g., API request, etc.)
      // await signIn(user);
      setCurrentUser(user);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  // useEffect(() => {
  //   // Check if the user is not signed in and navigate to the sign-in page
    
  //   if (!currentUser) {
  //     navigate('/'); // Assuming your sign-in page is at the root path ('/')
  //   }
  // }, [currentUser]);

  return (
    <Router>
    <div className="app-container">
      <div>
          <img src={myLogo} className="logo" alt="My logo" />
          <h1>Welcome to Envisionary</h1>
      </div>
      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <div >
        <Routes>
        {/* <Route path="/Home" element={
        currentUser ? (<Home user={currentUser} />) : (<SignIn onUserLogin={handleUserLogin} />       
  )} /> */}
<Route path="/" element = {<SignIn onUserLogin={handleUserLogin}/>} />
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
        </Routes>
      </div>
      </div>
      </Router>
  );
}

export default App

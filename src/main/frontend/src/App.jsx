import { useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import myLogo from './assets/myLogo.png'
import './App.css'
import SignIn from './components/oauth'
import Home from './Home'

function App() {
  const [currentUser, setCurrentUser] = useState(null);

  const handleUserLogin = (user) => {
    setCurrentUser(user);
  };

  return (
    <>
    <Router>
    <div className="app-container">
      <div>
          <img src={myLogo} className="logo" alt="My logo" />
          <h1>Welcome to Envisionary</h1>
      </div>
      <div >
        <Routes>
        {/* <Route path="/" element={<SignIn />}/>  */}
        <Route path="/Home" element={<Home user={currentUser} />} />         
        </Routes>
      </div>
      <div className="card"><SignIn onUserLogin={handleUserLogin} /></div>
      </div>
      </Router>
    </>
  )
}

export default App

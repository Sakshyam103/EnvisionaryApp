import { useState } from 'react'
import myLogo from './assets/myLogo.png'
import './App.css'
import SignIn from './components/oauth'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
    <div className="app-container">
      <div>
          <img src={myLogo} className="logo" alt="My logo" />
          <h1>Welcome to Envisionary</h1>
      </div>
      <div className="card">
        <SignIn/>      
      </div>


      </div>
    </>
  )
}

export default App

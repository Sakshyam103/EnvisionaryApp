import { useEffect, useState } from 'react';
import { jwtDecode } from "jwt-decode";
import React from 'react';
import Home from '../Home';
import {useNavigate} from 'react-router-dom'

function SignIn() {

  const [user, setUser] = useState({});
  const navigate = useNavigate();

  function handleCallbackResponse(response) {
    //document.getElementById("signInDiv").hidden = true;
    console.log(response.credential);
    var decodedToken = jwtDecode(response.credential);
    console.log(decodedToken);
    setUser(decodedToken);
    let data = {idString: decodedToken};
    fetch("http://localhost:8080/example", {
      method:"POST",
      body: JSON.stringify(data),
      headers:{
        "Content-Type": "application/json",
       },
      }).then(res => {
        if(!res.ok){
          console.error('Request failed with status:' , res.status);
          return res.text();
        }
        return res.text();
      })
      .then(data => {
        console.log(data);
      }).catch(error=>{
        console.error('Error: ', error);
      })
     
     ;
    document.getElementById("signInDiv").hidden = true;
    //<Home/>
  }

  useEffect(() => {
    /*global google*/
      google.accounts.id.initialize({
        client_id: "624239085627-7c42lm3h0nhp55hdnfpov2mui6frb1bl.apps.googleusercontent.com",
        callback: handleCallbackResponse
      });
      const buttonOptions = {
        theme: "outline",
        size: "large",
        style: {
          position: "center", // Set the desired CSS position, e.g., relative, absolute, etc.
          top: "50%",           // Adjust the top position as needed
          left: "50%",          // Adjust the left position as needed
          transform: "translate(-50%, -50%)",
          width: "500px",        // Set the desired width
          height: "100px"         // Set the desired height
        }

      };
    google.accounts.id.renderButton(
      document.getElementById("signInDiv"),
       buttonOptions,
    );

    google.accounts.id.prompt();

  }, []);


  return (
    <div className="SignIn">
      <div id="signInDiv"></div>

      {Object.keys(user).length !== 0 && (
  <div>
    <img src={user.picture} alt={user.name} />
    <h3>{user.name}</h3>
    <h4>
      Hey {user.given_name}! Go{' '}
      <a href=" " onClick={() => navigate('/Home')}>
        make predictions!
      </a>
    </h4>
    
  </div>
)}
    </div>

  );
}

export default SignIn;
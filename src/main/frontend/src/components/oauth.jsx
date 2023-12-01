import { React, useEffect, useState } from 'react';
import { jwtDecode } from "jwt-decode";
import { useNavigate } from 'react-router-dom';
import {Link} from 'react-router-dom';

function SignIn({ onUserLogin }) {

  const [user, setUser] = useState({});
  const navigate = useNavigate();

  function handleCallbackResponse(response) {
    console.log(response.credential);
    var decodedToken = jwtDecode(response.credential);
    console.log(decodedToken);
    setUser(decodedToken);
    onUserLogin(decodedToken)
    let data = {idString: decodedToken};
    fetch("http://localhost:8080/login", {
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
          position: "center", 
          top: "50%",           
          left: "50%",          
          transform: "translate(-50%, -50%)",
          width: "500px",       
          height: "100px"         
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
      Hey {user.given_name}! You are logged in. Go{' '}
      <Link to="/Home"> make predictions!</Link>
    </h4>
    
  </div>
)}
    </div>

  );
}

export default SignIn;
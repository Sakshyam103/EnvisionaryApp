// import React, { useState, useEffect } from 'react';
// function ViewNotifications {
//
//     const handleNotifications = () => {
//         // Logic for 'Notifications' button
//         console.log("Notifications clicked");
//         fetch("http://localhost:8080/viewNotification", {
//             method: "GET",
//             headers: {
//                 "Content-Type": "application/json",
//             },
//         }).then(res => {
//             if (!res.ok) {
//                 console.error('Request failed with status:', res.status);
//                 return res.text();
//             }
//             return res.text();
//         })
//             .then(data => {
//                 console.log(data);
//
//             }).catch(error => {
//             console.error('Error: ', error);
//         })
//
//         ;
//     };
//
//     return(
//         {handleNotifications}
//     )
// };
//
// export default ViewNotifications;
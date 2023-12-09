import React, { useState, useEffect } from 'react';
import NavigationBar from "./NavigationBar.jsx";
import JsonTable from "./JsonTable.jsx";

// Define a functional component named ViewNotifications
function ViewNotifications() {
    const [notificationData, setNotificationData] = useState([]);

    // Use useEffect to fetch data when the component mounts
    useEffect(() => {
        const fetchData = async () => {
            try {
                // Log a message to the console indicating that data is being fetched
                console.log("Fetching notification data...");

                // Perform a fetch request to the specified endpoint
                const res = await fetch("http://localhost:8080/viewNotification", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                // Check if the fetch request was successful (status code 2xx)
                if (!res.ok) {
                    // Log an error message if the request fails and return
                    console.error('Request failed with status:', res.status);
                    return;
                }

                // If the request is successful, parse the response as JSON
                const data = await res.json();

                // Log the data received from the server
                console.log(data);

                // Set the notification data state with the received data
                setNotificationData(data);
            } catch (error) {
                // Log an error message if an error occurs during the fetch request
                console.error('Error: ', error);
            }
        };

        // Call the fetchData function when the component mounts
        fetchData();
    }, []); // The empty dependency array ensures that this effect runs once when the component mounts

    // Return the component JSX
    return (
        <div>
            <NavigationBar>
                <p>{notificationData}</p>
                {/*{notificationData.map((item, index) => (*/}
                {/*    <JsonTable key={index} data={item} />*/}
                {/*))}*/}

                {/* Render the JsonTable component with the notificationData */}
                {/*<JsonTable data={notificationData} />*/}
            </NavigationBar>
        </div>
    );
}

// Export the ViewNotifications component as the default export
export default ViewNotifications;

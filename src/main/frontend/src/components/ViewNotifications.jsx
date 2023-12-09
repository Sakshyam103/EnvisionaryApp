import React, { useState, useEffect } from 'react';
import NavigationBar from "./NavigationBar.jsx";
import JsonTable from "./JsonTable.jsx";

// Define a functional component named ViewNotifications
function ViewNotifications() {
    const [notificationData, setNotificationData] = useState('');


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
        // fetch("http://localhost:8080/viewNotification",{
        //     method:"GET",
        //     headers:{
        //         "Content-Type":"application/json",
        //     },
        // })
        //     .then(res=>{
        //         if(!res.ok){
        //             console.error("Error",res.status);
        //         }
        //     })
        //     .then(data=>{
        //         console.log(data);
        //         setNotificationData(data);
        //     })
        //     .catch(error=>{
        //         console.log("error");
        //     })
        // ;

    }, []); // The empty dependency array ensures that this effect runs once when the component mounts

    const JsonTable = ({ data }) => {
        if (!Array.isArray(data) || data.length < 1) {
            return <p>Insufficient data to display the table.</p>;
        }
        if (!data || data.length === 0) {
            return <p>No data available.</p>;
        }
        const formatValue = (value) => {
            if (typeof value === 'boolean') {
                return value.toString();
            } else if (typeof value === 'string' && value.includes('America/New_York')) {
                return value.slice(0, 10);
            } else if( value === null){
                return 'Standard';}
            else {
                return value;
            }
        };

        return (
            <div style={{ textAlign: 'center' }}>
                <table style={{ borderCollapse: 'collapse', width: '100%' }}>
                    <thead>
                    <tr>
                        {Object.keys(data[0]).map((header) => (
                            <th key={header} style={tableHeaderStyle}>
                                {header}
                            </th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            {Object.values(row).map((value, i) => (
                                <td key={i}>{formatValue(value)}</td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    };

// CSS styles for the table
    const tableHeaderStyle = {
        border: '1px solid #ddd',
        padding: '8px',
        background: 'black',
    };

    const tableRowStyle = {
        border: '1px solid #ddd',
    };

    const tableCellStyle = {
        border: '1px solid #ddd',
        padding: '8px',
    };

    // Return the component JSX
    return (
        <div>
            <NavigationBar>
                <p>{<JsonTable data={notificationData}/> }</p>
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

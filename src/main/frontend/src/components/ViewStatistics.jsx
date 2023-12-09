// ViewStatistics.jsx
import React, {useEffect, useState} from 'react';
import NavigationBar from "./NavigationBar.jsx";
import JsonTable from "./JsonTable.jsx";

const ButtonPanel = ({onButtonClick})=> {
    const [statisticsData, setStatisticsData] = useState([]);

    const handleDescriptivePrediction = async () => {
        try {
            const res = await fetch("http://localhost:8080/viewUserDescriptiveStatistics", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            if (!res.ok) {
                console.error("Request failed with status: ", res.status);
                const errorText = await res.text();
                throw new Error(errorText);
            }

            const data = await res.json();
            setStatisticsData(data);
            console.log(data);
            console.log(statisticsData);
            onButtonClick(statisticsData);
        } catch (error) {
            console.error("Error: ", error);
        }
    };

const handleInferentialPrediction = async () => {
    try {
        const res = await fetch("http://localhost:8080/viewUserInferentialStatistics", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });
        if (!res.ok) {
            console.error("Request failed with status: ", res.status);
            const errorText = await res.text();
            throw new Error(errorText);
        }

        const data = await res.json();
        setStatisticsData(data);
        console.log(data);
        onButtonClick(statisticsData);
    } catch (error) {
        console.error("Error: ", error);
    }
};

    const handleOverallDescriptivePrediction = async () => {
        try {
            const res = await fetch("http://localhost:8080/viewOverallDescriptiveStatistics", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            if (!res.ok) {
                console.error("Request failed with status: ", res.status);
                const errorText = await res.json();
                throw new Error(errorText);
            }

            const data = await res.json();
            setStatisticsData(data);
            console.log(data);
            onButtonClick(statisticsData);
        } catch (error) {
            console.error("Error: ", error);
        }
    };

    const handleOverallInferentialPrediction = async () => {
        try {
            const res = await fetch("http://localhost:8080/viewOverallInferentialStatistics",{
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            if (!res.ok) {
                console.error("Request failed with status: ", res.status);
                const errorText = await res.text();
                throw new Error(errorText);
            }


            const data = await res.json();
            setStatisticsData(data);
            console.log(data);
            onButtonClick(statisticsData);
        } catch (error) {
            console.error("Error: ", error);
        }
    };

    return (
        <div style={{display: 'flex', flex:'left', flexDirection:'column'}}>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%', marginTop: "40px"}} onClick={handleDescriptivePrediction}>View Descriptive Statistics</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleInferentialPrediction}>View Inferential Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleOverallDescriptivePrediction}>View Overall Descriptive Prediction</button>
            <button style={{marginBottom:'5px', padding:'8px', width:'15%'}} onClick={handleOverallInferentialPrediction}>View Overall Inferential Prediction</button>
        </div>
    );
};

// const ViewStatistics = () => {
//     const [notification, setNotification] = useState('');
//     const [viewInferential, setInferential] = useState('');
//     const [viewDeferential, setDeferential] = useState('');
//     const [viewOverallInferential, setOverallInferential] = useState('');
//     const [viewOverallDeferential, setOverallDeferential] = useState('');


    // const DataTable = ({data}) => {
    //     if (!Array.isArray(data) || data.length === 0) {
    //         return <p>No data available.</p>;
    //     }
    //
    //     // Assuming data is an array of objects
    //     const formatValue = (value) => {
    //         if (typeof value === 'boolean') {
    //             return value.toString();
    //         } else if (typeof value === 'string' && value.includes('America/New_York')) {
    //             return value.slice(0, 10);
    //         } else if( value === null){
    //             return 'Standard';}
    //         else {
    //             return value;
    //         }
    //     };
    //
    //     return (
    //         <div style={{ textAlign: 'center' }}>
    //             <table style={{ borderCollapse: 'collapse', width: '100%' }}>
    //                 <thead>
    //                 <tr>
    //                     {Object.keys(data[0]).map((header) => (
    //                         <th key={header} style={tableHeaderStyle}>
    //                             {header}
    //                         </th>
    //                     ))}
    //                 </tr>
    //                 </thead>
    //                 <tbody>
    //                 {data.map((row, index) => (
    //                     <tr key={index}>
    //                         {Object.values(row).map((value, i) => (
    //                             <td key={i}>{formatValue(value)}</td>
    //                         ))}
    //                     </tr>
    //                 ))}
    //                 </tbody>
    //             </table>
    //         </div>
    //     );
    // };

const PredictionStatsTable = ({ predictionStats }) => {
    if (predictionStats === null || predictionStats === undefined) {
        return(<p> no value to display</p>);

    } else {
        return (
            <div style={{textAlign: 'center'}}>
                <table style={{borderCollapse: "collapse", width: "100%"}}>
                    <thead>
                    <tr>
                        <th style={tableHeaderStyle}>Statistic</th>
                        <th style={tableHeaderStyle}>Value</th>
                    </tr>
                    </thead>
                    <tbody>
                    {Object.entries(predictionStats).map(([key, value]) => (
                        <tr key={key}>
                            <td>{key}</td>
                            <td>{value !== null ? value : "No value"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }
    };
    // const handleViewStatistics = () => {
    //     console.log("View Statistics clicked");
    //
    //     fetch("http://localhost:8080/viewUserDescriptiveStatistics", {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //         },
    //     }).then(res => {
    //         if (!res.ok) {
    //             console.error('Request failed with status:', res.status);
    //             return res.text();
    //         }
    //         return res.text();
    //     })
    //         .then(data => {
    //             //console.log(data);
    //             setDeferential(data);
    //             console.log(viewDeferential);
    //         }).catch(error => {
    //         console.error('Error: ', error);
    //     })
    //
    //     ;
    //     fetch("http://localhost:8080/viewUserInferentialStatistics", {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //         },
    //     }).then(res => {
    //         if (!res.ok) {
    //             console.error('Request failed with status:', res.status);
    //             return res.text();
    //         }
    //         return res.text();
    //     })
    //         .then(data1 => {
    //             //console.log(data1);
    //             setInferential(data1);
    //             console.log(viewInferential);
    //         }).catch(error => {
    //         console.error('Error: ', error);
    //     })
    //
    //     ;
    //     fetch("http://localhost:8080/viewOverallInferentialStatistics", {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //         },
    //     }).then(res => {
    //         if (!res.ok) {
    //             console.error('Request failed with status:', res.status);
    //             return res.text();
    //         }
    //         return res.text();
    //     })
    //         .then(data2 => {
    //             //console.log(data2);
    //             setOverallInferential(data2);
    //             console.log(viewOverallInferential);
    //         }).catch(error => {
    //         console.error('Error: ', error);
    //     })
    //
    //     ;
    //     fetch("http://localhost:8080/viewOverallDescriptiveStatistics", {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //         },
    //     }).then(res => {
    //         if (!res.ok) {
    //             console.error('Request failed with status:', res.status);
    //             return res.text();
    //         }
    //         return res.text();
    //     })
    //         .then(data3 => {
    //             //console.log(data3);
    //             setOverallDeferential(data3);
    //             console.log(viewOverallDeferential);
    //         }).catch(error => {
    //         console.error('Error: ', error);
    //     })
    //
    //     ;
    //
    // };

const tableHeaderStyle = {
    border: '1px solid #ddd',
    padding: '8px',
    background: 'black',
};

const MainContent = ({content})=>(
    <div style={{ flex:'right', marginLeft:'300px', marginTop:'-14%' }}>
        {<PredictionStatsTable predictionStats={content}/> }

    </div>
);


    const ViewStatistics = () => {
        const [displayedContent, setDisplayedContent] = useState('');

    const  handleButtonClick = (content)=>{
        setDisplayedContent(content);
    };

    // useEffect(() => {
    //     handleViewStatistics();
    // }, []);

    return (
        <NavigationBar>
        <div style={{height:'100vh' , overflowY: 'auto'}}>
             <ButtonPanel onButtonClick={handleButtonClick}/>
            <MainContent content={displayedContent}/>
            {/*<p>{viewDeferential}</p>*/}
            {/*<p>{viewInferential}</p>*/}
        </div>
        </NavigationBar>
    );
};

export default ViewStatistics;

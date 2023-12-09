// ViewStatistics.jsx
import React, {useEffect, useState} from 'react';
import NavigationBar from "./NavigationBar.jsx";

const ViewStatistics = () => {
    const [notification, setNotification] = useState('');
    const [viewInferential, setInferential] = useState('');
    const [viewDeferential, setDeferential] = useState('');
    const [viewOverallInferential, setOverallInferential] = useState('');
    const [viewOverallDeferential, setOverallDeferential] = useState('');


    const DataTable = ({ data }) => {
        if (!Array.isArray(data) || data.length === 0) {
            return <p>No data available.</p>;
        }

        // Assuming data is an array of objects
        const headers = Object.keys(data[0]);

        return (
            <table>
                <thead>
                <tr>
                    {headers.map(header => (
                        <th key={header}>{header}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {data.map((item, index) => (
                    <tr key={index}>
                        {headers.map(header => (
                            <td key={header}>{item[header]}</td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        );
    };
    // const JsonTable = ({data}) => {
    //     if(!Array.isArray(data) || data.length < 1){
    //         return <p>Insufficient data to display the table.</p>
    //     }
    //     if(!data || data.length ===0){
    //         return <p>No data available.</p>
    //     }
    //     return(
    //         <div style={{textAlign: 'center'}}>
    //             {/*{setNotification(data)}*/}
    //             {/*<p>{data}</p>*/}
    //             <table>
    //                 <thead>
    //                 <tr>
    //                     {Object.keys(data[0]).map((header)=>(
    //                         <th key={header}>{header}</th>
    //                     ))}
    //                 </tr>
    //                 </thead>
    //                 <tbody>
    //                 {data.map((item,index)=>(
    //                     <tr key = {index}>
    //                         {Object.keys(item).map((key)=>(
    //                             <td key={key}>{item[key]}</td>
    //                         ))}
    //                     </tr>
    //                 ))}
    //                 </tbody>
    //             </table>
    //         </div>
    //     );
    // };
    const handleViewStatistics = () => {
        console.log("View Statistics clicked");

        fetch("http://localhost:8080/viewUserDescriptiveStatistics", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        }).then(res => {
            if (!res.ok) {
                console.error('Request failed with status:', res.status);
                return res.text();
            }
            return res.text();
        })
            .then(data => {
                //console.log(data);
                setDeferential(data);
                console.log(viewDeferential);
            }).catch(error => {
            console.error('Error: ', error);
        })

        ;
        fetch("http://localhost:8080/viewUserInferentialStatistics", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        }).then(res => {
            if (!res.ok) {
                console.error('Request failed with status:', res.status);
                return res.text();
            }
            return res.text();
        })
            .then(data1 => {
                //console.log(data1);
                setInferential(data1);
                console.log(viewInferential);
            }).catch(error => {
            console.error('Error: ', error);
        })

        ;
        fetch("http://localhost:8080/viewOverallInferentialStatistics", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        }).then(res => {
            if (!res.ok) {
                console.error('Request failed with status:', res.status);
                return res.text();
            }
            return res.text();
        })
            .then(data2 => {
                //console.log(data2);
                setOverallInferential(data2);
                console.log(viewOverallInferential);
            }).catch(error => {
            console.error('Error: ', error);
        })

        ;
        fetch("http://localhost:8080/viewOverallDescriptiveStatistics", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        }).then(res => {
            if (!res.ok) {
                console.error('Request failed with status:', res.status);
                return res.text();
            }
            return res.text();
        })
            .then(data3 => {
                //console.log(data3);
                setOverallDeferential(data3);
                console.log(viewOverallDeferential);
            }).catch(error => {
            console.error('Error: ', error);
        })

        ;

    };

    useEffect(() => {
        handleViewStatistics();
    }, []);

    return (
        <div>

            <p>{viewDeferential}</p>
            <p>{viewInferential}</p>
            {/*/!* Add JSX for the ViewStatistics page *!/*/}
            {/*{handleViewStatistics}*/}
            {/*{notification && (*/}
            {/*    <div className="notification">*/}
            {/*        <p>{notification}</p>*/}
            {/*    </div>*/}
            {/*)}*/}
        </div>
    );
};

export default ViewStatistics;

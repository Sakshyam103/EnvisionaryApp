import React, { useEffect, useState } from 'react';
import NavigationBar from "./NavigationBar";
import JsonTable from "./JsonTable";

const ResolvePredictions = () => {
    const [predictionData, setPredictionData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await fetch("http://localhost:8080/viewEntertainmentPredictions", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!res.ok) {
                    console.error('Request failed with status:', res.status);
                    const errorText = await res.text();
                    throw new Error(errorText);
                }

                const data = await res.json();
                console.log(data);
                setPredictionData(data);
            } catch (error) {
                console.error('Error:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []); // Empty dependency array to run the effect only once on mount

    return (
        <div>
                {loading ? <p>Loading...</p> : <JsonTable data={predictionData} />}
        </div>
    );
};

export default ResolvePredictions;

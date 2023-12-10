import React, { useEffect, useState } from 'react';
import NavigationBar from "./NavigationBar";
import ResolveCustomPredictionsTable from "./JsonTableHeader/ResolveCustomPredictions";

const ResolvePredictions = () => {
  const [predictionData, setPredictionData] = useState([]);
  const [predictionContent, setPredictionContent] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await fetch("http://localhost:8080/viewCustomPredictions", {
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
      }finally {
               setLoading(false);
             }
    };

    fetchData();
  }, []);

  return (
    <div>
      <NavigationBar>
        {loading ? <p>Loading...</p> : null}
        {!loading ? <ResolveCustomPredictionsTable data={predictionData} /> : null}
      </NavigationBar>
    </div>
  );
};

export default ResolvePredictions;

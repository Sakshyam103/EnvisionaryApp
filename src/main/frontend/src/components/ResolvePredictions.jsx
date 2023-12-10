import React, { useEffect, useState } from 'react';
import NavigationBar from "./NavigationBar";
import JsonTable from "./JsonTable";
import ResolveThePredictions from "./ResolveCustomPredictions";

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
      }
    };

    const fetchContent = async () => {
      try {
        const res = await fetch("http://localhost:8080/viewCustomContent", {
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
        setPredictionContent(data);
      } catch (error) {
        console.error('Error:', error);
      } finally {
        setLoading(false);
      }
    };

    // Fetch both sets of data concurrently using Promise.all
    Promise.all([fetchData(), fetchContent()])
      .catch((error) => {
        console.error('Error during data fetching:', error);
        setLoading(false); // Make sure to set loading to false in case of an error
      });
  }, []);

  return (
    <div>
      <NavigationBar>
        {loading ? <p>Loading...</p> : null}
        {!loading ? <JsonTable data={predictionData} /> : null}
        {!loading ? <ResolveThePredictions data={predictionContent} /> : null}
      </NavigationBar>
    </div>
  );
};

export default ResolvePredictions;

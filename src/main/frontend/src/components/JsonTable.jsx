import React from "react";

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

export default JsonTable;


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
                {data.map((item, index) => (
                    <tr key={index} style={tableRowStyle}>
                        {Object.keys(item).map((key) => (
                            <td key={key} style={tableCellStyle}>
                                {item[key]}
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default JsonTable;


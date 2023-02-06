import React, {useEffect, useState} from 'react';
import {
    Chart as ChartJS,
    BarElement,
    CategoryScale,
    LinearScale,
    Tooltip,
    Legend
} from "chart.js";

import {Bar} from 'react-chartjs-2';

import "../styles/graphPage.css"
import {useHistory} from "react-router-dom";

ChartJS.register(
    BarElement,
    CategoryScale,
    LinearScale,
    Tooltip,
    Legend
);

/**
 * Graph component that displays a bar graph of the user's monthly income and expenses
 */
const Graph = () => {

    const [firstName] = useState(sessionStorage.getItem("firstName"));
    const [labels, setLabels] = useState([]);
    const [income, setIncome] = useState([]);
    const [expense, setExpense] = useState([]);
    const [uploadNewFile, setUploadNewFile] = useState(false);

    const history = useHistory();

    /**
     * Function to update the graph data
     * @param {Array} newData - The new data for the graph
     */
    const updateGraph = (newData) => {

        console.log(newData);

        const newLabels = newData.map(d => d.month);
        const newIncome = newData.map(d => parseMoneyString(d.income));
        const newExpense = newData.map(d => parseMoneyString(d.expense));

        setLabels(newLabels);
        setIncome(newIncome);
        setExpense(newExpense);

        console.log(newIncome);
    }

    /**
     * Function to handle the uploading of a new file
     */
    const uploadNewFileHandler = () => {
        setUploadNewFile(true);
    }



    useEffect(() => {

        /**
         * Function to fetch the latest data for the graph
         */
        const fetchData = async () => {
            const response = await fetch(`/api/monthlyIncomeAndExpense/${firstName}`);
            const data = await response.json();
            updateGraph(data);
        };

        fetchData().then(r => console.log(r));

        if (uploadNewFile) {
            history.push('/home');
        }

    }, [history, uploadNewFile]);


    /**
     * Function to parse a money string into a float
     * @param {string} moneyString - The string to parse
     * @returns {number} - The parsed number
     */
    function parseMoneyString(moneyString) {
        return parseFloat(moneyString.replace(/[^\d.-]/g, ''));
    }



    return (

        <div className="graph-container">
            <p className="graph-header">{firstName}, Here is a graph representing your monthly Income and Expenses for the year: </p>
            <Bar data={
                {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Income',
                            data: income,
                            backgroundColor: 'aqua',
                            borderColor: 'black',
                            borderWidth: 1
                        },
                        {
                            label: 'Expenses',
                            data: expense,
                            backgroundColor: 'darkblue',
                            borderColor: 'black',
                            borderWidth: 1
                        }
                    ]
                }
        } />

            <button className="graph-btn" onClick={uploadNewFileHandler}> Submit a new file</button>
        </div>

    );
}
export default Graph;
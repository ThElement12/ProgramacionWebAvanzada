import React from 'react'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

import { Bar } from 'react-chartjs-2';


export default function Graph(props) {
  ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
  );
  

  const option = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: props.title,
      },
    },
  };
  

  const formatData = () => {
    return {
      labels: props.labels,
      datasets: [
        {
          label: props.single_label,
          data: props.data,
          backgroundColor: props.color
        }
      ]
    }
  }

  return (
    <Bar options={option} data={formatData()}/>
  )
}

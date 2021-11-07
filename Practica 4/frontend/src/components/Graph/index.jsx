import React, { Component } from 'react'

import { Line } from 'react-chartjs-2';

export default class Graph extends Component {
  state = {
    data1: {
      labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
      datasets: [
        {
          label: 'Scanner #1',
          data: [ 1.6335105089090705, 7.100673694468565, 2.2232461154516834, 4.67244189115599, 3.8372975360001105, 6.367697452780637, 3.288008908029769, 5.722789081708081, 4.600790144100515, 6.973484380413999 ],
          fill: false,
          backgroundColor: 'rgb(255, 99, 132)',
          borderColor: 'rgba(255, 99, 132)'
        }
      ]
    },
    data2: {
      labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
      datasets: [
        {
          label: 'Scanner #2',
          data: [ 9.377823922144163, 9.258441786204937, 1.7818697499100744, 7.3980392137741475, 7.237284230337685, 7.083682180024521, 2.9280417326604393, 3.7891568678943903, 4.9864892263922584, 7.888660745254845 ],
          fill: false,
          backgroundColor: 'rgb(255, 99, 132)',
          borderColor: 'rgba(255, 99, 132)'
        }
      ]
    }
    ,
    option: {
      scales: {
        y: {
          beginAtZero: true
        }
      }

    }
  }

  render() {
    return (
      <div>
        <Line height={50} data={this.state.data1} options={this.state.option}/>
        <Line height={50} data={this.state.data2} options={this.state.option}/>
      </div>
    )
  }
}

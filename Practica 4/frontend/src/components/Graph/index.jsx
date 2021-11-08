import React, { Component } from 'react'

import { Line } from 'react-chartjs-2';

import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
export default class Graph extends Component {

  state = {
    data1: {
      labels: [],
      datasets: [
        {
          label: 'Cargando...',
          data: [],
          fill: false,
          backgroundColor: 'rgb(255, 99, 132)',
          borderColor: 'rgba(255, 99, 132)'
        }
      ]
    },
    data2: {
      labels: [],
      datasets: [
        {
          label: 'Cargando...',
          data: [],
          fill: false,
          backgroundColor: 'rgb(255, 99, 132)',
          borderColor: 'rgba(255, 99, 132)'
        }
      ]
    },
    option: {
      scales: {
        y: {
          beginAtZero: true
        }
      }

    }
  }
  componentDidMount() {
    const socketClient = new SockJS(process.env.REACT_APP_WEBSOCKET);
    const stompClient = Stomp.over(socketClient);
    const global = this
    stompClient.connect({}, (frame) => {
      const out = global
      stompClient.subscribe('/topic/sensor', function (sensor) {
        if (JSON.parse(sensor.body).length === 2) {
          const body = JSON.parse(sensor.body)
          out.setDataSensor(body, "1");
          out.setDataSensor(body, "2");
        }
      });
    })
  }
  setDataSensor = (body, device) => {
    const dataSensor = body.filter(data => data.device === device)
    if(dataSensor.length > 0){
      const newData = {
        labels: dataSensor[0].time.reverse(),
        datasets: [
          {
            label: "Temperatura",
            data: dataSensor[0].temperature.reverse(),
            fill: false,
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgba(255, 99, 132)'
          },
          {
            label: "Humedad",
            data: dataSensor[0].humidity.reverse(),
            fill: false,
            backgroundColor: 'rgb(66, 72, 247)',
            borderColor: 'rgb(66, 72, 247)'
  
          }
        ]
      }
      if(device === "1"){
        this.setState({ data1: newData })
      }else{
        this.setState({ data2: newData })
      }
    }
  }
  render() {
    return (
      <div>
        <h3>Sensor #1</h3>
        <Line height={50} data={this.state.data1} options={this.state.option} />
        <h3>Sensor #2</h3>
        <Line height={50} data={this.state.data2} options={this.state.option} />
      </div>
    )
  }
}

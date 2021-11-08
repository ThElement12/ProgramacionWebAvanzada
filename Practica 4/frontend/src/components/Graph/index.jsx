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
  componentDidMount() {
    const socketClient = new SockJS("http://localhost:8081/ws-sensor/");
    const stompClient = Stomp.over(socketClient);
    const global = this
    stompClient.connect({}, (frame) => {
      const out = global
      console.log(frame);
      stompClient.subscribe('/topic/sensor', function(sensor){
        out.setDataSensor1(JSON.parse(sensor.body));
        out.setDataSensor2(JSON.parse(sensor.body));
        console.log(JSON.parse(sensor.body))
      });
    })
    socketClient.onopen = () => {
      console.log('connected')

    }
    
    socketClient.onclose = () => {
      console.log('disconnected')
      // automatically try to reconnect on connection loss

    }

  }
  setDataSensor1 = (body) => {
    var dataSensor = body.filter(data => data.device === 1)
    dataSensor = dataSensor.slice(Math.max(dataSensor.length - 10, 0));
    const newData1 = {
      labels: dataSensor.map(data => data.time),
      datasets:[
        {
          label: "Temperatura",
          data: dataSensor.map(data => data.temperature),
          fill: false,
          backgroundColor: 'rgb(255, 99, 132)',
          borderColor: 'rgba(255, 99, 132)'
        },
        {
          label: "Humedad",
          data: dataSensor.map(data => data.humidity),
          fill: false,
          backgroundColor: 'rgb(66, 72, 247)',
          borderColor: 'rgb(66, 72, 247)'

        }
      ]
    }
    this.setState({data1: newData1})
  }
  setDataSensor2 = (body) => {
    var dataSensor = body.filter(data => data.device === 2)
    dataSensor = dataSensor.slice(Math.max(dataSensor.length - 10, 0));
    const newData1 = {
      labels: dataSensor.map(data => data.time),
      datasets:[
        {
          label: "Temperatura",
          data: dataSensor.map(data => data.temperature),
          fill: false,
          backgroundColor: 'rgb(255, 99, 132)',
          borderColor: 'rgba(255, 99, 132)'
        },
        {
          label: "Humedad",
          data: dataSensor.map(data => data.humidity),
          fill: false,
          backgroundColor: 'rgb(66, 72, 247)',
          borderColor: 'rgb(66, 72, 247)'

        }
      ]
    }
    this.setState({data2: newData1})
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

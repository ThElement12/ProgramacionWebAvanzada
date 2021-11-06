import React, { Component } from 'react';

import Graph from '../../components/Graph';

export default class HomePage extends Component {

  ws = new WebSocket(process.env.REACT_APP_WEBSOCKET)
  

  componentDidMount() {
    this.ws.onopen = () => {
      console.log('connected')
    }

    this.ws.onmessage = evt => {
      // listen to data sent from the websocket server
      const message = evt.data
      this.setState({ dataFromServer: message })
      console.log(message)
    }

    this.ws.onclose = () => {
      console.log('disconnected')
      // automatically try to reconnect on connection loss

    }

  }
  render() {
    return (
      <div>
        Hola Todos otra vez
        <Graph/>
      </div>
    )
  }
}

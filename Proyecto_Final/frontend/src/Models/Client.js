import User from './User.js';

export default class Client extends User{
  constructor(username, email, password, events){
    super(username, email, password);
    this.events = events;
  }
}
import User from './User.js';

export default class Client extends User{
  constructor(username, email,events){
    super(username, email);
    this.events = events;
  }
}
import User from './User.js';

export default class Employee extends User{
  constructor(username, email, available){
    super(username, email);
    this.available = available;

  }
}
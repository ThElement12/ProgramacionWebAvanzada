import User from './User.js';

export default class Employee extends User{
  constructor(username, email, password, available){
    super(username, email, password);
    this.available = available;

  }
}
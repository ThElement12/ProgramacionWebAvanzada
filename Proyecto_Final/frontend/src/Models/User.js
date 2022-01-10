export default class User{
  constructor(username, mail, fullName, password){
    this.id = null;
    this.username = username;
    this.fullName = fullName;
    this.password = password;
    this.mail = mail;
    this.roles = [];
  }
}

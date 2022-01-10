export default class Event{
  constructor(id, name,startTime,endTime,plan, username, totalPrice, productsRequests){
    this.id = id;
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.plan = plan;
    this.username = username;
    this.totalPrice = totalPrice;
    this.productsRequests = productsRequests;
  }
}

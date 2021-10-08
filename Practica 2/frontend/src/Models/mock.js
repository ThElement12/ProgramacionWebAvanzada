export default class Mock {
    constructor(url, status, method, header, contentype){
        this.url = url;
        this.status = status;
        this.method = method;
        this.header = header;
        this.contentype = contentype;
    }
}
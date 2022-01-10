export default function authHeader(){
  if(sessionStorage.getItem('jwt')){
      return {Authorization: 'Bearer ' + sessionStorage.getItem('jwt')};
  }else{
      return {};
  }
}
import './App.css';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useState} from "react";
import axios from "axios";

function App() {
  const [message, setMessage] = useState();

  const responseHandler = ({data}) => {
    setMessage(data);
    return data;
  };

  const errorHandler = ({message}) =>{
    setMessage(message);
    return message;
  };

  const onNonCorsHeaderHandler = () => {
    axios.get('http://localhost:8080/not-cors')
      .then(responseHandler)
      .catch(errorHandler);
  };

  const onCorsHeaderHandler = () => {
    axios.get('http://localhost:8080/cors').then(responseHandler);
  };

  return (
    <div className="App">
      <br></br>
      <p>
        <h2> GCU PROXY</h2>
        (FrontEnd-BackEnd Integration)
      </p>
      <br></br>
      <p>
        {message}
      </p>
      <div>
        <Button className='btn btn-secondary' onClick={onNonCorsHeaderHandler}>
          non cors header
        </Button>
        <Button bsStyle="primary" bsSize="large" onClick={onCorsHeaderHandler}>
          cors header
        </Button>
      </div>
    </div>
  );

}

export default App;

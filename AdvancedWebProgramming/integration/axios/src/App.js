import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import styled from 'styled-components';
import axios from "axios";
import { useState, useEffect } from "react";

const Div = styled.div`
 margin: 1em;
 padding: 0.23em 13m;
`;

function App() {
  const baseUrl = "http://localhost:8081";

  const [name, setName] = useState();
  const [password, setPassword] = useState();

  useEffect(()=>{
      getUser();
  },[]);

  async function getUser(){
      await axios
            .get(baseUrl + "/")
            .then((response) => {
                console.log(response.data)
                setName(response.data.name);
                setPassword(response.data.password);
            })
            .catch((error) => {
                console.log(error);
            })
  }

  const handleChange_username = (e)=>{
      e.preventDefault();
      setName(e.target.value);
  }

  const handleChange_password = (e)=>{
      e.preventDefault();
      setPassword(e.target.value);
  }

  const handleSubmit = async (e) => {
      e.preventDefault();

      await axios
            .post(baseUrl + "/api/member",{
                name: name,
                password: password,
            })
            .then((response) => {
                console.log(response.data)
            })
            .catch((error)=>{
                console.log(error);
            });
  }

  return (
      <Div margin-left="10px">
        <br></br>
        <h1> Member Service</h1>
        <p></p>
          <form onSubmit={handleSubmit}>
             <p> username: <input type="text" class="form-control" placeholder="Username" aria-label="Username"  required={true} value={name} onChange={handleChange_username}></input></p>
             <p>password: <input type="text" class="form-control" placeholder="Password" aria-label="Password" required={true} value={password} onChange={handleChange_password}></input></p>
            
              <Button class="btn btn-outline-primary"  type="submit" > OK</Button>
             
          </form>
      </Div>
  )
}

export default App;

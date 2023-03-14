import './App.css';
import React from "react";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Layout from './pages/Layout';
import Home from './pages/Home';
import Carinfo from './pages/Carinfo';
import Userinfo from './pages/Userinfo';

function App() {
  return (
    <BrowserRouter>
    <h1>Welcome to the Service of Car sales</h1>
    <Routes>
      <Route path="/" element = {<Layout/>}>
      <Route index element={<Home/>}/>
      <Route path="carinfo" element={<Carinfo/>}/>
      <Route path="userinfo" element={<Userinfo/>}/>
      </Route>
    </Routes>
    </BrowserRouter>

  );
}

export default App;

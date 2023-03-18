import React, { useState } from "react";

function Calculator(){

    const [num1, setNum1] = useState('');
    const [num2, setNum2] = useState('');

    const [add, setAdd] = useState('');
    const [sub, setSub] = useState('');
    const [mul, setMul] = useState('');
    const [div, setDiv] = useState('');

    const handleNum1Change = (e) => {
        setNum1(e.target.value);
    }

    const handleNum2Change = (e) =>{
        setNum2(e.target.value);
    }

    const addHandler = () =>{
        setAdd(parseInt(num1)+parseInt(num2));
    }

    const subHandler = () =>{
        setSub(parseInt(num1) - parseInt(num2));
    }

    const mulHandler = () =>{
        setMul(parseInt(num1) * parseInt(num2));
    }

    const divHandler = () =>{
        setDiv(parseInt(num1) / parseInt(num2));
    }

    return (
    <div align = "center">
    <h2> GCU Calculator</h2> 
    <input type="text" value={num1} onChange={handleNum1Change}></input>
    <input type="text" value={num2} onChange={handleNum2Change}></input>

    <p>
    <button onClick={addHandler}> Add </button> &nbsp;{add}&nbsp;
    <button onClick={subHandler}> Sub </button> &nbsp;{sub}&nbsp;
    <button onClick={mulHandler}> Mul </button> &nbsp;{mul}&nbsp;
    <button onClick={divHandler}> Div </button> &nbsp;{div}&nbsp;
    </p>

    </div>
    );
}

export default Calculator;

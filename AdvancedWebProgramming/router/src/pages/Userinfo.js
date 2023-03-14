import React from "react";

const Userinfo = () =>{
    const style = {
        margin: "10px"
    }
    return (
        <>
        <div>
            <h2>Customer Information</h2>
            <span style={style}>Customer name: SukHwan Cho</span><br/>
            <span style={style}>Product: Grandeur XG Pric</span><br/>
            <span style={style}>e: 5500</span><br/>
            <span style={style}>Date: 2023-03-02</span>
        </div>
        </>
    );
}

export default Userinfo;
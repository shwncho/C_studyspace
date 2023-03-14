import React from "react";

const car=[
    {name:"Grandeur", price:5600, explainaion:"Economic cost Funct"},
    {name:"BMW", price:7800, explainaion:"ional excellence Grac"},
    {name:"Genesis", price:8000, explainaion:"eful Design"},
];


const Carinfo=()=>{
    return(
        <div>
            <h2>
                Car Information
            </h2>
            <table border="1">
               <tbody>
                {car.map((std)=>{
                    return(
                        <tr>
                            <td>{std.name}</td>
                            <td>{std.price}</td>
                            <td>{std.explainaion}</td>
                        </tr>
                    )
                })}
               </tbody>
            </table>
        </div>
    );
};

export default Carinfo;
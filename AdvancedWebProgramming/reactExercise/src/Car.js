import React from "react";

const car=[
    {id:1, name:"GRANDEUR", price:4500, explain:"Graceful design"},
    {id:2, name:"BMW", price:7800, explain:"Functional of high level"},
    {id:3, name:"AUDI", price:8000, explain:"Luxury Car"},
    {id:4, name:"BENZ", price:8500, explain:"Classy Car"},
    {id:5, name:"GENESIS", price:6700, explain:"Popular Car"}
];
const CarItem=({car}) => (
    <div>
        <span>
            [{car.id}] {car.name} {car.price} {car.explain}
        </span>
    </div>
);


const Car = () =>{
    return(
        <div>
            <h2>
                Car List
            </h2>
            {car.map((std)=>(
                <CarItem car={std}/>
            ))}
        </div>
    );
};

export default Car;
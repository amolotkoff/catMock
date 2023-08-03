import React from "react";
import "../assets/css/font-awesome.min.css"
import "../assets/css/w3.css"

//navigation must be vertical or horizontal
export default function Navigation({ bars }) {
    return (
        <div className='w3-sidebar w3-bar-block w3-teal w3-xxlarge' style={{width: '70px'}}>
            {bars.map(bar => {
                return (
                    <button onClick={bar.onClick} className="w3-bar-item w3-button"><i className={bar.icon}></i></button> 
                );
            })}
        </div>
    );
}
import React from "react";
import "../assets/css/Navigation.css"

//navigation must be vertical or horizontal
export default function Navigation({ bars, style }) {
    return (
        <div className='navigation-bar'>
            {bars.map(bar => {
                return (
                    <button onClick={bar.onClick} className="navigation-bar" >
                        {bar.content}
                    </button>
                );
            })}
        </div>
    );
}
import React from "react"
import "../assets/css/w3.css"

export default function Content(props) {

    return (
        <div style={{marginLeft: '70px'}}>
            <div className="w3-container">
                {props.children}
            </div>
        </div>
    );
}
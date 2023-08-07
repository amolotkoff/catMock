import React, { lazy, useState } from "react";
import classNames from "classnames";
import "../assets/css/w3.css"

export default function Collapsible(props) {
    const [isOpen, setIsOpen] = useState(props.open);
    const onClick = () => setIsOpen(!isOpen);

    return (
        <div>
            <button onClick={onClick} className={classNames("w3-button", "w3-block", "w3-left-align", props.className)}>
                {props.label}
            </button>
            <div className={classNames("w3-container", isOpen ? "w3-show" : "w3-hide")}>
                {props.children}
            </div>
        </div>
    ); 
}
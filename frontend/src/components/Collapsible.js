import React, { useState } from "react";
import "../assets/css/w3.css"

export default function Collapsible({open, label, content }) {
    [open, setOpen] = useState(open);

    const onClick = setOpen(open);

    return (
        <div>
            <button onClick={onClick}>{label}</button>
            {open && (
                <div>
                    {content}
                </div>
            )}
        </div>
    ); 
}
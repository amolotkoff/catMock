import React, { createRef, useState } from "react";
import BarNavigation from "./BarNavigation";
import Content from "./Content";

export default function Page(props) {
    const [content, setContent] = useState(props.children);
        
    return (
        <>
            <BarNavigation onCallback={setContent}/>
            <div style={{marginLeft: '10px'}}>
                <Content>{content}</Content>
            </div>
        </>
    );
}
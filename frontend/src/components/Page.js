import React from "react";
import BarNavigation from "./BarNavigation";
import Content from "./Content";

export default function Page(props) {
    return (
        <>
            <BarNavigation/>
            <div style={{marginLeft: '70px'}}>
                <Content>{props.children}</Content>
            </div>
        </>
    );
}
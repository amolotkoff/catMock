import React, { useRef } from "react";
import Navigation from "./Navigation";
import axios from "axios";
import HomePage from "./HomePage";
import DelayPage from "./DelayPage";

export default function BarNavigation({onCallback}) {
    const home = {
        icon : 'fa fa-home',
        onClick : () => onCallback(<HomePage />)
    }

    const delay = {
        icon: 'fa fa-play-circle-o',
        onClick: () => onCallback(<DelayPage />)
    }

    return (<Navigation bars={[home , delay ]} />);
}
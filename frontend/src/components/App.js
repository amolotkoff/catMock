import React from 'react';
import { useEffect, useState } from "react";
import axios from "axios";
import "../assets/css/App.css"
import Navigation from './Navigation';

export default function App() {
  const [host, setHost] = useState({ name : "undefined-host"});
  
  useEffect(() => {
    const fetchHost = async () => {
      const result = await axios('/mock/api/host');
      setHost(result.data);
    }

    fetchHost();
  }, []);

  return (
    <div className="wraper">
      <div className='top'>
      </div>
      <div className='main'>
          
      </div>
  
      <Navigation bars={[{content: "aaaa", onClick :  () => {}} , {content: "aaaaaaaaa", onClick :  () => {}}]} className="top-bar" />
      
      <div className="left-bar">
        LEFT
      </div>
      <div className="main">
        N content here MAIN content here MAIN content here 
      </div>
    </div>
  );
}



/*
 hello = () => {
  fetch('/api/time')
  .then( res => res.text())
  .then(res => { this.setState( {message: res})
  });
};*/
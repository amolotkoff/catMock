import React, { useState } from "react";
import axios from "axios";
import Page from "./Page";


export default function DelayPage() {
    [contentPage, setContentPage] = useState((<div></div>));

    useEffect(() => {
        const fetchHost = async () => {
            const result = await axios('/mock/api/all');
            setContentPage(result.data);
        }
    
        fetchHost();
      }, []);
    
    return (<Page></Page>);
}
import React, { useState, useEffect, useLayoutEffect } from "react";
import Collapsible from "./Collapsible"
import  className from "classnames" 
import "../assets/css/w3.css"

export default function DelayPage() {
    const [fetched, setFetched] = useState()
    useEffect(() => {
        const delayDataFetch = async () => {
            const data = await (await fetch("/mock/api/all")).json();
            setFetched(data);
        };
        delayDataFetch();
    }, []);

    if (fetched == undefined)
        return <p>loading</p>

    return (<div className="w3-container">
                {Object.keys(fetched).map((controllerName) => {
                    return (<Collapsible label={controllerName} className={"w3-green"}>
                                <table className="w3-table w3-hoverable">
                                        {fetched[controllerName].map((api) => {
                                            let name = api["name"]
                                            let path = api["path"]
                                            let requestMethod = api["requestMethod"]
                                            let delayType = api["delay"]["factory"]["type"]
                                            let delayKeys = api["delay"]["keys"] // array

                                            return (<tr>
                                                        <td style={{width: "20%"}}>{name}</td>
                                                        <td style={{width: "20%"}}>{path}</td>
                                                        <td style={{width: "10%"}}>{requestMethod}</td>
                                                        <td style={{width: "10%"}} className="w3-red" >{delayType}</td>
                                                        <td style={{width: "20%"}}>
                                                            <table className="w3-table">
                                                                {Object.keys(delayKeys).map(delayName => {
                                                                    return <tr>
                                                                                <th style={{width: "30%"}}>{delayName}</th>
                                                                                <th style={{width: "70%"}}><input value={delayKeys[delayName]["Value"]}></input></th>
                                                                           </tr>
                                                                })}
                                                            </table>
                                                        </td>
                                                        <td style={{width: "10%"}}>
                                                            <button className="w3-button w3-red">
                                                                Замедлить
                                                            </button>
                                                        </td>
                                                        <td style={{width: "10%"}}>
                                                            <button className="w3-button w3-red">
                                                                Исходное
                                                            </button>
                                                        </td>

                                                    </tr>);
                                        })}
                                </table>
                            </Collapsible>)
            
                })}
            </div>
    );
}
import React, { useState, useEffect, useLayoutEffect, useId, useRef } from "react";
import Collapsible from "./Collapsible"
import {generatePath} from "react-router"
import LoadingPage from "./LoadingPage";
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
        return <LoadingPage /> // TODO: add loading page

    return (<div className="w3-container">
                {Object.keys(fetched).map((controllerName) => {
                    return (<Collapsible label={controllerName} className={"w3-green"}>
                                <table className="w3-table w3-hoverable" style={{tableLayout: "fixed"}}>
                                    <tbody>
                                        {fetched[controllerName].map((api) => {
                                                let name = api["name"]
                                                let path = api["path"]
                                                let requestMethod = api["requestMethod"]
                                                let delayType = api["delay"]["factory"]["type"]
                                                let delayKeys = api["delay"]["keys"] // array
                                                const inputClassName = `${controllerName}-${name}`

                                                const sendDelay = () => {
                                                    let delay = {}
                                                    for(let input of Array.from(document.getElementsByClassName(inputClassName)))
                                                        delay[input.name] = Number(input.value)
                                                    
                                                    fetch(generatePath("/mock/api/id/:parent/:name", { parent : controllerName, name : name })  , { 
                                                            method: "POST", 
                                                            body: JSON.stringify(delay), 
                                                            headers: { 'Content-Type': 'application/json' }
                                                        });
                                                }

                                                const sendStartup = () => {                      
                                                    for(let input of Array.from(document.getElementsByClassName(inputClassName)))
                                                        input.value = delayKeys[input.name]["startValue"]

                                                    fetch(generatePath("/mock/api/id/:parent/:name/startup", { parent : controllerName, name : name }), {
                                                        method: "GET",
                                                        headers: { 'Content-Type': 'application/json' }
                                                    })
                                                }

                                                return (<tr>
                                                            <td style={{width: "10%"}}>{name}</td>
                                                            <td style={{width: "10%"}}>{path}</td>
                                                            <td style={{width: "20%", textAlign: "center" }}>{requestMethod}</td>
                                                            <td style={{width: "10%"}} className="w3-red" >{delayType}</td>
                                                            <td style={{width: "30%"}}>
                                                                <table className="w3-table">
                                                                    {Object.keys(delayKeys).map(delayName => {
                                                                        const onkeyChanged = (event) => {
                                                                            if (event.target.value < 0)
                                                                                event.target.value = 0;

                                                                            delayKeys[delayName]["Value"] = event.target.value;
                                                                            setFetched(fetched);
                                                                        }
                                                                        
                                                                        return <tr>
                                                                                    <th style={{width: "30%"}}>{delayName}</th>
                                                                                    <th style={{width: "70%"}}>
                                                                                        <input 
                                                                                            name={delayName} 
                                                                                            defaultValue={delayKeys[delayName]["Value"]} 
                                                                                            type="number" 
                                                                                            onChange={onkeyChanged} 
                                                                                            className={inputClassName} />
                                                                                    </th>
                                                                               </tr>
                                                                    })}
                                                                </table>
                                                            </td>
                                                            <td style={{width: "10%"}}>
                                                                <button className="w3-button w3-green" onClick={sendDelay}>
                                                                    Замедлить
                                                                </button>
                                                            </td>
                                                            <td style={{width: "10%"}}>
                                                                <button className="w3-button w3-green" onClick={sendStartup}>
                                                                    Исходное
                                                                </button>
                                                            </td>

                                                        </tr>);
                                            })}
                                    </tbody>
                                </table>
                            </Collapsible>)
                })}
            </div>
    );
}
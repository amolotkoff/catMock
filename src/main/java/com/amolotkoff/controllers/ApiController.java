package com.amolotkoff.controllers;

import com.amolotkoff.parser.model.api.Api;
import com.amolotkoff.mocker.util.DelayKey;
import org.springframework.http.MediaType;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mock/api")
public class ApiController
{
    private HashMap<String, List<Api>> apis = new HashMap<String, List<Api>>();

    public void AddApi(String parent, Api api) {
        if (!apis.containsKey(parent))
            apis.put(parent, new ArrayList<>());

        apis.get(parent).add(api);
    }

    @GetMapping(value = "/id/{parent}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, Object> fetchApi(@PathVariable String parent, @PathVariable String name) {
        for(Api api : apis.get(parent))
            if (api.getName().equals(name))
                return generateApi(api);

        return null;
    }

    @PostMapping(value = "/id/{parent}/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateApi(@PathVariable String parent, @PathVariable String name, @RequestBody HashMap<String, Long> model) {
        Api api = null;

        for (Api candidate: apis.get(parent))
            if (candidate.getName().equals(name)) {
                api = candidate;
                break;
            }

        HashMap<String, DelayKey> keys = api.getDelay().keys;

        for (String key : model.keySet()) {
            Long value = model.get(key);
            DelayKey delayKey = keys.get(key);
            delayKey.Value = value;
        }
    }

    @GetMapping(value = "/id/{parent}/{name}/startup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, Long> startupApi(@PathVariable String parent, @PathVariable String name) {
        HashMap<String, Long> startup = new HashMap<>();

        Api api = null;

        for (Api candidate: apis.get(parent))
            if (candidate.getName().equals(name)) {
                api = candidate;
                break;
            }

        for (String key : api.getDelay().keys.keySet()) {
            DelayKey delay = api.getDelay().keys.get(key);
            delay.Value = delay.getStartValue();
            startup.put(key, delay.getStartValue());
        }

        return startup;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public HashMap<String, List<Api>> fetchApis() {
        return apis;
    }

    private HashMap<String, Object> generateApi(Api api) {
        HashMap<String, Object> fetchedApi = new HashMap<>();

        fetchedApi.put("name", api.getName());
        fetchedApi.put("path", api.getPath());

        HashMap<String, Object> apiDelay = new HashMap<>();
        fetchedApi.put("delay", apiDelay);

        apiDelay.put("type", api.getDelay().factory.toString());

        apiDelay.put("keys", api.getDelay().keys);

        return fetchedApi;
    }

    /*
    @GetMapping(value = "/response-delay", consumes = MediaType.ALL_VALUE)
    public String getResponseDelayPage(Model model) {
        model.addAttribute("delays", delays);
        return "responseForm2";
    }

    @PostMapping(value = "/response-delay/set/{url}", params = "action=update", consumes = MediaType.ALL_VALUE)
    public String update(@RequestParam HashMap<String, String> params, @PathVariable(name = "url") String url) {
        HashMap<String, DelayKey> delayFactory = delays.get(url);

        for (Map.Entry<String, String> param : params.entrySet()) {
            String keyName = param.getKey();
            long keyValue = Long.valueOf(param.getValue());

            DelayKey delayKey = delayFactory.get(keyName);
            delayKey.Value = keyValue;
        }

        return "redirect:/response-delay";
    }

    @PostMapping(value = "/response-delay/set/{url}", params = "action=init", consumes = MediaType.ALL_VALUE)
    public String _init(@RequestParam HashMap<String, String> params, @PathVariable(name = "url") String url) {
        HashMap<String, DelayKey> delayFactory = delays.get(url);

        for (Map.Entry<String, String> param : params.entrySet()) {
            String keyName = param.getKey();
            DelayKey delayKey = delayFactory.get(keyName);
            delayKey.Value = delayKey.StartValue;
        }

        return "redirect:/response-delay";
    }
    */

}
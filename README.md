# catMock
Проект предназначен для динамического создания java Spring api's через файлики-конфиги
## Project Structure
- catMock.jar
- logs (папка с логами)
- lib (папка с библиотеками, **без нее ничего не запустится**)
- jdk (папка java + compiler)
- config (папка с конфигами для логгера и спринга)
- build (папка с файликами мока)

**Все самое интересное для нас находится в папках config, build**
## Build
Для того чтобы добавить конфигурацию, зайдите в папку build и создайте там yaml-файл 
**ВАЖНО! Имя файла должно быть уникальным!**
Структура файлика:
```yaml
http: # корневой элемент
  api: # здесь начинаются api'шки
    api1: # имя api, должно быть уникальным
      method: get # метод (get, post, и тд)
      path: '/mock/get' # url'а апишки
      result: # здесь результат работы апишки
        status: 200 #код ответа
        value: "<xml>123456789</xml>" # body ответа
      delay: # замедление, опционально и может отсутствовать
        type: base # стандартный тип, константная задержка
        value: 55 # 55 мс
```

Разберем структуру посложнее:

```yaml
http:
  context: # здесь хранятся переменные
    script: 'String token = "jwt-dsfbfdsbgfbfb54gb4v45g45g4";' # так мы можем вставлять кодом переменные
    source: # имя переменной
      file: "/files/myXml.xml" # так мы указываем, что значение переменной находится в файле по указанному пути (относительно текущего файла) 
    ssltoken: "jwt-ssl-eve4rh34rh34vfu347f34f3bfv"
  script: "int i = 5;" # здесь вставляем код, который выполняется при инициализации контроллера
  api:
    api2:
      method: get
      path: "/mock/get"
      produces: "application/json" # Content-Type
      consumes: "application/json" # Content-Type
      result:
        status: 200
        value: "${source}" # так происходит подстановка значения по указанному имени (может содержаться и в файлике) 
      delay:
        type: base
        value: 55
    api1:
      script: 'int i = 5;' # код, который выполняется каждый раз при обработке нового запроса
      method: get
      path: "/mock/{id}/get"
      async: 'async/exampleAsyncController.yml' # здесь указываем async-response (http-запрос, выполняемый после обработки входящего запроса) , путь указывается относителньо текущего файла
      headers: # хедеры ответа
        mySyperheader: 'superValue'
      result:
        status: 200
        value:
          file: '/files/myXml.xml' # читаем тело из файлика
      delay:
        type: percentile # тип перцентиль
        min: 100
        max: 200
        percentile: 90
        value: 55
```

Рассмотрим async-контроллер:
```yaml
async: # начинается асинк-контроллер
    context:
        id2: "5"
        id1:
            file: '/value1.txt'
        script: 'int b = 10;'
    timeout: 1000
    method: get
    path: 'http://localhost:8080/{id2}/mock/get?id={id2}' # обратите внимание,  {name} замещаются значениями переменных
    script: '' #some script executing every executing
    headers:
        myHeader: 'myHeaderValue'
    query-params:
        param1: 'value1'
    body:
        value: 'blabla'
    check:
        status: 200
```


      script: '
        //context.put("param1", "param1"); //этот метод сохраняет переменную в сабститютор
        //context.save("param2","param2"); // этот метод сохраняет переменную для дальнейшего использования в async
        //String dummyResult = context.substitude("mybody"); // метод подставляет значения из субститутора в строку
        //String param1 = context.get("param1"); // метод возвращает значение параметра по ключу
        /*
          Переменные:
            Входящие:
              requestBody - значение боди
              requestHeaders - хедеры
	      requestPath - URI
              value_headers -
		Асинк:
	        responseBody
                responseStatus
                
        */



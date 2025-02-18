# TALLER MICROFRAMEWORKS WEB


Este proyecto implementa un servidor web minimalista en Java con soporte para el manejo de páginas HTML, imágenes PNG y solicitudes REST. Además, incorpora un framework IoC (Inversión de Control) que permite la construcción de aplicaciones web a partir de POJOs utilizando capacidades reflectivas de Java.

El servidor está diseñado para atender múltiples solicitudes de forma no concurrente y permite la carga dinámica de clases anotadas con @RestController, publicando servicios en las URIs correspondientes definidas mediante @GetMapping. También soporta el uso de @RequestParam para recibir parámetros en las solicitudes.

En su versión final, explorará el classpath en busca de clases con las anotaciones adecuadas y las cargará automáticamente.

Para demostrar su funcionamiento, el proyecto incluye una aplicación web de ejemplo que responde a solicitudes REST y muestra contenido HTML relacionado con REM.

![{77378D07-17EE-46F6-9941-1207A993392A}](https://github.com/user-attachments/assets/0d10e85d-2502-4756-9b86-7f727cf55d42)

## Arquitectura
Este proyecto sigue la arquitectura cliente-servidor. Utilizando el estilo arquitectonico de REST, Los clientes envían solicitudes HTTP al servidor, que maneja la lógica de los serviciso REST y devuelve respuestas en formato JSON. El servidor también puede retornar archivos estáticos, como HTML, CSS e imágenes. 

![{B47E569F-C1A3-4BF5-A76C-21482C66D2A2}](https://github.com/user-attachments/assets/bfae084e-bc1d-405c-b8b0-ccbab15e84da)

### Componentes del Proyecto 
   - **Servidor** : HttpServer: Maneja las conexiones y enruta las solicitudes a los servicios correspondientes.
     ClientHandler: Gestiona la comunicación con el cliente, procesa la solicitud y decide si debe retornar un archivo estático o delegar la solicitud a un servicio REST.

   - **Servicios REST**: ClientHandler: Maneja operaciones CRUD en objetos JSON y consulta archivos HTML estáticos guardados en el servidor.

   - **Archivos estáticos**: Los archivos estáticos se almacenan en el directorio public, permitiendo que el servidor sirva contenido HTML, CSS, JS e imágenes de manera eficiente.

   - **Anotaciones y Controladores**: Annotations: Se definen las anotaciones necesarias para identificar los componentes del framework. Estas incluyen:

@RestController: Marca una clase como un controlador REST.
@GetMapping: Especifica que un método maneja solicitudes GET a una URL determinada.
@RequestParam: Permite la recepción de parámetros en los métodos manejadores.

Controllers: Consumen las anotaciones definidas y procesan las solicitudes REST.

## Primeros Pasos
Estas instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba. 


### Requisitos Previos
Para ejecutar este proyecto, necesitarás tener instalado:

- Java JDK 8 o superior.
- Un IDE de Java como IntelliJ IDEA, Eclipse.
- Maven para manejar las dependencias 
- Un navegador web para interactuar con el servidor.

### Instalación 

1. Tener instalado Git en tu maquina local 
2. Elegir una carpeta en donde guardes tu proyecto
3. abrir la terminal de GIT --> mediante el clik derecho seleccionas Git bash here
4. Clona el repositorio en tu máquina local:
   ```bash
   git https://github.com/andreec2/Laboratorio3-Arep.git
   ```
5. Abre el proyecto con tu IDE favorito o navega hasta el directorio del proyecto 
6. Desde la terminal  para compilar el proyecto ejecuta:

   ```bash
   mvn clean install
   ```
7. compila el proyecto  

   ```bash
    mvn clean package
   ```
8. Corra el servidor en la clase httpServer "main" o ejecute el siguiente comando desde consola
   
      ```bash
    java -cp target/classes org.example.server.HttpServer
   ```
   Vera que el servidor esta listo y corriendo sobre el puerto 35000
   
9. Puedes Ingresar desde el navegador a la pagina:
    
    http://localhost:35000/index.html

10. Puedes interactuar con los endpoints RESTful (/api):
   - POST= http://localhost:35000/post.html

11. Para este taller, además de los recursos estáticos ya implementados, hemos definido nuevos endpoints aprovechando las capacidades reflexivas de Java. Para ello, hemos creado anotaciones como @GetMapping y @RequestParam, lo que permite manejar solicitudes de manera más flexible y estructurada.

Los nuevos endPoints definidos son los siguientes:

http://localhost:35000/nombre?name=andres

![image](https://github.com/user-attachments/assets/b2770f13-9728-4218-874c-382dbd8c0f29)

![image](https://github.com/user-attachments/assets/90de5267-dfe7-4bb1-b03f-307a4c452bf1)

http://localhost:35000/sum?a=6&b=5

![image](https://github.com/user-attachments/assets/f75b949a-0b74-4f3e-9d8d-728b0014b867)

![image](https://github.com/user-attachments/assets/2e8fb19f-c2b8-473e-90b7-41ef9afbdc82)

http://localhost:35000/res?a=6&b=5

![image](https://github.com/user-attachments/assets/d6c91eca-bb6a-483b-a96c-633557affca1)

![image](https://github.com/user-attachments/assets/39c49846-0f39-4dad-8a63-5df2868e2689)

http://localhost:35000/mul?a=6&b=5

![image](https://github.com/user-attachments/assets/b5d5ac39-7a0f-4f58-97b0-28bc29c7c3a1)

![image](https://github.com/user-attachments/assets/9094870b-ea48-46bc-a4db-ef6dfb83feaa)

## Ejecutar las pruebas

Se implementaron pruebas unitarias para los métodos de manejo de solicitudes HTTP  en el servidor. Estas pruebas se realizaron utilizando JUnit  para simular las solicitudes y validar las respuestas.

Para ejecutar las pruebas:  
1. Desde tu IDE, ejecuta las clase AppTest.java o desde la terminal ejecutas:
   ```bash
   mvn test
   ```
![image](https://github.com/user-attachments/assets/3b772408-92f4-4182-8433-014ed24c2947)

![image](https://github.com/user-attachments/assets/76294f8b-96c8-4b29-8742-bee5917370aa)

2. Pruebas de extremo a extremo

# testGetIndexHtml
Propósito: Verificar que el servidor entrega correctamente el archivo index.html, asegurando que la respuesta incluya 200 OK y el Content-Type: text/html.

![image](https://github.com/user-attachments/assets/c42dbc8e-8d66-44b9-9ca4-83f4a8098c30)

# testFileNotFound
Propósito: Comprobar que el servidor responde con un código 404 cuando se solicita un archivo inexistente.

![image](https://github.com/user-attachments/assets/1a6e7739-30b5-4310-98f9-b46b564dc58d)

# testMathOperations
Propósito: Validar que las operaciones matemáticas (sum, res, mul) devuelven los resultados correctos.

![image](https://github.com/user-attachments/assets/ba651699-17a3-4140-b31a-6407985c4a50)

# testSumWithValidInput
Propósito: Probar la suma con valores a=5 y b=3, asegurando que el resultado es 8.

![image](https://github.com/user-attachments/assets/a9d38e8a-4261-49ea-8b5d-2858f0b959c3)

# testSumWithDefaultValues
Propósito: Verificar que si no se proporcionan valores para a y b, el resultado por defecto sea 0.

![image](https://github.com/user-attachments/assets/3ca0698c-07ff-4c01-87ac-be59efe5902f)

# testSumWithInvalidInput
Propósito: Asegurar que la operación /sum maneja correctamente valores no numéricos (a=abc&b=3) y devuelve un mensaje de error.

![image](https://github.com/user-attachments/assets/4741e761-076c-4958-b52f-c8bba0d8a60a)

# testResWithValidInput
Propósito: Verificar que la resta de a=10 y b=3 devuelve 7 correctamente.

![image](https://github.com/user-attachments/assets/7a52a5be-6cfb-435d-8f88-d1406956bd1e)

# testResWithNegativeResult
Propósito: Asegurar que la resta de a=3 y b=10 maneja correctamente los valores negativos y devuelve -7.

![image](https://github.com/user-attachments/assets/38fa5661-f1ec-483c-990d-d6734e4281d1)

# testMulWithValidInput
Propósito: Probar la multiplicación de 4 * 5, esperando un resultado de 20.

![image](https://github.com/user-attachments/assets/103f2081-a1fd-4963-b123-e6a53f286c6d)

# testMulWithZero
Propósito: Validar que la multiplicación por 0 devuelve correctamente 0.

![image](https://github.com/user-attachments/assets/0dcefcb8-5ead-4d18-8bc1-42cc433992e8)

# testGreetingWithDefaultValue
Propósito: Verificar que la ruta /greeting devuelve Hola World cuando no se proporciona un nombre.

![image](https://github.com/user-attachments/assets/5af8fdc2-eb3b-403a-9e8b-30d1f3456fe3)

# testGreetingWithCustomName
Propósito: Probar que la ruta /greeting?name=Juan Carlos devuelve Hola Juan Carlos.

![image](https://github.com/user-attachments/assets/8b2bc8c2-61ba-4008-94a5-1cd108521995)

# testPi
Propósito: Validar que la ruta /pi devuelve el valor de Math.PI.

![image](https://github.com/user-attachments/assets/57d96705-189a-4b0d-ade4-5fa8e5bde374)

# testAllControllersAreLoaded
Propósito: Asegurar que los controladores GreetingController y AndresController están cargados correctamente probando las rutas /greeting y /nombre.

![image](https://github.com/user-attachments/assets/ff06a526-146d-4b4f-998c-f8650312475f)

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Andres felipe montes ortiz** - 


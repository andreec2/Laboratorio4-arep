# TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER

En este taller, aprenderemos a desplegar en un entorno Dockerizado, tanto en nuestra máquina local como en la nube mediante AWS, nuestro MICROFRAMEWORK WEB.

El proceso se dividirá en varias etapas clave:

1. Dockerización de la aplicación: Construiremos un contenedor Docker para nuestra aplicación, lo ejecutaremos localmente y verificaremos su correcto funcionamiento.

2. Publicación en DockerHub: Crearemos un repositorio en DockerHub y subiremos nuestra imagen para que pueda ser accesible desde cualquier entorno.
   
3. Despliegue en AWS: Configuraremos una máquina virtual en Amazon Web Services (AWS), instalaremos Docker y desplegaremos nuestra aplicación desde la imagen almacenada en DockerHub.

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
   git https://github.com/andreec2/Laboratorio4-arep.git
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

### Dockerización de la aplicación

1. Importe las dependencias de spark Java en el archivo pom.

![image](https://github.com/user-attachments/assets/6205c26e-1135-4af1-a2b0-183cce4928e9)

2. Asegúrese que el proyecto esté compilando hacia la versión 17 o superior de Java.
   
![image](https://github.com/user-attachments/assets/8ab4e8d7-148d-4da0-9e09-4f142d3f13a9)

3. Asegúrese que el proyecto este copiando las dependencias en el directorio target al compilar el proyecto. Esto es necesario para poder construir una imagen de contenedor de docker usando los archivos ya compilados de java. Para hacer esto use el plugin de dependencias de Maven.

![image](https://github.com/user-attachments/assets/55af5b97-69a5-4476-b8bc-387054aae92e)

4. Asegúrese que el proyecto compila

   ```bash
    mvn clean package
   ```

5. Asegúrese que las dependencias están en el directorio target y que continentemente las dependencia, es decir las librerías necesarias para correr en formato jar. En este caso solo son las dependencias necesarias para correr SparkJava.

![image](https://github.com/user-attachments/assets/b769a1bd-52c3-435e-9337-145ba0017220)

6. Ejecute el programa invocando la máquina virtual de Java desde la línea de comandos y acceda la url http:localhost:6000/pi:

![image](https://github.com/user-attachments/assets/2d46f96b-db3a-40cb-9448-8435f3e5584d)

## Crear imagen para docker y subirla

1. En la raíz de su proyecto cree un archivo denominado Dockerfile con el siguiente contenido

![image](https://github.com/user-attachments/assets/43f02c1a-4fbb-4c0d-b28e-0c6e618127f9)

2. Usando la herramienta de línea de comandos de Docker construya la imagen.

   ```bash
    docker build --tag dockersparkprimer .
   ```

![image](https://github.com/user-attachments/assets/3b1916e3-53ef-4d6f-8a05-189c255ed640)

3. Revise que la imagen fue construida.

   ```bash
    docker images
   ```

![image](https://github.com/user-attachments/assets/ef631caa-bfb0-4529-821d-a5c99a01c2b0)

4. A partir de la imagen creada cree tres instancias de un contenedor docker independiente de la consola (opción “-d”) y con el puerto 6000 enlazado a un puerto físico de su máquina (opción -p).

![image](https://github.com/user-attachments/assets/0a79219b-c58a-4829-90cf-4de1f891743c)

5. Asegúrese que el contenedor está corriendo.

   ```bash
   docker ps
   ```
   
![image](https://github.com/user-attachments/assets/b8ed52d7-7c3b-4f83-9490-14734b47ed81)

6. Acceda por el browser a http://localhost:34002/index.html, o a http://localhost:34000/index.html, o a http://localhost:34001/index.html para verificar que están corriendo.

![image](https://github.com/user-attachments/assets/5ed68570-e05e-4d02-ba5e-3a8000b0d0f3)

7. Use docker-compose para generar automáticamente una configuración docker, por ejemplo un container y una instancia a de mongo en otro container. Cree en la raíz de su directorio el archivo docker-compose.yml con le siguiente contenido.

![image](https://github.com/user-attachments/assets/2a455be6-5a2f-4552-9b19-65cf0bd6ae5e)

8.  Ejecute el docker compose.

   ```bash
    docker-compose up -d
   ```

![image](https://github.com/user-attachments/assets/547ed07d-835b-4d4c-b1fb-f77d38560c0e)

9. Verifique que se crearon los servicios.

   ```bash
   docker ps
   ```

![image](https://github.com/user-attachments/assets/1803ef8d-cd89-4a0a-80e1-7f532782102e)

## Subir la imagen a Docker Hub

1. Cree una cuenta en Dockerhub y verifique su correo.
2. Acceda al menu de repositorios y cree un repositorio

![image](https://github.com/user-attachments/assets/813dd2dc-8d08-4210-9448-61440fe00d56)

3. En su motor de docker local cree una referencia a su imagen con el nombre del repositorio a donde desea subirla.

   ```bash
   docker tag dockersparkprimer andreec22/Laboratorio4
   ```

![image](https://github.com/user-attachments/assets/ac5a0791-6d34-4f88-894b-4adfd9818397)

5. Verifique que la nueva referencia de imagen existe

   ```bash
    docker images
   ```

![image](https://github.com/user-attachments/assets/e656a24a-af43-4e99-8bb8-9fd2765387d6)

6. Autentíquese en su cuenta de dockerhub (ingrese su usuario y clave si es requerida).

![image](https://github.com/user-attachments/assets/26a97557-2cfe-4e76-9f52-c41034bf6c8d)

7. Empuje la imagen al repositorio en DockerHub.

   ```bash
   docker push andreec22/Laboratorio4:latest
   ```

![image](https://github.com/user-attachments/assets/5faf3a84-9289-4ee5-a399-b92ccefebe9d)

En la solapa de Tags de su repositorio en Dockerhub debería ver algo así:

![image](https://github.com/user-attachments/assets/7092f1ef-47bd-456c-a423-f6bb852c9342)

## Cuarta parte: AWS

1. Crear una maquina virtual.

![image](https://github.com/user-attachments/assets/d31ecaf5-f037-4f4d-8f13-866b54fd8726)

![image](https://github.com/user-attachments/assets/5174a7c9-01ac-4e24-a9dc-b56d3dc8ccb5)

2. Crear un par de claves ssh para ingresar a la maquina virtual.

3. Acceda a la máquina virtual

![image](https://github.com/user-attachments/assets/2854b897-73c6-409f-a310-a662e7e9ce49)

4. Instale Docker

![image](https://github.com/user-attachments/assets/2279c775-0f54-4827-9aca-39e34d678ccb)

5. sudo service docker start.

![image](https://github.com/user-attachments/assets/1ffbb6b4-523a-4570-94a3-53a0849e6341)

6. A partir de la imagen creada en Dockerhub cree una instancia de un contenedor docker independiente de la consola (opción “-d”) y con el puerto 6000 enlazado a un puerto físico de su máquina (opción -p).

![image](https://github.com/user-attachments/assets/4cb47ccf-3050-49a7-871a-0c97f397a1be)

7. Abra los puertos de entrada del security group de la máxima virtual para acceder al servicio.

![image](https://github.com/user-attachments/assets/5175c8a5-96c5-4273-b109-bae3edecc5d1)

![image](https://github.com/user-attachments/assets/f3e25337-afef-4ff2-ab0b-5eaa13caa9c6)

8. Verifique que pueda acceder  en una url similar a esta (la url específica depende de los valores de su maquina virtual EC2)

![image](https://github.com/user-attachments/assets/4e60eff0-5525-400c-a859-9fe9142484b3)

ec2-52-90-4-194.compute-1.amazonaws.com:42000/index.html

![image](https://github.com/user-attachments/assets/e33114b5-d422-4ad5-a1f3-8ee2d6df22fa)

http://localhost:35000/nombre?name=andres
http://localhost:35000/sum?a=6&b=5
http://localhost:35000/res?a=6&b=5
http://localhost:35000/mul?a=6&b=5

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


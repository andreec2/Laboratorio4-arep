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
(aqui voy luego sigo xd)

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



## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Andres felipe montes ortiz** - 


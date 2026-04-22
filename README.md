# Bienvenido a la matanza de los microservicios  
Hice el repositorio de este modo porque según lo que investigué es un buen modo de hacerlo, entonces ajá
aquí irá el árbol del repositorio y como se hace para desplegar todo esto

# 1 estructura de carpetas

### services: contiene todos los proyectos de microservicios
### eureka-server: servidor principal que ayuda a que los microservicios se conozcan entre si
### gateway: punto de entrada único para el cliente
### .env.example: variables de entorno 
### docker-compose: acá nacen los contenedores de todo 

# 2- reproducir los microservicios 

### paso 1: clonar el repositoio
```
git clone origin https://github.com/Brayan1210-1/rick-and-morty-microservice.git
cd rick-and-morty-microservice
```

### paso 2: crea el .env en la raiz de la carpeta
```
# ejem:
.env
.env.example
services
gateway
```

### paso 3: copia lo que está en el .env.example y pégalo en .env (no olvides reemplazar los valores)
```
DB_USERNAME=TUusuario
DB_PASSWORD=contrasenasegura
```

### ejecuta docker-compose up --build y todo debería funcionar

### rutas de swagger para los microservicios:
* localizaciones: http://localhost:8082/swagger-ui/index.html#/
* character: http://localhost:8081/swagger-ui/index.html#/

### DISCLAIMER!!!! 
Primero debes crear las localizaciones y después los personajes, si quieres crear personajes con ubicaciones inexistentes habrá un error en la respuesta, pero igualmente se guardarán en la base de datos


### La url para el gateway es: http://localhost:8080/api/{microservicio}
microservicios habilitados: character    location

# Menta Tickets Challenge

---

## ¿De qué trata?
Los caracteres que utilizamos todos los días para escribir, tanto en español, inglés y otros lenguajes que comparten las
mismas raíces tienen una particularidad: puede ser de trazos cerrados o bien de trazos abiertos.
¿A qué nos referimos cuando hablamos de trazos cerrados? Básicamente que hay partes del caracter que quedan aisladas del
exterior, habitualmente con una circunferencia, y esta puede ser rellena o vacía.
Por ejemplo, la letra "**a**" tiene un trazado completo que forma un círculo en su interior.

Otro ejemplo es la "**B**", que tiene dos espacios cerrados. La letra "**U**", por el contrario, ninguno. Y así con
todos los caracteres que habitualmente utilizamos para escribir.
Nos interesa contar la cantidad de “espacios cerrados” tiene una cadena de texto que ingresamos en nuestro programa.
Puede ser una oración completa o incluso un párrafo.

### Consideraciones principales

1. La entrada de texto únicamente será de caracteres que se utilicen en el español (
   letras [incluso con tildes o símbolos (á, é)], números y algunos
   símbolos [aclarados al pie](#smbolos-permitidos-_--el-resto-de-los-smbolos-no-son-permitidos)).
2. Ej: Frente a la cadena de texto de entrada: “El 37% de los humanos está bancarizado” el resultado debería ser 15.
3. Puntos que se encuentren en las letras también deberían sumar: (ejemplo la i o la j)

Si sobra tiempo y queres dar un extra mile:

- Montar un servicio API REST para enviar una cadena mediante HTTP y obtener una
  respuesta codificada en JSON con el resultado. Utiliza la implementación que creas
  conveniente.
- Realizar manejo de errores
- Realizar una ideación extensible que no solo permita soportar español, sino
  lenguajes como el francés o el alemán (ej), sin volver a compilar el programa para agregar un nuevo lenguaje.

Y si aún querés más:

- Deploya la API REST en algún proveedor de cloud computing y generá una URL
  estática para consumir el servicio públicamente desde internet.

#### Símbolos permitidos: **@#$%∞%‰&/()=?¿_-** el resto de los símbolos no son permitidos.

## Resolución

El servicio con la resolución fue deployado en el servicio Cloud de [Heroku](https://www.heroku.com/) en la siguiente URL:

```
https://menta-tickets-challenge.herokuapp.com/
```

### Tecnología

El servicio REST fue realizado con el siguiente stack tecnológico:

- Kotlin.
- Gradle.
- Spring Boot.

A su vez, fue documentado con el uso de Swagger/OpenAPI, con la librería SpringDoc.

### Ejemplo de Uso

```
curl -X 'POST' \
  'https://menta-tickets-challenge.herokuapp.com/challenge/solve' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "El 37% de los humanos está bancarizado"
}'
```

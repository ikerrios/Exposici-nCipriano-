# REST vs GraphQL

## Características de REST

- Utiliza múltiples endpoints, cada uno representando un recurso (por ejemplo: `/users`, `/posts`, `/comments`).
- Se basa en los métodos HTTP estándar: `GET`, `POST`, `PUT`, `DELETE`.
- La estructura de las respuestas es fija y la define el servidor.
- Puede sufrir **over-fetching** (recibir más datos de los necesarios) o **under-fetching** (necesidad de varias peticiones).
- El diseño se centra en recursos y en cómo se manipulan mediante los métodos HTTP.
- El versionado se gestiona creando nuevas rutas (`/api/v2/...`).
- Usa herramientas como Swagger o Postman para la documentación.
- Curva de aprendizaje más sencilla y ampliamente adoptada.
- Seguridad basada en tokens, API keys o JWT, con control por endpoint.
- Cacheo sencillo basado en URLs.
- Testing simple, ya que cada endpoint tiene un propósito claro.

## Características de GraphQL

- Utiliza un único endpoint (generalmente `/graphql`).
- El cliente define qué datos quiere recibir mediante *queries*.
- Evita el **over-fetching** y el **under-fetching**.
- Permite obtener datos de varios recursos en una sola petición.
- Basado en un esquema de tipos y relaciones entre datos.
- No requiere versionado: el esquema puede evolucionar agregando nuevos campos.
- Documentación automática e interactiva (GraphiQL, Apollo Explorer).
- Curva de aprendizaje más alta, pero con gran flexibilidad.
- Control de seguridad más granular, incluso por campo.
- Cacheo más complejo debido a la naturaleza dinámica de las consultas.
- Testing más difícil porque las queries pueden variar.

## Principales diferencias entre REST y GraphQL

1. **Número de endpoints**  
   - REST usa muchos endpoints, GraphQL solo uno.

2. **Forma de obtener datos**  
   - En REST la respuesta está predefinida.  
   - En GraphQL el cliente elige qué datos necesita.

3. **Eficiencia**  
   - REST puede requerir varias peticiones.  
   - GraphQL obtiene todo en una sola consulta.

4. **Versionado**  
   - REST necesita nuevas versiones del API.  
   - GraphQL evoluciona sin versionado.

5. **Documentación**  
   - REST usa herramientas externas como Swagger.  
   - GraphQL genera documentación automática.

6. **Cacheo**  
   - REST tiene cacheo sencillo basado en URL.  
   - GraphQL requiere estrategias más complejas.

7. **Seguridad**  
   - REST se protege por endpoints y métodos.  
   - GraphQL necesita controlar el acceso a nivel de campo.

8. **Curva de aprendizaje**  
   - REST es más fácil de aprender.  
   - GraphQL requiere aprender su lenguaje de consultas.

9. **Uso recomendado**  
   - REST: APIs simples o públicas.  
   - GraphQL: aplicaciones complejas, móviles o SPA.

## Ejemplo comparativo

**REST**
```http
GET /users/1
→ {
  "id": 1,
  "name": "Iker",
  "posts": [1, 2, 3]
}
```

Si se necesitan los títulos de los posts:
```http
GET /posts?userId=1
```
Requiere dos peticiones.

**GraphQL**
```graphql
{
  user(id: 1) {
    name
    posts {
      title
    }
  }
}
```
Una sola petición devuelve todos los datos necesarios.

#version 330

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

// Models a light positionated at infinity.
struct PointLight
{
    vec3 color;
    vec3 position; // In view coordinates.
    float intensity;
    Attenuation attenuation;
};

// Models a directional light, e.g. a spotlight.
struct DirectionalLight
{
    vec3 color;
    vec3 direction;
    float intensity;
};

struct Material
{
    // Colors used if no texture given.
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};

in vec2 outTextureCoordinate;
in vec3 modelViewVertexPosition;
in vec3 modelViewVertexNormal;
out vec4 fragmentColor;

uniform sampler2D textureSampler;
uniform vec3 color;
uniform int useColor;

uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;

uniform PointLight pointLight;
uniform DirectionalLight directionalLight;

uniform vec3 cameraPosition;

vec4 ambientConstant;
vec4 diffuseConstant;
vec4 specularConstant;

void setupColors(Material material, vec2 textureCoordinates)
{
    if (material.hasTexture == 1)
    {
        ambientConstant = texture(textureSampler, textureCoordinates);
        diffuseConstant = ambientConstant;
        specularConstant = ambientConstant;
    }
    else
    {
        ambientConstant = material.ambient;
        diffuseConstant = material.diffuse;
        specularConstant = material.specular;
    }
}

vec4 computeLightColor(vec3 lightColor, float lightIntensity, vec3 position, vec3 toLightDirection, vec3 normal)
{
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specColor = vec4(0, 0, 0, 0);

    // Diffuse Light
    float diffuseFactor = max(dot(normal, toLightDirection), 0.0);
    diffuseColor = diffuseConstant * vec4(lightColor, 1.0) * lightIntensity * diffuseFactor;

    // Specular Light
    vec3 cameraDirection = normalize(cameraPosition - position);
    vec3 fromLightDirection = -toLightDirection;
    vec3 reflectedLight = normalize(reflect(fromLightDirection, normal));
    float specularFactor = max(dot(cameraDirection, reflectedLight), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColor = specularConstant * lightIntensity  * specularFactor * material.reflectance * vec4(lightColor, 1.0);

    return (diffuseColor + specColor);
}

vec4 computePointLight(PointLight light, vec3 position, vec3 normal)
{
    vec3 lightDirection = light.position - position;
    vec3 toLightDirection  = normalize(lightDirection);
    vec4 lightColor = computeLightColor(light.color, light.intensity, position, toLightDirection, normal);

    float distance = length(lightDirection);
    float attenuationInverse = light.attenuation.constant + light.attenuation.linear * distance +
        light.attenuation.exponent * distance * distance;
    return lightColor / attenuationInverse;
}

vec4 computeDirectionalLight(DirectionalLight light, vec3 position, vec3 normal)
{
    return computeLightColor(light.color, light.intensity, position, normalize(light.direction), normal);
}

void main()
{
    setupColors(material, outTextureCoordinate);

    vec4 diffuseSpecularComponent = computePointLight(pointLight, modelViewVertexPosition, modelViewVertexNormal);
    diffuseSpecularComponent += computeDirectionalLight(directionalLight, modelViewVertexPosition, modelViewVertexNormal);

    fragmentColor = ambientConstant * vec4(ambientLight, 1) + diffuseSpecularComponent;
}